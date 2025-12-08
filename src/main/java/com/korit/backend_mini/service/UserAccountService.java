package com.korit.backend_mini.service;

import com.korit.backend_mini.dto.ApiRespDto;
import com.korit.backend_mini.dto.ModifyPasswordReqDto;
import com.korit.backend_mini.dto.ModifyUsernameReqDto;
import com.korit.backend_mini.entity.User;
import com.korit.backend_mini.repository.UserRepository;
import com.korit.backend_mini.security.model.Principal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserAccountService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public ApiRespDto<?> modifyPassword(ModifyPasswordReqDto modifyPasswordReqDto, Principal principal) {
        if (!modifyPasswordReqDto.getUserId().equals(principal.getUserId())) {
            return new ApiRespDto<>("failed", "잘못된 접근입니다", null);
        }
        Optional<User> foundUser = userRepository.findUserByUserId(modifyPasswordReqDto.getUserId());
        if (foundUser.isEmpty()) {
            return new ApiRespDto<>("failed", "존재하지 않은 회원정보입니다", null);
        }

        User user = foundUser.get();
        if (bCryptPasswordEncoder.matches(modifyPasswordReqDto.getPassword(), user.getPassword())) {
            return new ApiRespDto<>("failed", "현재 비밀번호가 일치하지 않습니다", null);
        }

        if (bCryptPasswordEncoder.matches(modifyPasswordReqDto.getNewPassword(), user.getPassword())) {
            return new ApiRespDto<>("failed", "새 비밀번호는 기존 비밀번호와 일치합니다", null);
        }

        user.setPassword(bCryptPasswordEncoder.encode(modifyPasswordReqDto.getNewPassword()));
        int result = userRepository.modifyPassword(user);
        if (result != 1) {
            return new ApiRespDto<>("failed", "문제가 발생", null);
        }
        return new ApiRespDto<>("success", "비밀번호 변경 성공", null);
    }

    public ApiRespDto<?> modifyUsername (ModifyUsernameReqDto modifyUsernameReqDto, Principal principal) {
        if (!modifyUsernameReqDto.getUserId().equals(principal.getUserId())) {
            return new ApiRespDto<>("failed", "잘못된 접근입니다", null);
        }
        Optional<User> foundUser = userRepository.findUserByUserId(modifyUsernameReqDto.getUserId());
        if (foundUser.isEmpty()) {
            return new ApiRespDto<>("failed", "존재하지 않은 회원입니다", null);
        }
        Optional<User> optionalUser = userRepository.findUserByUsername(modifyUsernameReqDto.getUsername());
        if (optionalUser.isPresent()) {
            return new ApiRespDto<>("failed", "이미 존재하는 닉네임입니다", null);
        }
        User user = foundUser.get();
        user.setUsername(modifyUsernameReqDto.getNewUsername());
        int result = userRepository.modifyUsername(user);
        if (result != 1) {
            return new ApiRespDto<>("failed", "닉네임 변경에 실패했습니다", null);
        }
        return new ApiRespDto<>("success", "변경 성공", null);
    }
}
