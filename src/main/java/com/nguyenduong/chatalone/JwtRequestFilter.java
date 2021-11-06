package com.nguyenduong.chatalone;

import com.nguyenduong.chatalone.model.Token;
import com.nguyenduong.chatalone.model.UserPrincipal;
import com.nguyenduong.chatalone.responstory.TokenRepository;
import com.nguyenduong.chatalone.service.JwtUtil;
import com.nguyenduong.chatalone.service.TokenService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
public class JwtRequestFilter extends OncePerRequestFilter {

    private JwtUtil jwtUtil = new JwtUtil();

    public JwtRequestFilter(TokenRepository verificationTokenService) {
        this.verificationTokenService = verificationTokenService;
    }

    public JwtRequestFilter() {
    }

    private TokenRepository verificationTokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authorizationHeader = request.getHeader("Authorization");

        UserPrincipal user = null;
        Token token = null;
        if (StringUtils.hasText(authorizationHeader) && authorizationHeader.startsWith("Token ")) {
            String jwt = authorizationHeader.substring(6);
            user = jwtUtil.getUserFromToken(jwt);
            token = verificationTokenService.findByToken(jwt);
        }
        if (null != user && null != token && token.getTokenExpDate().after(new Date())) {
            Set<GrantedAuthority> authorities = new HashSet<>();
            for (Object role : user.getAuthorities().toArray()) {
                System.out.println(role);
                authorities.add(new SimpleGrantedAuthority(role.toString()));
            }
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(user, null, authorities);
            System.out.println(authentication.getName());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }
}
