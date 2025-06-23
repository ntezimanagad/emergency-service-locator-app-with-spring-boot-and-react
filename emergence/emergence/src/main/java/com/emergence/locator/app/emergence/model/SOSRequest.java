package com.emergence.locator.app.emergence.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

import org.locationtech.jts.geom.Point;

@Entity
@Table(name = "sos_request")
public class SOSRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(columnDefinition = "geometry(Point, 4326)")
    private org.locationtech.jts.geom.Point location;

    private String status;

    private LocalDateTime timestamp;

    // === Getters and Setters ===

    public SOSRequest() {
    }

    public SOSRequest(User user, Point location, String status, LocalDateTime timestamp) {
        this.user = user;
        this.location = location;
        this.status = status;
        this.timestamp = timestamp;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public org.locationtech.jts.geom.Point getLocation() {
        return location;
    }

    public void setLocation(org.locationtech.jts.geom.Point location) {
        this.location = location;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
