package src;

import java.time.Instant;

class OrderCreatedEvent extends Event {
    private String orderId;
    private String customerId;
    private double totalAmount;

    public OrderCreatedEvent(String eventId, Instant timestamp, String orderId, String customerId, double totalAmount) {
        super(eventId, timestamp, "OrderCreated");
        this.orderId = orderId;
        this.customerId = customerId;
        this.totalAmount = totalAmount;
    }

    public String getOrderId() { return orderId; }
    public String getCustomerId() { return customerId; }
    public double getTotalAmount() { return totalAmount; }
}

class PaymentReceivedEvent extends Event {
    private String orderId;
    private double amountPaid;

    public PaymentReceivedEvent(String eventId, Instant timestamp, String orderId, double amountPaid) {
        super(eventId, timestamp, "PaymentReceived");
        this.orderId = orderId;
        this.amountPaid = amountPaid;
    }

    public String getOrderId() { return orderId; }
    public double getAmountPaid() { return amountPaid; }
}

class ShippingScheduledEvent extends Event {
    private String orderId;
    private String shippingDate;

    public ShippingScheduledEvent(String eventId, Instant timestamp, String orderId, String shippingDate) {
        super(eventId, timestamp, "ShippingScheduled");
        this.orderId = orderId;
        this.shippingDate = shippingDate;
    }

    public String getOrderId() { return orderId; }
    public String getShippingDate() { return shippingDate; }
}

class OrderCancelledEvent extends Event {
    private String orderId;
    private String reason;

    public OrderCancelledEvent(String eventId, Instant timestamp, String orderId, String reason) {
        super(eventId, timestamp, "OrderCancelled");
        this.orderId = orderId;
        this.reason = reason;
    }

    public String getOrderId() { return orderId; }
    public String getReason() { return reason; }
}
