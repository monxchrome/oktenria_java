package com.oktenria.dto;

import lombok.Data;

@Data
public class ResponseDto {

    private String accessToken;

    private String refreshToken;
}
