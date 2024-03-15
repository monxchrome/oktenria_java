package com.oktenria.dto;

import com.oktenria.entities.Car;
import com.oktenria.entities.enums.UserRole;
import com.oktenria.entities.enums.UserSub;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class UserDto {

    private Long id;

    private String email;

    private String phone;

    private String password;

    private String name;

    private byte[] image;

    private UserRole role;

    private UserSub subscribe;

    private Set<Car> cars;

}
