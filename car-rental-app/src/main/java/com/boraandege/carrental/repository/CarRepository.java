package com.boraandege.carrental.repository;

import com.boraandege.carrental.model.Car;
import com.boraandege.carrental.enums.CarStatus;
import com.boraandege.carrental.enums.CarType;
import com.boraandege.carrental.enums.TransmissionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

    List<Car> findByStatusAndCarTypeAndTransmissionType(CarStatus status, CarType carType, TransmissionType transmissionType);

    List<Car> findByStatusIn(List<CarStatus> statuses);

    Optional<Car> findByBarcodeNumber(String barcodeNumber);
}
