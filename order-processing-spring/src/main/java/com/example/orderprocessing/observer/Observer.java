package com.example.orderprocessing.observer;

import com.example.orderprocessing.domain.Order;
import com.example.orderprocessing.events.BaseEvent;

public interface Observer {
    void onEventProcessed(Order order, BaseEvent event);
    void onStatusChanged(Order order);
}
