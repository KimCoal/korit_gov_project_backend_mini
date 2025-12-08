package com.korit.backend_mini.controller;

import com.korit.backend_mini.security.model.Principal;
import com.korit.backend_mini.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
@RequestMapping("/mail")
@RequiredArgsConstructor
public class MailController {
    private final MailService mailService;

    @PostMapping("/send")
    public ResponseEntity<?> sendMail (@AuthenticationPrincipal Principal principal) {
        return ResponseEntity.ok(mailService.sendMail(principal));
    }

    @GetMapping("/verify")
    public String verify(Model model, @RequestParam String token) {
        Map<String, Object> resultMap = mailService.verify(token);
        model.addAllAttributes(resultMap);
        return "result_page";
    }
}
