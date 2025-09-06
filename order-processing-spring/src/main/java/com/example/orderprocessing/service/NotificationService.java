package com.example.orderprocessing.service;

import java.util.ArrayList;
import java.util.List;

import com.example.orderprocessing.domain.Order;
import com.example.orderprocessing.events.BaseEvent;
import com.example.orderprocessing.observer.Observer;

public class NotificationService {
    private final List<Observer> observers = new ArrayList<>();

    public void register(Observer observer) {
        observers.add(observer);
    }

    public void notifyEventProcessed(Order order, BaseEvent event) {
        for (Observer o : observers) {
            o.onEventProcessed(order, event);
        }
    }

    public void notifyStatusChanged(Order order) {
        for (Observer o : observers) {
            o.onStatusChanged(order);
        }
    }
}
