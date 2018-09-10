package com.maple.services;

/**
 * desc: EventStore
 *
 * @author hz.lei
 * @since 2018年08月03日 下午3:30
 */
public class EventStore {

    private Long id;
    private String eventType;
    private byte[] eventBinary;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public byte[] getEventBinary() {
        return eventBinary;
    }

    public void setEventBinary(byte[] eventBinary) {
        this.eventBinary = eventBinary;
    }
}
