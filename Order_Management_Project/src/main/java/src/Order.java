package src;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private String orderId;
    private String customerId;
    private List<String> items = new ArrayList<>();
    private double totalAmount;
    private String status; // PENDING, PAID, SHIPPED, CANCELLED
    private List<String> eventHistory = new ArrayList<>();

    public Order(String orderId, String customerId, double totalAmount) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.totalAmount = totalAmount;
        this.status = "PENDING";
    }

    public String getOrderId() { return orderId; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public void addEvent(String eventDesc) {
        eventHistory.add(eventDesc);
    }

    public void printHistory() {
        System.out.println("History for order " + orderId + ": " + eventHistory);
    }
}
