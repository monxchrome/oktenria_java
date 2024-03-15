package com.oktenria.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RequestDto {

    @NotBlank
    private String email;

    @NotBlank
    private String phone;

    @NotBlank
    private String password;

    @NotBlank
    private String name;
}
