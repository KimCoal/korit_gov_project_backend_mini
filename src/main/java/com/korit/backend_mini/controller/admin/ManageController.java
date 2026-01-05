package com.korit.backend_mini.controller.admin;

import com.korit.backend_mini.security.model.Principal;
import com.korit.backend_mini.service.ManageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/manage")
@RequiredArgsConstructor
public class ManageController {
    private final ManageService manageService;

    @GetMapping("/user/list")
    public ResponseEntity<?> getUserList (@AuthenticationPrincipal Principal principal) {
        return ResponseEntity.ok(manageService.getUserList(principal));
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<?> getUserByUsername (@PathVariable String username,
                                                @AuthenticationPrincipal Principal principal) {
        return ResponseEntity.ok(manageService.getUserBuUsername(username, principal));
    }
}
