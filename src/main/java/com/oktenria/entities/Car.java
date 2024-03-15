package com.oktenria.entities;

import com.oktenria.entities.enums.CarBrand;
import com.oktenria.entities.enums.CarFuel;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private CarBrand brand;

    private String model;

    private String year;

    private Float engine;

    private CarFuel fuel;

    private String color;

    private String userId;

    private String wanted;
}
