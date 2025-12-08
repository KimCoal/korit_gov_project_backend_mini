package com.korit.backend_mini.repository;

import com.korit.backend_mini.entity.User;
import com.korit.backend_mini.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepository {
    private final UserMapper userMapper;

    public Optional<User> findUserByUserId (Integer userId) {
        return userMapper.findUserByUserId(userId);
    }

    public Optional<User> findUserByUserEmail (String email) {
        return userMapper.findUserByUserEmail(email);
    }

    public Optional<User> findUserByUsername (String username) {
        return userMapper.findUserByUsername(username);
    }

    public Optional<User> addUser (User user) {
        try {
           int result = userMapper.addUser(user);
           if (result != 1) {
               throw new RuntimeException("회원정보 추가에 실패했습니다");
           }
        } catch (RuntimeException e) {
            return Optional.empty();
        }
        return Optional.of(user);
    }

    public int modifyPassword (User user) {
        return userMapper.modifyPassword(user);
    }

    public int modifyUsername (User user) {
        return userMapper.modifyUsername(user);
    }
}
