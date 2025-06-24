package com.emergence.locator.app.emergence.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

import org.locationtech.jts.geom.Point;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String phone;
    @Enumerated(EnumType.STRING)
    private Role role;
    private String password;
    @Column(columnDefinition = "geometry(Point, 4326)")
    private org.locationtech.jts.geom.Point location;

    private LocalDateTime createdAt;
    public User() {
    }

    

    public User(String name, String email, String phone, Role role, String password, Point location,
            LocalDateTime createdAt) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.role = role;
        this.password = password;
        this.location = location;
        this.createdAt = createdAt;
    }
    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public org.locationtech.jts.geom.Point getLocation() {
        return location;
    }

    public void setLocation(org.locationtech.jts.geom.Point location) {
        this.location = location;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}