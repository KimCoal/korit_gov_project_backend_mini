package com.korit.backend_mini.dto.account;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ModifyProfileImgReqDto {
    private Integer userId;
    private String profileImg;
}
