package com.korit.backend_mini.service;

import com.korit.backend_mini.dto.response.ApiRespDto;
import com.korit.backend_mini.entity.User;
import com.korit.backend_mini.repository.UserRepository;
import com.korit.backend_mini.security.model.Principal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ManageService {
    private final UserRepository userRepository;

    public ApiRespDto<?> getUserList(Principal principal) {
        if (principal.getUserRoles().stream().noneMatch(userRole -> userRole.getRoleId() == 1)) {
            return new ApiRespDto<>("failed", "접근 권한이 없습니다", null);
        }
        return new ApiRespDto<>("success", "전체 조회", userRepository.getUserList());
    }

    public ApiRespDto<?> getUserBuUsername(String username, Principal principal) {
        if (principal.getUserRoles().stream().noneMatch(userRole -> userRole.getRoleId() == 1)) {
            return new ApiRespDto<>("failed", "접근 권한이 없습니다", null);
        }

        Optional<User> foundUser = userRepository.findUserByUsername(username);
        if (foundUser.isEmpty()) {
            return new ApiRespDto<>("failed", "해당 회원이 존재하지 않습니다", null);
        }

        return new ApiRespDto<>("success", "회원 정보 조회 성공", foundUser.get());
    }
}
