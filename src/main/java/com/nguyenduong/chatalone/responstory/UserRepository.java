package com.nguyenduong.chatalone.responstory;

import com.nguyenduong.chatalone.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByUsername(String username);

    @Query("select u from User u where u.username = ?1 ")
    ArrayList<User> GetUsername(String username);

}