package com.korit.backend_mini.dto;

import com.korit.backend_mini.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Data
@AllArgsConstructor
public class ModifyPasswordReqDto {
    private Integer userId;
    private String password;
    private String newPassword;

}
