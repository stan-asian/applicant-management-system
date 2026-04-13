package com.lab.narra.ams.service.token;

import com.lab.narra.ams.repository.TokenRepository;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lab.narra.ams.mapper.TokenMapper;
import com.lab.narra.ams.model.dto.TokenDTO;
import com.lab.narra.ams.model.entity.Token;
import com.lab.narra.ams.model.entity.User;

@Service
public class TokenServiceImp {

    private final TokenRepository tokenRepository;
    private final TokenMapper tokenMapper;

    public TokenServiceImp(TokenRepository tokenRepository, TokenMapper tokenMapper) {
        this.tokenRepository = tokenRepository;
        this.tokenMapper = tokenMapper;
    }

    public void saveToken(TokenDTO tokenDto, User user) {

        // Expiry date is 15 minutes after the token is generated
        tokenDto.setExpiryDate(LocalDateTime.now().plusMinutes(15));
        tokenDto.setUser(user);

        Token token = tokenMapper.DTOtoToken(tokenDto);
        tokenRepository.save(token);
    }

    public Token getTokenByTokenString(String tokenString) {
        return tokenRepository.findByToken(tokenString);
    }

    // Delete token for completed operations such as password reset or email
    // verification
    public void deleteToken(Token token) {
        tokenRepository.delete(token);
    }

    // Delete expired tokens from the database to prevent it from being cluttered
    // with expired tokens
    // transactional annotation is used to ensure that the delete operation is
    // atomic and consistent, preventing potential issues with concurrent access to
    // the database.
    @Transactional
    public void deleteExpiredTokens() {
        tokenRepository.deleteByExpiryDateBefore(LocalDateTime.now());
    }

    public String generateToken() {
        return UUID.randomUUID().toString();
    }

}
