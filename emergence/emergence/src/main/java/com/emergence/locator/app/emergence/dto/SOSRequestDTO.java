package com.emergence.locator.app.emergence.dto;

public class SOSRequestDTO {
    private Long id;
    private Long userId;
    private double latitude;
    private double longitude;
    private String status;

    public SOSRequestDTO() {
    }

    public SOSRequestDTO(Long userId, double latitude, double longitude, String status) {
        this.userId = userId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.status = status;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
