package com.oktenria.dto;

import lombok.Data;

@Data
public class LoginRequestDto {

    private String emailOrPhone;

    private String password;
}
