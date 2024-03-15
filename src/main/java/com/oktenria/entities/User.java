package com.oktenria.entities;

import com.oktenria.entities.enums.UserRole;
import com.oktenria.entities.enums.UserSub;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Data
@Document("users")
public class User {

    @MongoId
    private ObjectId id;

    @NotBlank(message = "Email can not be empty")
    private String email;

    @NotBlank(message = "Phone can not be empty")
    private String phone;

    @NotBlank(message = "Password can not be empty")
    private String password;

    @NotBlank(message = "Name can not be empty")
    private String name;

    private byte[] image;

    private UserRole role;

    private UserSub subscribe;

    private Long carId;
}
