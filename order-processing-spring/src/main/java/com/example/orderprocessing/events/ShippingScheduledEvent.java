package com.example.orderprocessing.events;

import java.time.LocalDate;

public class ShippingScheduledEvent extends BaseEvent {
    private String orderId;
    private LocalDate shippingDate;

    public String getOrderId() { return orderId; }
    public void setOrderId(String orderId) { this.orderId = orderId; }

    public LocalDate getShippingDate() { return shippingDate; }
    public void setShippingDate(LocalDate shippingDate) { this.shippingDate = shippingDate; }
}
