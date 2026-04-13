package com.lab.narra.ams.mapper;

import org.springframework.stereotype.Component;

import com.lab.narra.ams.model.dto.TokenDTO;
import com.lab.narra.ams.model.entity.Token;

@Component
public class TokenMapper {

    public Token DTOtoToken(TokenDTO tokenDto) {
        return new Token()
                .setToken(tokenDto.getToken())
                .setExpiryDate(tokenDto.getExpiryDate())
                .setUser(tokenDto.getUser());
    }

}
