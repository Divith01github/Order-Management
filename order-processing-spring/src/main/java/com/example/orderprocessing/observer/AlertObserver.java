package com.example.orderprocessing.observer;

import com.example.orderprocessing.domain.Order;
import com.example.orderprocessing.events.BaseEvent;
import com.example.orderprocessing.domain.OrderStatus;

public class AlertObserver implements Observer {

    @Override
    public void onEventProcessed(Order order, BaseEvent event) {
        // No-op for generic events
    }

    @Override
    public void onStatusChanged(Order order) {
        if (order.getStatus() == OrderStatus.CANCELLED || order.getStatus() == OrderStatus.SHIPPED) {
            System.out.printf("[ALERT] Sending alert for Order %s: Status changed to %s\n",
                order.getOrderId(), order.getStatus());
        }
    }
}
