package com.lab.narra.ams.service.token;

import com.lab.narra.ams.repository.TokenRepository;
import org.springframework.stereotype.Service;

import com.lab.narra.ams.mapper.TokenMapper;
import com.lab.narra.ams.model.dto.TokenDTO;
import com.lab.narra.ams.model.entity.Token;

@Service
public class TokenServiceImp {
   
    private final TokenRepository tokenRepository;
    private final TokenMapper tokenMapper;

    public TokenServiceImp(TokenRepository tokenRepository, TokenMapper tokenMapper){
        this.tokenRepository = tokenRepository;
        this.tokenMapper = tokenMapper;
    }

    public void saveToken(TokenDTO tokenDto){
        Token token = tokenMapper.DTOtoToken(tokenDto);
        tokenRepository.save(token);
    }

}
