package com.lab.narra.ams.model.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "token_tbl")
public class Token {
    
    @Id
    private Long tokenId;

    // Here you can also fetch user using token id
    @OneToOne
    @MapsId
    @JoinColumn(name = "token_id")
    private User user;

    @Column(name = "token", updatable = false)
    private String token;

    @Column(name = "expiry_date", updatable = false)
    private LocalDateTime expiryDate;

    public Long getTokenId() {
        return tokenId;
    }

    public Token setTokenId(Long tokenId) {
        this.tokenId = tokenId;
        return this;
    }

    public String getToken() {
        return token;
    }

    public Token setToken (String token) {
        this.token = token;
        return this;
    }

    public LocalDateTime getExpiryDate() {
        return expiryDate;
    }

    public Token setExpiryDate(LocalDateTime expiryDate) {
        this.expiryDate = expiryDate;
        return this;
    }


}
