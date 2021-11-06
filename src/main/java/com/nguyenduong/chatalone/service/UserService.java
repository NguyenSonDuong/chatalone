package com.nguyenduong.chatalone.service;

import com.nguyenduong.chatalone.model.User;
import com.nguyenduong.chatalone.model.UserPrincipal;

public interface UserService {
    User createUser(String role,User user);
    UserPrincipal findByUsername(User user);
    boolean CheckExitsAccount(String username, String email);
}
