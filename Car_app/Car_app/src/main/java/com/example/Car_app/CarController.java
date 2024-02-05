package com.example.Car_app;

import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("v1/cars")
public class CarController {
    @Autowired
    private CarRepository carRepository;
    
    @PostMapping("/createCar")
    public Car postCar(@RequestBody Car car) {
        car.setId(null);
        return carRepository.saveAndFlush(car);
    }

    @GetMapping("/getAllCars")
    public List<Car> getAllCars() {
        List<Car> cars = carRepository.findAll();
        return cars;
    }

    @GetMapping("/getCar/{id}")
    public Optional<Car> getCar(@PathVariable Optional<Long> id) {
        @Nullable // funziona con "findById" e restituisce "null" se l'entità con l'id specificato non viene trovata nel database
        Optional<Car> carId = carRepository.findById(id.get());
        return carId;
    }

    @PatchMapping("/updateTypeCar/{id}")
    public Optional<Car> updatingType(@PathVariable Long id, @RequestBody String newType) {
        Optional<Car> oldCar = carRepository.findById(id);
        if (oldCar.isPresent()) {
            oldCar.get().setType(newType);
            carRepository.saveAndFlush(oldCar.get());
            return oldCar;
        } else {
           return Optional.empty();
        }
    }

    @DeleteMapping("/deleteCar/{id}")
    public ResponseEntity deleteOneCar(@PathVariable Long id) {
        if(carRepository.existsById(id)) {
            carRepository.deleteById(id);
            return new ResponseEntity(HttpStatus.ACCEPTED);
        } else {
          return new ResponseEntity(HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping("/deleteAllCars")
    public void deleteAll() {
        carRepository.deleteAll();
    }
}

