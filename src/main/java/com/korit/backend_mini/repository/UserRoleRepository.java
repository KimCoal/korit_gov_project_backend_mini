package com.korit.backend_mini.repository;

import com.korit.backend_mini.entity.UserRole;
import com.korit.backend_mini.mapper.UserRoleMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserRoleRepository {
    private final UserRoleMapper userRoleMapper;

    public int addUserRole (UserRole userRole) {
       return userRoleMapper.addUserRole(userRole);
    }

    public int modifyUserRole (UserRole userRole) {
        return userRoleMapper.modifyUserRole(userRole);
    }

}
