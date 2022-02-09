package com.nguyenduong.chatalone.responstory;

import com.nguyenduong.chatalone.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByUsername(String username);

    @Query("select u from User u where u.username = ?1 ")
    ArrayList<User> GetUsername(String username);

    @Query("select u from User u where u.userInfo.DateOfBirth = ?1")
    ArrayList<User> GetBirthday(int birthday);

    @Query("select u from User u where u.userInfo.DateOfBirth > ?1 and u.userInfo.DateOfBirth < ?2")
    ArrayList<User> GetBirthday(int birthdayMin, int birthdayMax);

    @Query("select u from User u where u.userInfo.sex = ?1")
    ArrayList<User> GetSex(int sex);

    @Query("select u from User u where u.userInfo.DateOfBirth = ?1 and u.userInfo.sex = ?2")
    ArrayList<User> GetUserBirthdayAndSex(int birthday, int sex);

    @Query("select u from User u where u.userInfo.DateOfBirth > ?1 and u.userInfo.DateOfBirth < ?2 and u.userInfo.sex = ?3 ")
    ArrayList<User> GetUserBirthdayAndSex(int birthdayMin, int birthdayMax, int sex);

    @Query("update User u set u.userInfo.status = ?2 where u.id = ?1 ")
    boolean UpdateStatus(int id, String staus);

}