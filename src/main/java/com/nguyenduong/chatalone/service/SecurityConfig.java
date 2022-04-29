package com.nguyenduong.chatalone.service;


import com.nguyenduong.chatalone.JwtRequestFilter;
import com.nguyenduong.chatalone.responstory.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Service;

@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    TokenRepository tokenRepository;

    private JwtRequestFilter jwtRequestFilter = new JwtRequestFilter();



    @Override
    public void configure(HttpSecurity http) throws Exception {
        jwtRequestFilter.setVerificationTokenService(tokenRepository);
        http.csrf().disable().authorizeRequests().antMatchers("/api/user/register","/api/login","/api/customer/login","/api/customer/register").permitAll();
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }

}
