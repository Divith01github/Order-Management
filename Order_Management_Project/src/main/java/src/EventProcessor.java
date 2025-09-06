package src;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventProcessor {
    private Map<String, Order> orders = new HashMap<>();
    private List<Observer> observers = new ArrayList<>();

    public void registerObserver(Observer obs) {
        observers.add(obs);
    }

    private void notifyObservers(String message) {
        for (Observer obs : observers) {
            obs.notify(message);
        }
    }

    public void process(Event event) {
        switch (event.getEventType()) {
            case "OrderCreated":
                OrderCreatedEvent oce = (OrderCreatedEvent) event;
                Order order = new Order(oce.getOrderId(), oce.getCustomerId(), oce.getTotalAmount());
                orders.put(oce.getOrderId(), order);
                order.addEvent("Order created");
                notifyObservers("Order " + oce.getOrderId() + " created with status PENDING");
                break;

            case "PaymentReceived":
                PaymentReceivedEvent pre = (PaymentReceivedEvent) event;
                Order o1 = orders.get(pre.getOrderId());
                if (o1 != null) {
                    o1.setStatus("PAID");
                    o1.addEvent("Payment received");
                    notifyObservers("Order " + pre.getOrderId() + " marked as PAID");
                }
                break;

            case "ShippingScheduled":
                ShippingScheduledEvent sse = (ShippingScheduledEvent) event;
                Order o2 = orders.get(sse.getOrderId());
                if (o2 != null) {
                    o2.setStatus("SHIPPED");
                    o2.addEvent("Shipping scheduled");
                    notifyObservers("Order " + sse.getOrderId() + " marked as SHIPPED");
                }
                break;

            case "OrderCancelled":
                OrderCancelledEvent oce2 = (OrderCancelledEvent) event;
                Order o3 = orders.get(oce2.getOrderId());
                if (o3 != null) {
                    o3.setStatus("CANCELLED");
                    o3.addEvent("Cancelled: " + oce2.getReason());
                    notifyObservers("Order " + oce2.getOrderId() + " CANCELLED due to " + oce2.getReason());
                }
                break;

            default:
                System.out.println("[WARN] Unknown event type: " + event.getEventType());
        }
    }

    public void printAllOrders() {
        for (Order o : orders.values()) {
            System.out.println("Order " + o.getOrderId() + " final status: " + o.getStatus());
            o.printHistory();
        }
    }
}
