package com.boraandege.carrental.model;

import com.boraandege.carrental.enums.CarStatus;
import com.boraandege.carrental.enums.CarType;
import com.boraandege.carrental.enums.TransmissionType;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "cars")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String barcodeNumber;
    private String licensePlateNumber;
    private int passengerCapacity;
    private String brand;
    private String model;
    private int mileage;

    @Enumerated(EnumType.STRING)
    private TransmissionType transmissionType;

    private BigDecimal dailyPrice;

    @Enumerated(EnumType.STRING)
    private CarStatus status;

    @Enumerated(EnumType.STRING)
    private CarType carType;

    @OneToMany(mappedBy = "car", fetch = FetchType.LAZY)
    private List<Reservation> reservations;


    public Long getId() {
        return id;
    }

    public String getBarcodeNumber() {
        return barcodeNumber;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public BigDecimal getDailyPrice() {
        return dailyPrice;
    }

    public TransmissionType getTransmissionType() {
        return transmissionType;
    }

    public CarStatus getStatus() {
        return status;
    }

    public CarType getCarType() {
        return carType;
    }

    public String getLicensePlateNumber() {
        return licensePlateNumber;
    }

    public int getPassengerCapacity() {
        return passengerCapacity;
    }

    public int getMileage() {
        return mileage;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setBarcodeNumber(String barcodeNumber) {
        this.barcodeNumber = barcodeNumber;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setDailyPrice(BigDecimal dailyPrice) {
        this.dailyPrice = dailyPrice;
    }

    public void setTransmissionType(TransmissionType transmissionType) {
        this.transmissionType = transmissionType;
    }

    public void setStatus(CarStatus status) {
        this.status = status;
    }

    public void setCarType(CarType carType) {
        this.carType = carType;
    }

    public void setLicensePlateNumber(String licensePlateNumber) {
        this.licensePlateNumber = licensePlateNumber;
    }

    public void setPassengerCapacity(int passengerCapacity) {
        this.passengerCapacity = passengerCapacity;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations != null ? reservations : List.of();
    }
}
