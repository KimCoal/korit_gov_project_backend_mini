package com.korit.backend_mini.controller.user;

import com.korit.backend_mini.dto.account.ModifyPasswordReqDto;
import com.korit.backend_mini.dto.account.ModifyProfileImgReqDto;
import com.korit.backend_mini.dto.account.ModifyUsernameReqDto;
import com.korit.backend_mini.dto.response.ApiRespDto;
import com.korit.backend_mini.security.model.Principal;
import com.korit.backend_mini.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user/account")
@RequiredArgsConstructor
public class UserAccountController {
    private final AccountService accountService;

    @GetMapping("/principal")
    public ResponseEntity<?> getPrincipal(@AuthenticationPrincipal Principal principal) {
        return ResponseEntity.ok(new ApiRespDto<>("success", "회원 조회 완료", principal));
    }

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

    @PostMapping("/modify/profileImg")
    public ResponseEntity<?> modifyProfileImg (@RequestBody ModifyProfileImgReqDto modifyProfileImgDto,
                                               @AuthenticationPrincipal Principal principal) {
        return ResponseEntity.ok(accountService.modifyProfileImg(modifyProfileImgDto, principal));
    }

    @PostMapping("/withdraw")
    public ResponseEntity<?> withdraw(@AuthenticationPrincipal Principal principal) {
        return ResponseEntity.ok(accountService.withdraw(principal));
    }

}
