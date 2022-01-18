package com.nguyenduong.chatalone.responstory;

import com.nguyenduong.chatalone.model.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserInfoRepository extends JpaRepository<UserInfo, Integer> {

}