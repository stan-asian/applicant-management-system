package com.lab.narra.ams.repository;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lab.narra.ams.model.entity.Token;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {
    Token findByToken(String token);

    void deleteByExpiryDateBefore(LocalDateTime now);
}
