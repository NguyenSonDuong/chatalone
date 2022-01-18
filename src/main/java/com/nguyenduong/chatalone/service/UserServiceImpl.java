package com.nguyenduong.chatalone.service;

import com.nguyenduong.chatalone.UserRoleRepository;
import com.nguyenduong.chatalone.model.*;
import com.nguyenduong.chatalone.responstory.RoleRepository;
import com.nguyenduong.chatalone.responstory.UserInfoRepository;
import com.nguyenduong.chatalone.responstory.UserRepository;
import com.nimbusds.jose.shaded.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository roleRepository;

    @Autowired
    private UserInfoRepository userInfoRepository;


    @Autowired
    private RoleRepository roleMainRepository;

    @Override
    public User createUser(String role, JSONObject user) {
        Role rol = new Role(role, (role.equals("USER") ? "Người dùng" : (role.equals("ADMIN") ? "Quản trị viên" : "Khách")));
        int id = roleMainRepository.saveAndFlush(rol).getId();
        User user1 = new User(user.get("username").toString(),user.get("password").toString(),user.get("email").toString());
        user1.setUserInfo(new UserInfo(user.getAsNumber("birthday").intValue(),user.getAsNumber("sex").intValue()));
        User userUpdate = userRepository.saveAndFlush(user1);
        roleRepository.saveAndFlush(new UserRole(user1.getId(),id));
        System.out.println(userRepository);
        return userUpdate;
    }

    @Override
    public UserPrincipal findByUsername( User user) {
        System.out.println(user.getRoles().toArray().length);
        UserPrincipal userPrincipal = new UserPrincipal();
        if (null != user) {
            Set<String> authorities = new HashSet<>();
            if (null != user.getRoles()) user.getRoles().forEach(r -> {
                authorities.add(r.getKey());
                r.getPermissions().forEach(p -> authorities.add(p.getKey()));
            });

            userPrincipal.setUserId(user.getId());
            userPrincipal.setUsername(user.getUsername());
            userPrincipal.setPassword(user.getPassword());
            userPrincipal.setAuthorities(authorities);
        }
        return userPrincipal;
    }

    @Override
    public boolean CheckExitsAccount(String username, String email) {
        userRepository.findAll();
        return false;
    }
}
