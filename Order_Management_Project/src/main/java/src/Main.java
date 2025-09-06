package src;

public class Main {
    public static void main(String[] args) {
        EventProcessor processor = new EventProcessor();
        processor.registerObserver(new LoggerObserver());
        processor.registerObserver(new AlertObserver());

        EventReader reader = new EventReader();
        String filePath = "src/main/resources/events.jsonl"; // path to your file

        for (Event event : reader.readEvents(filePath)) {
            processor.process(event);
        }

        processor.printAllOrders();
    }
}

//import java.time.Instant;

//public class Main {
//    public static void main(String[] args) {
//        EventProcessor processor = new EventProcessor();
//        processor.registerObserver(new LoggerObserver());
//        processor.registerObserver(new AlertObserver());
//
//        // Simulating reading events from file:
//        Event e1 = new OrderCreatedEvent("e1", Instant.now(), "ORD001", "CUST001", 100.0);
//        Event e2 = new PaymentReceivedEvent("e2", Instant.now(), "ORD001", 100.0);
//        Event e3 = new ShippingScheduledEvent("e3", Instant.now(), "ORD001", "2025-07-30");
//        Event e4 = new OrderCancelledEvent("e4", Instant.now(), "ORD002", "Customer cancelled");
//
//        processor.process(e1);
//        processor.process(e2);
//        processor.process(e3);
//        processor.process(e4);
//
//        processor.printAllOrders();
//    }
//}
