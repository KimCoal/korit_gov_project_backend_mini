package com.korit.backend_mini.controller.admin;

import com.korit.backend_mini.dto.account.ModifyPasswordReqDto;
import com.korit.backend_mini.dto.account.ModifyUsernameReqDto;
import com.korit.backend_mini.security.model.Principal;
import com.korit.backend_mini.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/account")
@RequiredArgsConstructor
public class AdminAccountController {
    private final AccountService accountService;

    @PostMapping("/modify/password")
    public ResponseEntity<?> modifyPassword (@RequestBody ModifyPasswordReqDto modifyPasswordReqDto,
                                             @AuthenticationPrincipal Principal principal) {
        return ResponseEntity.ok(accountService.modifyPassword(modifyPasswordReqDto, principal));
    }

    @PostMapping("/modify/username")
    public ResponseEntity<?> modifyUsername (@RequestBody ModifyUsernameReqDto modifyUsernameReqDto,
                                             @AuthenticationPrincipal Principal principal) {
        return ResponseEntity.ok(accountService.modifyUsername(modifyUsernameReqDto, principal));
    }

}
