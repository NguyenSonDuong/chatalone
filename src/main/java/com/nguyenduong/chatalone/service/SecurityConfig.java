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


        http.csrf().disable().authorizeRequests().antMatchers(
                "/api/v1/register",
                "/api/v1/login").permitAll().and().formLogin().failureHandler(new CustomAuthenticationFailureHandler());
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }

}
