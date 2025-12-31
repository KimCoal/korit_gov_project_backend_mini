package com.korit.backend_mini.security.handler;

import com.korit.backend_mini.entity.OAuth2User;
import com.korit.backend_mini.entity.User;
import com.korit.backend_mini.repository.OAuth2UserRepository;
import com.korit.backend_mini.repository.UserRepository;
import com.korit.backend_mini.security.jwt.JwtUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {
    private final OAuth2UserRepository oAuth2UserRepository;
    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        DefaultOAuth2User defaultOAuth2User = (DefaultOAuth2User) authentication.getPrincipal();

        // OAuth2UserService에서 설정한 attribute 가져오기
        String provider = defaultOAuth2User.getAttribute("provider");
        String providerUserId = defaultOAuth2User.getAttribute("providerUserId");
        String email = defaultOAuth2User.getAttribute("email");

        // null 체크 추가
        if (provider == null || providerUserId == null || email == null) {
            response.sendRedirect("http://localhost:5173/auth/signin?error=oauth2_data_missing");
            return;
        }

        // 기존 OAuth2 사용자 확인
        Optional<OAuth2User> foundOAuth2User = oAuth2UserRepository.findOAuth2UserByProviderAndProviderUserId(provider, providerUserId);

        if (foundOAuth2User.isEmpty()) {
            // 신규 OAuth2 사용자 - 회원가입/연동 페이지로
            response.sendRedirect("http://localhost:5173/auth/oauth2?provider=" + provider + "&providerUserId=" + providerUserId + "&email=" + email);
            return;
        }

        // 기존 OAuth2 사용자가 있으면 User 조회
        Optional<User> foundUser = userRepository.findUserByUserId(foundOAuth2User.get().getUserId());

        // 수정: isPresent()가 false일 때 에러 처리
        if (foundUser.isEmpty()) {
            throw new RuntimeException("연동된 사용자 정보를 찾을 수 없습니다");
        }

        // 수정: 사용자가 있으면 토큰 발급
        User user = foundUser.get();
        String accessToken = jwtUtils.generateAccessToken(Integer.toString(user.getUserId()));

        response.sendRedirect("http://localhost:5173/auth/oauth2/signin?accessToken=" + accessToken);
    }
}