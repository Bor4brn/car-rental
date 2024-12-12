package com.boraandege.carrental.model;

import com.boraandege.carrental.enums.ReservationStatus;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "reservations")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 8, unique = true)
    private String reservationNumber;

    private LocalDateTime creationDate;
    private LocalDateTime pickUpDateTime;
    private LocalDateTime dropOffDateTime;
    private LocalDateTime returnDate;

    @Enumerated(EnumType.STRING)
    private ReservationStatus status;

    private int dayCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_id")
    private Car car;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pick_up_location_id")
    private Location pickUpLocation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "drop_off_location_id")
    private Location dropOffLocation;

    @ManyToMany
    @JoinTable(
            name = "reservation_equipment",
            joinColumns = @JoinColumn(name = "reservation_id"),
            inverseJoinColumns = @JoinColumn(name = "equipment_id")
    )
    private List<Equipment> additionalEquipments;

    @ManyToMany
    @JoinTable(
            name = "reservation_service",
            joinColumns = @JoinColumn(name = "reservation_id"),
            inverseJoinColumns = @JoinColumn(name = "service_id")
    )
    private List<AdditionalService> additionalServices;

    public Reservation() {

    }

    public List<Equipment> getAdditionalEquipments() {
        return additionalEquipments;
    }

    public List<AdditionalService> getAdditionalServices() {
        return additionalServices;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public int getId() {
        return id;
    }

    public void setAdditionalEquipments(List<Equipment> additionalEquipments) {
        this.additionalEquipments = additionalEquipments;
    }

    public void setAdditionalServices(List<AdditionalService> additionalServices) {
        this.additionalServices = additionalServices;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public void setDayCount(int dayCount) {
        this.dayCount = dayCount;
    }

    public void setDropOffDateTime(LocalDateTime dropOffDateTime) {
        this.dropOffDateTime = dropOffDateTime;
    }

    public void setDropOffLocation(Location dropOffLocation) {
        this.dropOffLocation = dropOffLocation;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setMember(Member member) {
        this.member = member;
    }


    public void setPickUpDateTime(LocalDateTime pickUpDateTime) {
        this.pickUpDateTime = pickUpDateTime;
    }

    public void setPickUpLocation(Location pickUpLocation) {
        this.pickUpLocation = pickUpLocation;
    }

    public void setReservationNumber(String reservationNumber) {
        this.reservationNumber = reservationNumber;
    }

    public void setReturnDate(LocalDateTime returnDate) {
        this.returnDate = returnDate;
    }

    public void setStatus(ReservationStatus status) {
        this.status = status;
    }

    public Car getCar() {
        return car;
    }

    public int getDayCount() {
        return dayCount;
    }

    public LocalDateTime getDropOffDateTime() {
        return dropOffDateTime;
    }

    public LocalDateTime getPickUpDateTime() {
        return pickUpDateTime;
    }

    public LocalDateTime getReturnDate() {
        return returnDate;
    }

    public Location getDropOffLocation() {
        return dropOffLocation;
    }

    public Location getPickUpLocation() {
        return pickUpLocation;
    }

    public Member getMember() {
        return member;
    }

    public ReservationStatus getStatus() {
        return status;
    }

    public String getReservationNumber() {
        return reservationNumber;
    }

}

