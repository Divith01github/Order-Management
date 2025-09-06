package com.example.orderprocessing.repository;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Repository;

import com.example.orderprocessing.domain.Order;

@Repository
public class InMemoryOrderRepository {
    private final Map<String, Order> orders = new ConcurrentHashMap<>();

    public Order save(Order order) {
        orders.put(order.getOrderId(), order);
        return order;
    }

    public Order findById(String id) {
        return orders.get(id);
    }

    public boolean exists(String id) {
        return orders.containsKey(id);
    }

    public Collection<Order> findAll() {
        return orders.values();
    }
}
