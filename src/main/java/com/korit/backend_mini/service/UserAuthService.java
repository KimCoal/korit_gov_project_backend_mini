package com.korit.backend_mini.service;

import com.korit.backend_mini.dto.response.ApiRespDto;
import com.korit.backend_mini.dto.auth.SigninReqDto;
import com.korit.backend_mini.dto.auth.SignupReqDto;
import com.korit.backend_mini.entity.User;
import com.korit.backend_mini.entity.UserRole;
import com.korit.backend_mini.repository.UserRepository;
import com.korit.backend_mini.repository.UserRoleRepository;
import com.korit.backend_mini.security.jwt.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserAuthService {
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final JwtUtils jwtUtils;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional(rollbackFor = Exception.class)
    public ApiRespDto<?> signup (SignupReqDto signupReqDto) {
        Optional<User> foundUser = userRepository.findUserByUserEmail(signupReqDto.getEmail());
        if (foundUser.isPresent()) {
            return new ApiRespDto<>("failed", "이미 존재하는 이메일", null);
        }

        Optional<User> foundUserByUsername = userRepository.findUserByUsername(signupReqDto.getUsername());
        if (foundUserByUsername.isPresent()) {
            return new ApiRespDto<>("failed", "이미 존재하는 닉네임", signupReqDto.getUsername());
        }
        Optional<User> optionalUser = userRepository.addUser(signupReqDto.toEntity(bCryptPasswordEncoder));
        if (optionalUser.isEmpty()) {
            throw new RuntimeException("회원 추가에 실패했습니다");
        }

        UserRole userRole = UserRole.builder()
                .userId(optionalUser.get().getUserId())
                .roleId(3)
                .build();

        int result = userRoleRepository.addUserRole(userRole);
        if (result != 1) {
            throw new RuntimeException("회원 권한 추가에 실패했습니다");
        }
        return new ApiRespDto<>("success", "회원가입 성공", optionalUser.get());
    }

    public ApiRespDto<?> signin(SigninReqDto signinReqDto) {

        Optional<User> foundUser = userRepository.findUserByUserEmail(signinReqDto.getEmail());
        if (foundUser.isEmpty()) {
            return new ApiRespDto<>("failed", "해당 유저 없음", signinReqDto.getEmail());
        }
        User user = foundUser.get();
        if (!bCryptPasswordEncoder.matches(signinReqDto.getPassword(), user.getPassword())) {
            return new ApiRespDto<>("failed", "정보 확인", null);
        }
        if (!foundUser.get().isActive()) {
            return new ApiRespDto<>("failed", "탈퇴 처리된 계정입니다", null);
        }

        String accessToken = jwtUtils.generateAccessToken(user.getUserId().toString());
        return new ApiRespDto<>("success", "로그인 성공", accessToken);
    }
}
