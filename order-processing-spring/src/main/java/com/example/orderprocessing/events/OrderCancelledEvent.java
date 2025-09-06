package com.example.orderprocessing.events;

public class OrderCancelledEvent extends BaseEvent {
    private String orderId;
    private String reason;

    public String getOrderId() { return orderId; }
    public void setOrderId(String orderId) { this.orderId = orderId; }

    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }
}
