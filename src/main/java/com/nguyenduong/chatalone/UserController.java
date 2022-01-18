package com.nguyenduong.chatalone;

import com.google.gson.Gson;
import com.nguyenduong.chatalone.model.Role;
import com.nguyenduong.chatalone.model.User;
import com.nguyenduong.chatalone.responstory.UserRepository;
import com.nguyenduong.chatalone.service.UserService;
import com.nguyenduong.chatalone.service.UserServiceImpl;
import com.nimbusds.jose.shaded.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashSet;


@RestController()
public class UserController {

    @Autowired
    private UserServiceImpl userRepository;

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

    @RequestMapping(value = "/xinchao", method = RequestMethod.GET)
    public  String GetAda(){
        return  "Xin choa";
    }
}
