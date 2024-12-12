package com.boraandege.carrental.dto;

import com.boraandege.carrental.enums.CarStatus;
import com.boraandege.carrental.enums.CarType;
import com.boraandege.carrental.enums.TransmissionType;
import java.math.BigDecimal;

public class CarDTO {

    private Long id;
    private String barcodeNumber;
    private String licensePlateNumber;
    private int passengerCapacity;
    private String brand;
    private String model;
    private int mileage;
    private TransmissionType transmissionType;
    private BigDecimal dailyPrice;
    private CarStatus status;
    private CarType carType;

    public BigDecimal getDailyPrice() {
        return dailyPrice;
    }

    public Long getId() {
        return id;
    }

    public CarStatus getStatus() {
        return status;
    }

    public CarType getCarType() {
        return carType;
    }

    public int getMileage() {
        return mileage;
    }

    public int getPassengerCapacity() {
        return passengerCapacity;
    }

    public String getBarcodeNumber() {
        return barcodeNumber;
    }

    public String getBrand() {
        return brand;
    }

    public String getLicensePlateNumber() {
        return licensePlateNumber;
    }

    public String getModel() {
        return model;
    }

    public TransmissionType getTransmissionType() {
        return transmissionType;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setStatus(CarStatus status) {
        this.status = status;
    }

    public void setTransmissionType(TransmissionType transmissionType) {
        this.transmissionType = transmissionType;
    }

    public void setPassengerCapacity(int passengerCapacity) {
        this.passengerCapacity = passengerCapacity;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    public void setLicensePlateNumber(String licensePlateNumber) {
        this.licensePlateNumber = licensePlateNumber;
    }

    public void setCarType(CarType carType) {
        this.carType = carType;
    }

    public void setDailyPrice(BigDecimal dailyPrice) {
        this.dailyPrice = dailyPrice;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setBarcodeNumber(String barcodeNumber) {
        this.barcodeNumber = barcodeNumber;
    }

}
