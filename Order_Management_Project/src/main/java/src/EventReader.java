package src;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.FileReader;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class EventReader {

    private ObjectMapper mapper = new ObjectMapper();

    public List<Event> readEvents(String filename) {
        List<Event> events = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                JsonNode node = mapper.readTree(line);
                String type = node.get("eventType").asText();
                String eventId = node.get("eventId").asText();
                Instant timestamp = Instant.parse(node.get("timestamp").asText());

                switch (type) {
                    case "OrderCreated":
                        events.add(new OrderCreatedEvent(
                                eventId,
                                timestamp,
                                node.get("orderId").asText(),
                                node.get("customerId").asText(),
                                node.get("totalAmount").asDouble()
                        ));
                        break;

                    case "PaymentReceived":
                        events.add(new PaymentReceivedEvent(
                                eventId,
                                timestamp,
                                node.get("orderId").asText(),
                                node.get("amountPaid").asDouble()
                        ));
                        break;

                    case "ShippingScheduled":
                        events.add(new ShippingScheduledEvent(
                                eventId,
                                timestamp,
                                node.get("orderId").asText(),
                                node.get("shippingDate").asText()
                        ));
                        break;

                    case "OrderCancelled":
                        events.add(new OrderCancelledEvent(
                                eventId,
                                timestamp,
                                node.get("orderId").asText(),
                                node.get("reason").asText()
                        ));
                        break;

                    default:
                        System.out.println("[WARN] Unknown event type: " + type);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return events;
    }
}
