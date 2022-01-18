package com.nguyenduong.chatalone;

import com.nguyenduong.chatalone.model.User;
import com.nguyenduong.chatalone.responstory.UserRepository;
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
    public ResponseEntity<?> GetListUser(@RequestParam("birthday") int birthday){
        List<User> listUser = userRepository.GetBirthday(birthday);
        return  ResponseEntity.ok(listUser);
    }
}
