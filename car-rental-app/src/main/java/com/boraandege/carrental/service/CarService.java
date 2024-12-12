package com.boraandege.carrental.service;

import com.boraandege.carrental.dto.CarDTO;
import com.boraandege.carrental.enums.CarStatus;
import com.boraandege.carrental.enums.CarType;
import com.boraandege.carrental.enums.TransmissionType;
import java.util.List;

public interface CarService {

    CarDTO addCar(CarDTO carDTO);

    CarDTO getCarById(Long id);

    List<CarDTO> getAllCars();

    CarDTO updateCar(Long id, CarDTO carDTO);

    void deleteCar(Long id);

    List<CarDTO> searchAvailableCars(CarType carType, TransmissionType transmissionType);

    List<CarDTO> getRentedCars();

    boolean deleteCarByBarcode(String barcodeNumber);
}
