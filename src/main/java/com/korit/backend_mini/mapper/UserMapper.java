package com.korit.backend_mini.mapper;

import com.korit.backend_mini.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface UserMapper {
    Optional<User> findUserByUserId(Integer userId);
    Optional<User> findUserByUserEmail(String email);
    Optional<User> findUserByUsername(String username);
    int addUser(User user);
    int modifyPassword (User user);
    int modifyUsername (User user);
    List<User> getUserList();
    int withdraw (Integer userId);
    void removeUser ();
}
