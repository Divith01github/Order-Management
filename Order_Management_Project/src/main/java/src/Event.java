package src;

import java.time.Instant;

abstract class Event {
    protected String eventId;
    protected Instant timestamp;
    protected String eventType;

    public Event(String eventId, Instant timestamp, String eventType) {
        this.eventId = eventId;
        this.timestamp = timestamp;
        this.eventType = eventType;
    }

    public String getEventType() { return eventType; }
    public String getEventId() { return eventId; }
}
