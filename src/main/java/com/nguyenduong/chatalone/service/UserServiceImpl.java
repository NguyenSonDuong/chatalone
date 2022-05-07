package com.nguyenduong.chatalone.service;

import com.nguyenduong.chatalone.model.*;
import com.nguyenduong.chatalone.responstory.RoleRepository;
import com.nguyenduong.chatalone.responstory.UserInfoRepository;
import com.nguyenduong.chatalone.responstory.UserRepository;
import com.nguyenduong.chatalone.responstory.UserRoleRepository;
import com.nimbusds.jose.shaded.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
        System.out.println(userRepository);
        ArrayList<User> check = userRepository.GetUsername(user.getAsString("username"));
        if(check == null)
            return null;
        System.out.println(check.size());
        if(check.size() > 0)
            return null;
        Role rol = new Role(role, (role.equals("USER") ? "Người dùng" : (role.equals("ADMIN") ? "Quản trị viên" : "Khách")));
        rol.getPermissions().addAll(GetPremission(role));
        int id = roleMainRepository.saveAndFlush(rol).getId();
        User user1 = new User(user.get("username").toString(),user.get("password").toString(),role.equals("CUSTOMER") ? "" : user.get("email").toString() );
        if(user.containsKey("birthday") && user.containsKey("sex"))
            user1.setUserInfo(new UserInfo(user.getAsNumber("birthday").intValue(),user.getAsNumber("sex").intValue()));
        else
            user1.setUserInfo(new UserInfo(2022,0));
        User userUpdate = userRepository.saveAndFlush(user1);
        roleRepository.saveAndFlush(new UserRole(user1.getId(),id));
        System.out.println(userRepository);
        if(role.equals("CUSTOMER"))
            userUpdate.setPassword(user.getAsString("password"));
        return userUpdate;
    }
    public Set<Permission> GetPremission(String role){
        Set<Permission> permissions = new HashSet<Permission>();
        if(role.equals("USER")){
            permissions.add(new Permission("CREATE_USER","Tạo tài khoản"));
            permissions.add(new Permission("READ_USER","Đọc thông tin tài khoản"));
            permissions.add(new Permission("UPDATE_USER","Sửa thông tài khoản"));
            permissions.add(new Permission("MESSAGE","Gửi tin nhắn"));
        }
        if(role.equals("ADMIN")){
            permissions.add(new Permission("CREATE_USER","Tạo tài khoản"));
            permissions.add(new Permission("READ_USER","Đọc thông tin tài khoản"));
            permissions.add(new Permission("UPDATE_USER","Sửa thông tài khoản"));
            permissions.add(new Permission("DELETE_USER","Xóa tài khoản"));
            permissions.add(new Permission("MESSAGE","Gửi tin nhắn"));
            permissions.add(new Permission("CHANGE_ROLE_USER","Thay đổi quyền người dùng"));
        }
        if(role.equals("CUSTOMER")){
            permissions.add(new Permission("READ_USER","Đọc thông tin tài khoản"));
            permissions.add(new Permission("MESSAGE","Gửi tin nhắn"));
        }
        return  permissions;
    }
    @Override
    public UserPrincipal findByUsername( User user) {
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
