package com.nguyenduong.chatalone.responstory;

import com.nguyenduong.chatalone.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<Token, Integer> {
    Token findByToken(String token);
}