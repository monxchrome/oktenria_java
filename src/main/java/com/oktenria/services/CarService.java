package com.oktenria.services;

import com.oktenria.entities.Car;
import com.oktenria.repositories.CarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CarService {

    private final CarRepository carRepository;

    public List<Car> getAll() {
        return carRepository.findAll();
    }

    public Optional<Car> getById(Long id) {
        return carRepository.findById(id);
    }

    public void deleteCar(Long id) {
        carRepository.deleteById(id);
    }

    public void updateCar(Long id, Car updatedCar) {
        Optional<Car> optionalCar = carRepository.findById(id);

        if (optionalCar.isPresent()) {
            Car existingCar = optionalCar.get();

            existingCar.setBrand(updatedCar.getBrand());
            existingCar.setModel(updatedCar.getModel());
            existingCar.setYear(updatedCar.getYear());
            existingCar.setEngine(updatedCar.getEngine());
            existingCar.setFuel(updatedCar.getFuel());
            existingCar.setColor(updatedCar.getColor());
            existingCar.setWanted(updatedCar.getWanted());
        }
    }

    public Car postCar(Car newCar) {
        return carRepository.save(newCar);
    }
}
