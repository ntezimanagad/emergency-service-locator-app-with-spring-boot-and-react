package com.emergence.locator.app.emergence.dto;

import com.emergence.locator.app.emergence.model.Role;

public class UserDTO {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private Role role;
    private String password;
    private double latitude;
    private double longitude;
    private String otpCode;

    public UserDTO() {
    }
    

    public UserDTO(String name, String email, String phone, Role role, String password, double latitude,
            double longitude) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.role = role;
        this.password = password;
        this.latitude = latitude;
        this.longitude = longitude;
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



    // Getters and Setters
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


    public String getOtpCode() {
        return otpCode;
    }


    public void setOtpCode(String otpCode) {
        this.otpCode = otpCode;
    }
}
