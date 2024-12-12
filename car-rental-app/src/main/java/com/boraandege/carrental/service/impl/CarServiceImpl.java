package com.boraandege.carrental.service.impl;

import com.boraandege.carrental.dto.CarDTO;
import com.boraandege.carrental.model.Car;
import com.boraandege.carrental.enums.CarStatus;
import com.boraandege.carrental.enums.CarType;
import com.boraandege.carrental.enums.TransmissionType;
import com.boraandege.carrental.exception.ResourceNotFoundException;
import com.boraandege.carrental.mapper.CarMapper;
import com.boraandege.carrental.repository.CarRepository;
import com.boraandege.carrental.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;
    private final CarMapper carMapper;

    @Autowired
    public CarServiceImpl(CarRepository carRepository, CarMapper carMapper) {
        this.carRepository = carRepository;
        this.carMapper = carMapper;
    }

    @Override
    public CarDTO addCar(CarDTO carDTO) {
        Car car = carMapper.toEntity(carDTO);
        car.setStatus(CarStatus.AVAILABLE);

        if (car.getReservations() == null) {
            car.setReservations(Collections.emptyList());
        }

        Car savedCar = carRepository.save(car);
        return carMapper.toDTO(savedCar);
    }

    @Override
    public CarDTO getCarById(Long id) {
        Car car = carRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Car not found with id: " + id));
        return carMapper.toDTO(car);
    }

    @Override
    public List<CarDTO> getAllCars() {
        List<Car> cars = carRepository.findAll();
        return cars.stream()
                .map(carMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CarDTO updateCar(Long id, CarDTO carDTO) {
        Car existingCar = carRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Car not found with id: " + id));

        existingCar.setBarcodeNumber(carDTO.getBarcodeNumber());
        existingCar.setLicensePlateNumber(carDTO.getLicensePlateNumber());
        existingCar.setPassengerCapacity(carDTO.getPassengerCapacity());
        existingCar.setBrand(carDTO.getBrand());
        existingCar.setModel(carDTO.getModel());
        existingCar.setMileage(carDTO.getMileage());
        existingCar.setTransmissionType(carDTO.getTransmissionType());
        existingCar.setDailyPrice(carDTO.getDailyPrice());
        existingCar.setCarType(carDTO.getCarType());

        Car updatedCar = carRepository.save(existingCar);
        return carMapper.toDTO(updatedCar);
    }

    @Override
    public void deleteCar(Long id) {
        Car car = carRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Car not found with id: " + id));
        carRepository.delete(car);
    }

    @Override
    public List<CarDTO> searchAvailableCars(CarType carType, TransmissionType transmissionType) {
        if (carType == null || transmissionType == null) {
            throw new IllegalArgumentException("CarType and TransmissionType must not be null");
        }

        List<Car> cars = carRepository.findByStatusAndCarTypeAndTransmissionType(
                CarStatus.AVAILABLE, carType, transmissionType);

        if (cars.isEmpty()) {
            throw new ResourceNotFoundException("No available cars found matching the criteria");
        }

        return cars.stream()
                .map(carMapper::toDTO)
                .collect(Collectors.toList());
    }


    @Override
    public List<CarDTO> getRentedCars() {
        List<Car> cars = carRepository.findByStatusIn(List.of(CarStatus.RESERVED, CarStatus.LOANED));
        return cars.stream()
                .map(carMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public boolean deleteCarByBarcode(String barcodeNumber) {
        Optional<Car> optionalCar = carRepository.findByBarcodeNumber(barcodeNumber);
        if (optionalCar.isEmpty()) {
            throw new ResourceNotFoundException("Car not found with barcode: " + barcodeNumber);
        }
        Car car = optionalCar.get();

        if (car.getStatus() != CarStatus.AVAILABLE || !car.getReservations().isEmpty()) {
            return false;
        }

        carRepository.delete(car);
        return true;
    }
}
