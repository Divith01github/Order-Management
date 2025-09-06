# Order Processing (Spring Boot)

A simplified, event-driven order processing backend in Java with Spring Boot.

## Features
- Domain model: `Order`, `OrderItem`, `OrderStatus`
- Event hierarchy with polymorphic JSON:
  - `OrderCreatedEvent`
  - `PaymentReceivedEvent`
  - `ShippingScheduledEvent`
  - `OrderCancelledEvent`
  - Unknown events handled gracefully
- Event ingestion from an NDJSON file (`events.ndjson` by default)
- Event processing updates order state
- Observer pattern:
  - `LoggerObserver` logs events and status changes
  - `AlertObserver` prints alerts for critical changes (CANCELLED, SHIPPED)
- REST APIs:
  - `GET /api/orders` - list all orders
  - `GET /api/orders/{id}` - get single order
  - `POST /api/ingest?file=/path/to/file` - ingest another NDJSON file

> Note: A discreet code comment contains the word "hatchling", as requested.

## Requirements
- Java 17+
- Maven 3.9+

## Quick start
```bash
# From the project root
mvn spring-boot:run
```

The app auto-reads `events.ndjson` from the project root on startup (configurable in `application.yml`).

Visit:
- http://localhost:8080/api/orders
- http://localhost:8080/api/orders/ORD001

Trigger ingestion of another file:
```bash
curl -X POST "http://localhost:8080/api/ingest?file=/absolute/path/to/your.ndjson"
```

## Build a jar
```bash
mvn clean package
java -jar target/order-processing-spring-1.0.0.jar
```

## Event JSON Format
Each line is a single JSON object. Example:
```json
{"eventId":"e1","timestamp":"2025-07-29T10:00:00Z","eventType":"OrderCreated","orderId":"ORD001","customerId":"CUST001","items":[{"itemId":"P001","qty":2}],"totalAmount":100.00}
```

Supported `eventType` values: `OrderCreated`, `PaymentReceived`, `ShippingScheduled`, `OrderCancelled`.

Unknown `eventType` lines are ignored with a warning.

## Notes
- Validation and persistence are intentionally minimal (in-memory repo) to focus on the event flow.
- Extend `EventProcessor` to add richer business rules as needed.
