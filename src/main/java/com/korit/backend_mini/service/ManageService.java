package com.korit.backend_mini.service;

import com.korit.backend_mini.dto.response.ApiRespDto;
import com.korit.backend_mini.repository.UserRepository;
import com.korit.backend_mini.security.model.Principal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}
