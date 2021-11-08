package com.nguyenduong.chatalone.service;

import com.nguyenduong.chatalone.model.UserInfo;
import com.nguyenduong.chatalone.responstory.UserInfoRepository;
import com.nguyenduong.chatalone.responstory.UserRoleRepository;
import com.nguyenduong.chatalone.model.User;
import com.nguyenduong.chatalone.model.UserPrincipal;
import com.nguyenduong.chatalone.model.UserRole;
import com.nguyenduong.chatalone.responstory.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
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

    @Override
    public User createUser(String role,User user) {
        User userUpdate = userRepository.saveAndFlush(user);
        roleRepository.saveAndFlush(new UserRole(user.getId(),3));
        userInfoRepository.saveAndFlush(new UserInfo(user.getId(),"",0,new Date(),""));
        System.out.println(userRepository);
        return userRepository.findById(user.getId()).get();
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
