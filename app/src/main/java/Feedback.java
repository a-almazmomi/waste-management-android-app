package com.example.project;

public class Feedback {
    private String id;
    private String feedback;
    private String preferredTime;

    public Feedback() {
        // Default constructor required for calls to DataSnapshot.getValue(Feedback.class)
    }

    public Feedback(String id, String feedback, String preferredTime) {
        this.id = id;
        this.feedback = feedback;
        this.preferredTime = preferredTime;
    }

    // Getters and setters (optional)
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getFeedback() { return feedback; }
    public void setFeedback(String feedback) { this.feedback = feedback; }
    public String getPreferredTime() { return preferredTime; }
    public void setPreferredTime(String preferredTime) { this.preferredTime = preferredTime; }
}