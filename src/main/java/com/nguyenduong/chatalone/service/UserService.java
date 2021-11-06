package com.nguyenduong.chatalone.service;

import com.nguyenduong.chatalone.model.User;
import com.nguyenduong.chatalone.model.UserPrincipal;

public interface UserService {
    User createUser(User user);
    UserPrincipal findByUsername(User user);
}
