package com.cursospringalura.ScreenMatch.servicios.gemini.modelos;

import java.util.List;

// Clase Content
public class Content {
    private List<Part> parts;
    private String role;

    // Getters y Setters
    public List<Part> getParts() {
        return parts;
    }

    public void setParts(List<Part> parts) {
        this.parts = parts;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
