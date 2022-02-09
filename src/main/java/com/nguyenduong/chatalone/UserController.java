package com.nguyenduong.chatalone;

import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.google.gson.Gson;
import com.nguyenduong.chatalone.model.*;
import com.nguyenduong.chatalone.responstory.UserRepository;
import com.nguyenduong.chatalone.service.FCMService;
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

    @Autowired
    private FCMService fcmService;

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
        User user1 = userRepositoryNoService.findByUsername(userPrincipal.getUsername());
        JSONObject jo = new JSONObject();
        jo.put("id", userPrincipal.getUserId());
        jo.put("data", user1);
        jo.put("token", "");
        jo.put("create_at", "");
        return ResponseEntity.ok(jo);
    }
    @PreAuthorize("hasAnyAuthority('READ_USER')")
    @RequestMapping(value = "/infouser", method = RequestMethod.GET)
    public  ResponseEntity GetInfoUser(@RequestParam(value = "username")String username){
        User user1 = userRepositoryNoService.findByUsername(username);
        if(user1==null){
            return ResponseEntity.notFound().build();
        }
        JSONObject jo = new JSONObject();
        jo.put("birthday", user1.getUserInfo().getDateOfBirth());
        jo.put("sex", user1.getUserInfo().getSex());
        jo.put("email", user1.getEmail());
        jo.put("username", user1.getUsername());
        jo.put("point", user1.getUserInfo().getPoint());
        jo.put("status", user1.getUserInfo().getStatus());
        return ResponseEntity.ok(jo);
    }
    @PreAuthorize("hasAnyAuthority('MESSAGE')")
    @RequestMapping(value = "/notification", method = RequestMethod.POST)
    public String  PushNotification(@RequestParam(value = "username") String username){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserPrincipal userPrincipal = null;
        if (principal instanceof UserDetails) {
            userPrincipal = (UserPrincipal) principal;
        }
        User user = userRepositoryNoService.findByUsername(username);
        PnsRequest pnsRequest = new PnsRequest();
        pnsRequest.setFcmToken(user.getUserInfo().getTokenfb());
        pnsRequest.setTitle("["+username+"] có người muốn kết nối với bạn");
        pnsRequest.setContent("["+userPrincipal.getUsername()+"] gửi một lời mời nhắn tin");
        return fcmService.pushNotification(pnsRequest);
    }
    @PreAuthorize("hasAnyAuthority('MESSAGE')")
    @RequestMapping(value = "/notificationfeedback", method = RequestMethod.POST)
    public String  PushNotificationFeedback(@RequestParam(value = "username") String username,@RequestParam(value = "status") String status){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserPrincipal userPrincipal = null;
        if (principal instanceof UserDetails) {
            userPrincipal = (UserPrincipal) principal;
        }
        User user = userRepositoryNoService.findByUsername(username);
        PnsRequest pnsRequest = new PnsRequest();
        pnsRequest.setFcmToken(user.getUserInfo().getTokenfb());
        pnsRequest.setTitle("["+username+"] "+ userPrincipal.getUsername() +" "+(status.equals("OK") ? "Chấp nhận" :(status.equals("CLOSE")? "Ngắt" :"Không chấp nhận") )+" kết nối với bạn");
        pnsRequest.setContent(status);
        return fcmService.pushNotification(pnsRequest);
    }

    @PreAuthorize("hasAnyAuthority('UPDATE_USER')")
    @RequestMapping(value = "/block", method = RequestMethod.POST)
    public  ResponseEntity GetAda(@RequestParam("id") int id,@RequestParam("name") String name){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserPrincipal userPrincipal = null;
        if (principal instanceof UserDetails) {
            userPrincipal = (UserPrincipal) principal;
        }

        userRepositoryNoService.findByUsername(userPrincipal.getUsername()).getUserInfo().getListBlocker().add(new Blocker(id,name,new Date()));
        userRepositoryNoService.flush();

        return ResponseEntity.ok(HelpController.GetSuccess("Block thành công",userPrincipal));
    }

    @PreAuthorize("hasAnyAuthority('MESSAGE')")
    @RequestMapping(value = "/token", method = RequestMethod.POST)
    public  ResponseEntity SetToken(@RequestParam("tokens") String token){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserPrincipal userPrincipal = null;
        if (principal instanceof UserDetails) {
            userPrincipal = (UserPrincipal) principal;
        }
        userRepositoryNoService.findByUsername(userPrincipal.getUsername()).getUserInfo().setTokenfb(token);
        userRepositoryNoService.flush();

        User user1 = userRepositoryNoService.findByUsername(userPrincipal.getUsername());
        JSONObject jo = new JSONObject();
        jo.put("birthday", user1.getUserInfo().getDateOfBirth());
        jo.put("sex", user1.getUserInfo().getSex());
        jo.put("email", user1.getEmail());
        jo.put("username", user1.getUsername());
        jo.put("point", user1.getUserInfo().getPoint());
        jo.put("status", user1.getUserInfo().getStatus());

        return ResponseEntity.ok(HelpController.GetSuccess("Cài token thành công",jo));
    }

    @PreAuthorize("hasAnyAuthority('UPDATE_USER')")
    @RequestMapping(value = "/evaluate", method = RequestMethod.POST)
    public  ResponseEntity SetRank(@RequestParam("id") int id,@RequestParam("point") int point){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserPrincipal userPrincipal = null;
        if (principal instanceof UserDetails) {
            userPrincipal = (UserPrincipal) principal;
        }
        try{
            userRepositoryNoService.findById(id).get().getUserInfo().getEvaluates().add(new Evaluate(userPrincipal.getUserId(),point));
            userRepositoryNoService.findById(id).get().getUserInfo().setPoint((((float)userRepositoryNoService.findById(id).get().getUserInfo().getPoint()+(float)point)/userRepositoryNoService.findById(id).get().getUserInfo().getEvaluates().toArray().length));
            userRepositoryNoService.flush();
            return ResponseEntity.ok(HelpController.GetSuccess("Đánh giá người dùng thành công",null));
        }catch (Exception ex){
            return  ResponseEntity.badRequest().body(HelpController.GetError("Error: "+ ex.getMessage(),400));
        }

    }



}
