package com.nguyenduong.chatalone;

import com.google.gson.Gson;
import com.nguyenduong.chatalone.model.Role;
import com.nguyenduong.chatalone.model.User;
import com.nguyenduong.chatalone.responstory.UserRepository;
import com.nguyenduong.chatalone.service.UserService;
import com.nguyenduong.chatalone.service.UserServiceImpl;
import com.nimbusds.jose.shaded.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;


@RestController()
public class UserController {

    @Autowired
    private UserServiceImpl userRepository;

    @RequestMapping(value = "/register", method = RequestMethod.POST,
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = MediaType.ALL_VALUE)
    public User register(@RequestBody JSONObject user){
        System.out.println(user.toString());
        user.replace("password",new BCryptPasswordEncoder().encode(user.getAsString("password")));
        return userRepository.createUser("USER",user);
    }

    @RequestMapping(value = "/xinchao", method = RequestMethod.GET)
    public  String GetAda(){
        return  "Xin choa";
    }
}
