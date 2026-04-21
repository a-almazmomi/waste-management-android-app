package com.example.project;

public class WasteReport {
    private String id;
    private String wasteId;
    private String district;

    public WasteReport() {
        // Default constructor required for calls to DataSnapshot.getValue(WasteReport.class)
    }

    public WasteReport(String id, String wasteId, String district) {
        this.id = id;
        this.wasteId = wasteId;
        this.district = district;
    }

    // Getters and setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getWasteId() { return wasteId; }
    public void setWasteId(String wasteId) { this.wasteId = wasteId; }
    public String getDistrict() { return district; }
    public void setDistrict(String district) { this.district = district; }
}