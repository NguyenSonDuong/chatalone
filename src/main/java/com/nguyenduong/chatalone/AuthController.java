package com.nguyenduong.chatalone;

import com.nguyenduong.chatalone.model.Token;
import com.nguyenduong.chatalone.model.User;
import com.nguyenduong.chatalone.model.UserPrincipal;
import com.nguyenduong.chatalone.responstory.TokenRepository;
import com.nguyenduong.chatalone.responstory.UserRepository;
import com.nguyenduong.chatalone.service.JwtUtil;
import com.nguyenduong.chatalone.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RestController
public class AuthController {
    @Autowired
    private TokenRepository tokenService;

    private JwtUtil jwtUtil = new JwtUtil();

    private UserServiceImpl userService = new UserServiceImpl();
    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "/login", method = RequestMethod.POST,
            produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_FORM_URLENCODED_VALUE},
            consumes = MediaType.ALL_VALUE)
    public ResponseEntity<?> login(@RequestBody User user){
        System.out.println(user.getUsername());
        System.out.println(userRepository.findByUsername(user.getUsername()).getPassword());
        UserPrincipal userPrincipal = userService.findByUsername(userRepository.findByUsername(user.getUsername()));
        System.out.println(userPrincipal.getUsername());
        if (null == user || !new BCryptPasswordEncoder().matches(user.getPassword(), userPrincipal.getPassword())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("tài khoản hoặc mật khẩu không chính xác");
        }
        Token token = new Token();
        token.setToken(jwtUtil.generateToken(userPrincipal));
        token.setTokenExpDate(jwtUtil.generateExpirationDate());
        token.setCreatedBy(userPrincipal.getUserId());
        System.out.println(token.getToken());
        tokenService.saveAndFlush(token);
        return ResponseEntity.ok(token.getToken());
    }

    @GetMapping("/hello")
    @PreAuthorize("hasAnyAuthority('USER_REVIEW')")
    public ResponseEntity hello(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserPrincipal userPrincipal = null;
        if (principal instanceof UserDetails) {
            userPrincipal = (UserPrincipal) principal;
        }
        return ResponseEntity.ok(userPrincipal == null ?"hello": userPrincipal);
    }
}
