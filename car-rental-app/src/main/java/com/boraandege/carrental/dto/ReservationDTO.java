package com.boraandege.carrental.dto;

import com.boraandege.carrental.enums.ReservationStatus;
import java.time.LocalDateTime;
import java.util.List;

public class ReservationDTO {

    private Long id;
    private String reservationNumber;
    private LocalDateTime creationDate;
    private LocalDateTime pickUpDateTime;
    private LocalDateTime dropOffDateTime;
    private LocalDateTime returnDate;
    private ReservationStatus status;
    private int dayCount;
    private Long memberId;
    private String memberName;
    private String carBarcodeNumber;
    private String carBrand;
    private String carModel;
    private String pickUpLocationCode;
    private String pickUpLocationName;
    private String dropOffLocationCode;
    private String dropOffLocationName;
    private List<Long> additionalEquipmentIds;
    private List<String> additionalEquipmentNames;
    private List<Long> additionalServiceIds;
    private List<String> additionalServiceNames;
    private Double totalAmount;

    // Getters and Setters for all fields
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReservationNumber() {
        return reservationNumber;
    }


    public void setReservationNumber(String reservationNumber) {
        this.reservationNumber = reservationNumber;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDateTime getPickUpDateTime() {
        return pickUpDateTime;
    }

    public void setPickUpDateTime(LocalDateTime pickUpDateTime) {
        this.pickUpDateTime = pickUpDateTime;
    }

    public LocalDateTime getDropOffDateTime() {
        return dropOffDateTime;
    }

    public void setDropOffDateTime(LocalDateTime dropOffDateTime) {
        this.dropOffDateTime = dropOffDateTime;
    }

    public LocalDateTime getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDateTime returnDate) {
        this.returnDate = returnDate;
    }

    public ReservationStatus getStatus() {
        return status;
    }

    public void setStatus(ReservationStatus status) {
        this.status = status;
    }

    public int getDayCount() {
        return dayCount;
    }

    public void setDayCount(int dayCount) {
        this.dayCount = dayCount;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getCarBarcodeNumber() {
        return carBarcodeNumber;
    }

    public void setCarBarcodeNumber(String carBarcodeNumber) {
        this.carBarcodeNumber = carBarcodeNumber;
    }

    public String getCarBrand() {
        return carBrand;
    }

    public void setCarBrand(String carBrand) {
        this.carBrand = carBrand;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public String getPickUpLocationCode() {
        return pickUpLocationCode;
    }

    public void setPickUpLocationCode(String pickUpLocationCode) {
        this.pickUpLocationCode = pickUpLocationCode;
    }

    public String getPickUpLocationName() {
        return pickUpLocationName;
    }

    public void setPickUpLocationName(String pickUpLocationName) {
        this.pickUpLocationName = pickUpLocationName;
    }

    public String getDropOffLocationCode() {
        return dropOffLocationCode;
    }

    public void setDropOffLocationCode(String dropOffLocationCode) {
        this.dropOffLocationCode = dropOffLocationCode;
    }

    public String getDropOffLocationName() {
        return dropOffLocationName;
    }

    public void setDropOffLocationName(String dropOffLocationName) {
        this.dropOffLocationName = dropOffLocationName;
    }

    public List<Long> getAdditionalEquipmentIds() {
        return additionalEquipmentIds;
    }

    public void setAdditionalEquipmentIds(List<Long> additionalEquipmentIds) {
        this.additionalEquipmentIds = additionalEquipmentIds;
    }

    public List<String> getAdditionalEquipmentNames() {
        return additionalEquipmentNames;
    }

    public void setAdditionalEquipmentNames(List<String> additionalEquipmentNames) {
        this.additionalEquipmentNames = additionalEquipmentNames;
    }

    public List<Long> getAdditionalServiceIds() {
        return additionalServiceIds;
    }

    public void setAdditionalServiceIds(List<Long> additionalServiceIds) {
        this.additionalServiceIds = additionalServiceIds;
    }

    public List<String> getAdditionalServiceNames() {
        return additionalServiceNames;
    }

    public void setAdditionalServiceNames(List<String> additionalServiceNames) {
        this.additionalServiceNames = additionalServiceNames;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }
}
