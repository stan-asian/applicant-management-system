package com.lab.narra.ams.model.dto;

import java.time.LocalDateTime;

import com.lab.narra.ams.model.entity.User;

public class TokenDTO {
    private String token;
    private LocalDateTime expiryDate;
    private boolean used;
    private User user;

    // Getters and Setters

    public String getToken() {
        return token;
    }

    public TokenDTO setToken(String token) {
        this.token = token;
        return this;

    }

    public LocalDateTime getExpiryDate() {
        return expiryDate;
    }

    public TokenDTO setExpiryDate(LocalDateTime expiryDate) {
        this.expiryDate = expiryDate;
        return this;

    }

    public boolean isUsed() {
        return used;
    }

    public TokenDTO setUsed(boolean used) {
        this.used = used;
        return this;

    }

    public User getUser() {
        return user;
    }

    public TokenDTO setUser(User user) {
        this.user = user;
        return this;

    }
}
