package com.lab.narra.ams.service.token;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class TokenCleanupScheduler {

    @Autowired
    private TokenServiceImp tokenService;

    @Scheduled(fixedDelay = 120000) // every 3 minutes conducts cleanup
    public void cleanupExpiredTokens() {
        System.out.println("Running cleanup at: " + LocalDateTime.now());
        tokenService.deleteExpiredTokens();
    }
}