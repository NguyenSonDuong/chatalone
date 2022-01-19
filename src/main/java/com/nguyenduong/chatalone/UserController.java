package com.nguyenduong.chatalone;

import com.google.gson.Gson;
import com.nguyenduong.chatalone.model.*;
import com.nguyenduong.chatalone.responstory.UserRepository;
import com.nguyenduong.chatalone.service.UserService;
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
import java.util.HashSet;

@RequestMapping("/api/user")
@RestController()
public class UserController {

    @Autowired
    private UserServiceImpl userRepository;

    @Autowired
    private  UserRepository userRepositoryNoService;

    @RequestMapping(value = "/register", method = RequestMethod.POST,
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = MediaType.ALL_VALUE)
    public ResponseEntity<?> register(@RequestBody JSONObject user){
        System.out.println(user.toString());
        String pass = user.getAsString("password");
        user.replace("password",new BCryptPasswordEncoder().encode(pass));
        User check = userRepository.createUser("USER",user);
        System.out.println(check);
        if(check == null){
            JSONObject json = new JSONObject();
            json.put("error","Tài khoản đã tồn tại vui lòng thay đổi thông tin");
            json.put("status",409);
            json.put("create_at",new Date().toString());
            return  ResponseEntity.status(HttpStatus.CONFLICT).body(json);
        }
        else
            return ResponseEntity.ok(check);
    }


    @PreAuthorize("hasAnyAuthority('READ_USER')")
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public  ResponseEntity GetInfoUser(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserPrincipal userPrincipal = null;
        if (principal instanceof UserDetails) {
            userPrincipal = (UserPrincipal) principal;
        }
        userRepositoryNoService.findByUsername(userPrincipal.getUsername());
        return ResponseEntity.ok(HelpController.GetSuccess("Block thành công",userRepositoryNoService.findByUsername(userPrincipal.getUsername())));
    }

    @PreAuthorize("hasAnyAuthority('UPDATE_USER')")
    @RequestMapping(value = "/block", method = RequestMethod.POST)
    public  ResponseEntity GetAda(@RequestParam("id") int id){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserPrincipal userPrincipal = null;
        if (principal instanceof UserDetails) {
            userPrincipal = (UserPrincipal) principal;
        }
        userRepositoryNoService.findByUsername(userPrincipal.getUsername()).getUserInfo().getListBlocker().add(new Blocker(id));
        return ResponseEntity.ok(HelpController.GetSuccess("Block thành công",userPrincipal));
    }
}
