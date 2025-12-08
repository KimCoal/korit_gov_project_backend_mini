package com.korit.backend_mini.controller;

import com.korit.backend_mini.dto.ModifyPasswordReqDto;
import com.korit.backend_mini.dto.ModifyUsernameReqDto;
import com.korit.backend_mini.security.model.Principal;
import com.korit.backend_mini.service.UserAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/account")
@RequiredArgsConstructor
public class UserAccountController {
    private final UserAccountService userAccountService;

    @PostMapping("/modify/password")
    public ResponseEntity<?> modifyPassword (@RequestBody ModifyPasswordReqDto modifyPasswordReqDto,
                                             @AuthenticationPrincipal Principal principal) {
        return ResponseEntity.ok(userAccountService.modifyPassword(modifyPasswordReqDto, principal));
    }

    @PostMapping("/modify/username")
    public ResponseEntity<?> modifyUsername (@RequestBody ModifyUsernameReqDto modifyUsernameReqDto,
                                             @AuthenticationPrincipal Principal principal) {
        return ResponseEntity.ok(userAccountService.modifyUsername(modifyUsernameReqDto, principal));
    }

}
