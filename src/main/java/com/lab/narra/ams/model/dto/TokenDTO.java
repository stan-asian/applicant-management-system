package com.lab.narra.ams.model.dto;

import java.time.LocalDateTime;

public class TokenDTO {
    private Long tokenId;
    private String token;
    private LocalDateTime expiryDate;


    public Long getTokenId() {
        return tokenId;
    }
    public void setTokenId(Long tokenId) {
        this.tokenId = tokenId;
    }

    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }
    
    public LocalDateTime getExpiryDate() {
        return expiryDate;
    }
    public void setExpiryDate(LocalDateTime expiryDate) {
        this.expiryDate = expiryDate;
    }
    
}
