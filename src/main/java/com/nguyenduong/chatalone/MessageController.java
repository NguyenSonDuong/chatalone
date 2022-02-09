package com.nguyenduong.chatalone;

import com.nguyenduong.chatalone.model.User;
import com.nguyenduong.chatalone.model.UserPrincipal;
import com.nguyenduong.chatalone.responstory.UserRepository;
import com.nimbusds.jose.shaded.json.JSONArray;
import com.nimbusds.jose.shaded.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;


@RequestMapping("/api/message")
@RestController()
public class MessageController {

    @Autowired
    private UserRepository userRepository;

    @PreAuthorize("hasAnyAuthority('MESSAGE')")
    @RequestMapping(value = "/users",method = RequestMethod.POST,
            produces = {MediaType.APPLICATION_JSON_VALUE},consumes = MediaType.ALL_VALUE)
    public ResponseEntity<?> GetListUser(@RequestParam("birthdaymin") int birthdayMin,@RequestParam("birthdaymax") int birthdayMax,@RequestParam(value = "sex",defaultValue = "-1") int sex ){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserPrincipal userPrincipal = null;
        if (principal instanceof UserDetails) {
            userPrincipal = (UserPrincipal) principal;
        }
        if(userPrincipal==null)
            return ResponseEntity.badRequest().body("");
        List<User> listUser = null;
        if(sex<0)
          listUser = userRepository.GetBirthday(birthdayMin,birthdayMax);
        else
          listUser = userRepository.GetUserBirthdayAndSex(birthdayMin,birthdayMax,sex);

        UserPrincipal finalUserPrincipal = userPrincipal;
        listUser.removeIf(new Predicate<User>() {
            @Override
            public boolean test(User user) {
                return user.getUsername().equals(finalUserPrincipal.getUsername());
            }
        });
        listUser.sort(new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
                return o1.getUserInfo().getPoint() > o2.getUserInfo().getPoint() ? 1 : (o1.getUserInfo().getPoint() < o2.getUserInfo().getPoint() ? -1 : 0);
            }
        });
        JSONArray jsonArray = new JSONArray();
        for (User user : listUser) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("username",user.getUsername());
            jsonObject.put("email",user.getEmail());
            jsonObject.put("birthday",user.getUserInfo().getDateOfBirth());
            jsonObject.put("sex",user.getUserInfo().getSex());
            jsonObject.put("point",user.getUserInfo().getPoint());
            jsonObject.put("status",user.getUserInfo().getStatus());
            jsonArray.appendElement(jsonObject);
        }
        return  ResponseEntity.ok(jsonArray);
    }

//    @PreAuthorize("hasAnyAuthority('MESSAGE')")
//    @RequestMapping(value = "/users",method = RequestMethod.POST,
//            produces = {MediaType.APPLICATION_JSON_VALUE},consumes = MediaType.ALL_VALUE)

}

