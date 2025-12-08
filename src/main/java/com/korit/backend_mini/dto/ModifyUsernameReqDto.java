package com.korit.backend_mini.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ModifyUsernameReqDto {
    private Integer userId;
    private String username;
    private String newUsername;
}
