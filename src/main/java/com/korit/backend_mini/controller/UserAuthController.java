package com.korit.backend_mini.controller;

import com.korit.backend_mini.dto.SigninReqDto;
import com.korit.backend_mini.dto.SignupReqDto;
import com.korit.backend_mini.service.UserAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/auth")
@RequiredArgsConstructor
public class UserAuthController {
    private final UserAuthService userAuthService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup (@RequestBody SignupReqDto signupReqDto) {
        return ResponseEntity.ok(userAuthService.signup(signupReqDto));
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signin (@RequestBody SigninReqDto signinReqDto) {
        return ResponseEntity.ok(userAuthService.signin(signinReqDto));
    }
}
