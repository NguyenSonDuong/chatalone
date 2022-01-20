package com.nguyenduong.chatalone;

import com.nguyenduong.chatalone.model.User;
import com.nguyenduong.chatalone.responstory.UserRepository;
import com.nimbusds.jose.shaded.json.JSONArray;
import com.nimbusds.jose.shaded.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RequestMapping("/api/message")
@RestController()
public class MessageController {

    @Autowired
    private UserRepository userRepository;

    @PreAuthorize("hasAnyAuthority('MESSAGE')")
    @RequestMapping(value = "/user",method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE},consumes = MediaType.ALL_VALUE)
    public ResponseEntity<?> GetListUser(@RequestParam("birthdaymin") int birthdayMin,@RequestParam("birthdaymax") int birthdayMax,@RequestParam("sex") int sex){
        List<User> listUser = userRepository.GetUserBirthdayAndSex(birthdayMin,birthdayMax,sex);
        JSONArray jsonArray = new JSONArray();
        for (User user : listUser) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("username",user.getUsername());
            jsonObject.put("email",user.getEmail());
            jsonObject.put("birthday",user.getUserInfo().getDateOfBirth());
            jsonObject.put("sex",user.getUsername());
//            jsonArray.appendElement();
        }
        return  ResponseEntity.ok(listUser);
    }
}

