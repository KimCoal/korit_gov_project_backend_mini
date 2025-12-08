package com.korit.backend_mini.service;

import com.korit.backend_mini.dto.ApiRespDto;
import com.korit.backend_mini.entity.User;
import com.korit.backend_mini.entity.UserRole;
import com.korit.backend_mini.repository.UserRepository;
import com.korit.backend_mini.repository.UserRoleRepository;
import com.korit.backend_mini.security.jwt.JwtUtils;
import com.korit.backend_mini.security.model.Principal;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MailService {
    private final JwtUtils jwtUtils;
    private final JavaMailSender javaMailSender;
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;

    public ApiRespDto<?> sendMail (Principal principal) {
        boolean hasTempRole = principal.getUserRoles().stream().anyMatch(userRole -> userRole.getRoleId() == 3);
        if (!hasTempRole) {
            return new ApiRespDto<>("failed", "이미 인증이 완료되었습니다", null);
        }
        String verifyToken = jwtUtils.generateVerifyToken(principal.getUserId().toString());
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(principal.getEmail());
        message.setSubject("이메일 인증을 완료해주세요");
        message.setText("이메일 인증 링크입니다. 링크를 눌러 인증을 완료해 주세요.\n" + "http://localhost:8080/mail/verify?token=" + verifyToken);
        javaMailSender.send(message);

        return new ApiRespDto<>("success", "인증 이메일 전송", null);
    }
    public Map<String, Object> verify(String token) {
        Claims claims;
        Map<String, Object> resultMap;

        try {
            claims = jwtUtils.getClaims(token);
            if (!"VerifyToken".equals(claims.getSubject())) {
                resultMap = Map.of(
                        "status", "failed",
                        "message", "잘못된 접근입니다"
                );
            }
            String id = claims.getId();
            Integer userId = Integer.parseInt(id);
            Optional<User> foundUser = userRepository.findUserByUserId(userId);
            if (foundUser.isEmpty()) {
                resultMap = Map.of(
                        "status", "failed",
                        "message", "존재하지 않은 회원정보 입니다"
                );
            }
            List<UserRole> userRoles = foundUser.get().getUserRoles();
            Optional<UserRole> userRole = userRoles.stream().filter(u -> u.getRoleId() == 3).findFirst();
            if (userRole.isEmpty()) {
                resultMap = Map.of(
                        "status", "failed",
                        "message", "인증이 필요하지 않습니다"
                );
            }
            UserRole resultRole = userRole.get();
            resultRole.setRoleId(2);
            int result = userRoleRepository.modifyUserRole(userRole.get());
            if (result != 1) {
                resultMap = Map.of(
                        "status", "failed",
                        "message", "문제가 발생했습니다"
                );
            }

            resultMap = Map.of(
                    "status", "success",
                    "message", "인증이 완료되었습니다"
            );
        } catch (ExpiredJwtException e) {
            resultMap = Map.of(
                    "status", "failed",
                    "message", "인증시간이 만료된 요청입니다"
            );
        } catch (Exception e) {
            resultMap = Map.of(
                    "status", "failed",
                    "message", "잘못된 요청입니다"
            );
        }
        return resultMap;
    }
}
