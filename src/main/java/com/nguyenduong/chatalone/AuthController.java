package com.nguyenduong.chatalone;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nguyenduong.chatalone.model.*;
import com.nguyenduong.chatalone.model.responsive.Responsive;
import com.nguyenduong.chatalone.responstory.TokenRepository;
import com.nguyenduong.chatalone.responstory.UserRepository;
import com.nguyenduong.chatalone.service.JwtUtil;
import com.nguyenduong.chatalone.service.UserServiceImpl;
import com.nimbusds.jose.shaded.json.JSONObject;
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

import java.util.Date;
import java.util.Map;

@Controller
@RequestMapping("/api/v1")
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
        User user1 = userRepository.findByUsername(user.getUsername());
        UserPrincipal userPrincipal = userService.findByUsername(user1);
        System.out.println(userPrincipal.getUsername());
        if (null == user || !new BCryptPasswordEncoder().matches(user.getPassword(), userPrincipal.getPassword())) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("status","error");
            jsonObject.put("title","Wrong username or password");
            jsonObject.put("content",null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(jsonObject);
        }
        Token token = new Token();
        token.setToken(jwtUtil.generateToken(userPrincipal));
        token.setTokenExpDate(jwtUtil.generateExpirationDate());
        token.setCreatedBy(userPrincipal.getUserId());
        System.out.println(token.getToken());
        tokenService.saveAndFlush(token);
        JSONObject jo = new JSONObject();
        JSONObject jsonUser = new JSONObject();
        jsonUser.put("username",user1.getUsername());
        jsonUser.put("email",user1.getEmail());
        jsonUser.put("id",user1.getId());
        jo.put("details", jsonUser);
        jo.put("token", "Token "+token.getToken());
        jo.put("create_at", token.getCreatedAt());
        userRepository.findByUsername(userPrincipal.getUsername()).getUserInfo().setStatus(UserInfo.StatusUser.ONLINE);
        userRepository.flush();
        return ResponseEntity.ok(Responsive.SuccessResponsive("Login success!",jo));
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_FORM_URLENCODED_VALUE},consumes = MediaType.ALL_VALUE)
    public ResponseEntity<?> Register(@RequestBody JSONObject user){
        try{
            String password = user.getAsString("password");
            user.replace("password",new BCryptPasswordEncoder().encode(password));
            User check = userService.createUser(Role.RoleKey.USER,user);
            if(check == null){
                return  ResponseEntity.status(HttpStatus.CONFLICT).body(Responsive.ErrorResponsive("Account already exists, please try again",2001));
            }
            else
                return ResponseEntity.ok(Responsive.SuccessResponsive("Register success",check));
        }catch (Exception ex){
            return  ResponseEntity.badRequest().body(Responsive.ErrorResponsive("An unknown error",1001));
        }
    }
    @GetMapping("/hello")
    @PreAuthorize("hasAnyAuthority('READ_USER')")
    public ResponseEntity hello(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserPrincipal userPrincipal = null;
        if (principal instanceof UserDetails) {
            userPrincipal = (UserPrincipal) principal;
        }
        return ResponseEntity.ok(userPrincipal == null ?"hello": userPrincipal);
    }
}
