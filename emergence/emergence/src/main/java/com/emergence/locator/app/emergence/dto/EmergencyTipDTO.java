package com.emergence.locator.app.emergence.dto;

public class EmergencyTipDTO {
    private Long id;
    private String title;
    private String content;
    private String category;

    
    public EmergencyTipDTO() {
    }

    public EmergencyTipDTO(String title, String content, String category) {
        this.title = title;
        this.content = content;
        this.category = category;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}