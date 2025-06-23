package com.emergence.locator.app.emergence.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

import org.locationtech.jts.geom.Point;

@Entity
@Table(name = "emergency_service")
public class EmergencyService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private ServiceType type;

    private String phone;
    private String address;

    @Column(columnDefinition = "geometry(Point, 4326)")
    private org.locationtech.jts.geom.Point location;

    private LocalDateTime createdAt;

    // === Getters and Setters ===

    public EmergencyService() {
    }

    

    public EmergencyService(String name, ServiceType type, String phone, String address, Point location,
            LocalDateTime createdAt) {
        this.name = name;
        this.type = type;
        this.phone = phone;
        this.address = address;
        this.location = location;
        this.createdAt = createdAt;
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ServiceType getType() {
        return type;
    }

    public void setType(ServiceType type) {
        this.type = type;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }



    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }



    public org.locationtech.jts.geom.Point getLocation() {
        return location;
    }



    public void setLocation(org.locationtech.jts.geom.Point location) {
        this.location = location;
    }
}
