package com.oktenria.dto;

import com.oktenria.entities.User;
import com.oktenria.entities.enums.CarBrand;
import com.oktenria.entities.enums.CarFuel;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class CarDto {

    private Long id;

    private CarBrand brand;

    private String model;

    private String year;

    private Float engine;

    private CarFuel fuel;

    private String color;

    private Set<User> users;

    private String wanted;
}
