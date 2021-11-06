package com.nguyenduong.chatalone.service;

import com.nguyenduong.chatalone.model.User;
import com.nguyenduong.chatalone.model.UserPrincipal;
import com.nguyenduong.chatalone.responstory.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public User createUser(User user) {
        System.out.println(userRepository);
        return userRepository.saveAndFlush(user);
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
            System.out.println(authorities);
            userPrincipal.setUsername(user.getUsername());
            userPrincipal.setPassword(user.getPassword());
            userPrincipal.setAuthorities(authorities);
        }
        return userPrincipal;
    }
}
