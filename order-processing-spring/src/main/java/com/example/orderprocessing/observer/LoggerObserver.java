package com.example.orderprocessing.observer;

import com.example.orderprocessing.domain.Order;
import com.example.orderprocessing.events.BaseEvent;

public class LoggerObserver implements Observer {

    @Override
    public void onEventProcessed(Order order, BaseEvent event) {
        System.out.printf("[Logger] Order %s processed event %s (%s)\n",
            order != null ? order.getOrderId() : "N/A",
            event.getEventId(),
            event.getEventType());
    }

    @Override
    public void onStatusChanged(Order order) {
        System.out.printf("[Logger] Order %s status changed to %s\n",
            order.getOrderId(),
            order.getStatus());
    }
}
