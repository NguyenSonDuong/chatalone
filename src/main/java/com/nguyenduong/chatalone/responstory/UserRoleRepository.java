package com.nguyenduong.chatalone.responstory;

import com.nguyenduong.chatalone.model.UserRole;
import com.nguyenduong.chatalone.model.UserRoleId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository extends JpaRepository<UserRole, UserRoleId> {
}