package com.oktenria.mapper;

import com.oktenria.dto.CarDto;
import com.oktenria.entities.Car;
import org.springframework.stereotype.Component;

@Component
public class CarMapper {

    public CarDto toDto(Car car) {
        return CarDto.builder()
                .id(car.getId())
                .brand(car.getBrand())
                .model(car.getModel())
                .year(car.getYear())
                .engine(car.getEngine())
                .fuel(car.getFuel())
                .color(car.getColor())
                .build();
    }

    public Car fromDto(CarDto carDto) {
        Car car = new Car();
        car.setId((carDto.getId()));
        car.setBrand(carDto.getBrand());
        car.setModel(carDto.getModel());
        car.setYear(carDto.getYear());
        car.setEngine(carDto.getEngine());
        car.setFuel(carDto.getFuel());
        car.setColor(carDto.getColor());

        return car;
    }
}
