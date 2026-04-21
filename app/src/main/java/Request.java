package com.example.project;

public class Request {
    private String id;
    private String description;
    private String location;
    private String type;
    private String urgency;
    private String contact;
    private String source;

    public Request() {
        // Default constructor required for calls to DataSnapshot.getValue(Request.class)
    }

    public Request(String id, String description, String location, String type, String urgency, String contact, String source) {
        this.id = id;
        this.description = description;
        this.location = location;
        this.type = type;
        this.urgency = urgency;
        this.contact = contact;
        this.source = source;
    }

    // Getters and setters (optional)
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getUrgency() { return urgency; }
    public void setUrgency(String urgency) { this.urgency = urgency; }
    public String getContact() { return contact; }
    public void setContact(String contact) { this.contact = contact; }
    public String getSource() { return source; }
    public void setSource(String source) { this.source = source; }
}