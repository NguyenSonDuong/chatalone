package com.nguyenduong.chatalone.service;

import com.nguyenduong.chatalone.model.User;
import com.nguyenduong.chatalone.model.UserPrincipal;
import com.nimbusds.jose.shaded.json.JSONObject;

public interface UserService {
    User createUser(String role, JSONObject user);
    UserPrincipal findByUsername(User user);
    boolean CheckExitsAccount(String username, String email);
}
