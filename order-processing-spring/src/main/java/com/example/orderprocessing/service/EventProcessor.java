package com.example.orderprocessing.service;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.example.orderprocessing.domain.Order;
import com.example.orderprocessing.domain.OrderStatus;
import com.example.orderprocessing.events.BaseEvent;
import com.example.orderprocessing.events.OrderCancelledEvent;
import com.example.orderprocessing.events.OrderCreatedEvent;
import com.example.orderprocessing.events.PaymentReceivedEvent;
import com.example.orderprocessing.events.ShippingScheduledEvent;
import com.example.orderprocessing.events.UnknownEvent;
import com.example.orderprocessing.repository.InMemoryOrderRepository;

@Service
public class EventProcessor {
    private static final Logger log = LoggerFactory.getLogger(EventProcessor.class);
    private final InMemoryOrderRepository repo;
    private final NotificationService notifier;

    public EventProcessor(InMemoryOrderRepository repo, NotificationService notifier) {
        this.repo = repo;
        this.notifier = notifier;
    }

    public void process(BaseEvent event) {
        if (event instanceof UnknownEvent) {
            log.warn("Unknown event encountered: type={}, id={}", event.getEventType(), event.getEventId());
            return;
        }

        if (event instanceof OrderCreatedEvent oce) {
            processOrderCreated(oce);
        } else if (event instanceof PaymentReceivedEvent pre) {
            processPaymentReceived(pre);
        } else if (event instanceof ShippingScheduledEvent sse) {
            processShippingScheduled(sse);
        } else if (event instanceof OrderCancelledEvent oce2) {
            processOrderCancelled(oce2);
        } else {
            log.warn("Unsupported event type: {}", event.getClass().getSimpleName());
        }
    }

    private void processOrderCreated(OrderCreatedEvent e) {
        if (repo.exists(e.getOrderId())) {
            log.warn("Order {} already exists. Skipping creation.", e.getOrderId());
            return;
        }
        Order order = new Order(e.getOrderId(), e.getCustomerId(), e.getItems(), e.getTotalAmount());
        order.addEventToHistory(e);
        repo.save(order);
        notifier.notifyEventProcessed(order, e);
        notifier.notifyStatusChanged(order);
    }

    private void processPaymentReceived(PaymentReceivedEvent e) {
        Order order = repo.findById(e.getOrderId());
        if (order == null) {
            log.warn("Payment for unknown order {} received. Ignoring.", e.getOrderId());
            return;
        }
        if (order.getStatus() == OrderStatus.CANCELLED) {
            log.warn("Order {} is CANCELLED. Ignoring payment.", order.getOrderId());
            return;
        }
        BigDecimal total = order.getTotalAmount();
        BigDecimal paid = e.getAmountPaid();
        OrderStatus previous = order.getStatus();
        if (paid == null || total == null) {
            log.warn("Invalid amounts for order {}", order.getOrderId());
        } else if (paid.compareTo(total) >= 0) {
            order.setStatus(OrderStatus.PAID);
        } else if (paid.compareTo(BigDecimal.ZERO) > 0) {
            order.setStatus(OrderStatus.PARTIALLY_PAID);
        }
        order.addEventToHistory(e);
        repo.save(order);
        notifier.notifyEventProcessed(order, e);
        if (order.getStatus() != previous) {
            notifier.notifyStatusChanged(order);
        }
    }

    private void processShippingScheduled(ShippingScheduledEvent e) {
        Order order = repo.findById(e.getOrderId());
        if (order == null) {
            log.warn("Shipping scheduled for unknown order {}. Ignoring.", e.getOrderId());
            return;
        }
        if (order.getStatus() == OrderStatus.CANCELLED) {
            log.warn("Order {} is CANCELLED. Ignoring shipping schedule.", order.getOrderId());
            return;
        }
        OrderStatus previous = order.getStatus();
        order.setStatus(OrderStatus.SHIPPED);
        order.addEventToHistory(e);
        repo.save(order);
        notifier.notifyEventProcessed(order, e);
        if (order.getStatus() != previous) {
            notifier.notifyStatusChanged(order);
        }
    }

    private void processOrderCancelled(OrderCancelledEvent e) {
        Order order = repo.findById(e.getOrderId());
        if (order == null) {
            log.warn("Cancellation for unknown order {}. Ignoring.", e.getOrderId());
            return;
        }
        if (order.getStatus() == OrderStatus.CANCELLED) {
            log.info("Order {} already CANCELLED.", order.getOrderId());
            return;
        }
        OrderStatus previous = order.getStatus();
        order.setStatus(OrderStatus.CANCELLED);
        order.addEventToHistory(e);
        repo.save(order);
        notifier.notifyEventProcessed(order, e);
        if (order.getStatus() != previous) {
            notifier.notifyStatusChanged(order);
        }
    }
}
