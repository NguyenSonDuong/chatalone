package com.nguyenduong.chatalone.service;

import com.nguyenduong.chatalone.model.Token;
import com.nguyenduong.chatalone.responstory.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TokenService {

    @Autowired
    private TokenRepository verificationTokenService;

    public Token findToken(String token){
        return verificationTokenService.findByToken(token);
    }
}
