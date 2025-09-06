package com.example.orderprocessing.events;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class UnknownEvent extends BaseEvent {
    private String raw;

    public String getRaw() { return raw; }
    public void setRaw(String raw) { this.raw = raw; }

    @JsonIgnore
    public String getSummary() {
        return "Unknown event type: " + getEventType();
    }
}
