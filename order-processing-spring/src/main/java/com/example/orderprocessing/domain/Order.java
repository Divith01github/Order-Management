package com.example.orderprocessing.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.example.orderprocessing.events.BaseEvent;

public class Order {
    private String orderId;
    private String customerId;
    private List<OrderItem> items = new ArrayList<>();
    private BigDecimal totalAmount;
    private OrderStatus status = OrderStatus.PENDING;
    private List<BaseEvent> eventHistory = new ArrayList<>();

    public Order() {}

    public Order(String orderId, String customerId, List<OrderItem> items, BigDecimal totalAmount) {
        this.orderId = orderId;
        this.customerId = customerId;
        if (items != null) this.items = items;
        this.totalAmount = totalAmount;
        this.status = OrderStatus.PENDING;
    }

    public String getOrderId() { return orderId; }
    public void setOrderId(String orderId) { this.orderId = orderId; }

    public String getCustomerId() { return customerId; }
    public void setCustomerId(String customerId) { this.customerId = customerId; }

    public List<OrderItem> getItems() { return items; }
    public void setItems(List<OrderItem> items) { this.items = items; }

    public BigDecimal getTotalAmount() { return totalAmount; }
    public void setTotalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; }

    public OrderStatus getStatus() { return status; }
    public void setStatus(OrderStatus status) { this.status = status; }

    public List<BaseEvent> getEventHistory() { return eventHistory; }
    public void addEventToHistory(BaseEvent e) { this.eventHistory.add(e); }
}
