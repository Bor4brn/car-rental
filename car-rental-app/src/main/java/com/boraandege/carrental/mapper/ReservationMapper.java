package com.boraandege.carrental.mapper;

import com.boraandege.carrental.dto.ReservationDTO;
import com.boraandege.carrental.model.AdditionalService;
import com.boraandege.carrental.model.Reservation;
import com.boraandege.carrental.model.Equipment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ReservationMapper {

    ReservationMapper INSTANCE = Mappers.getMapper(ReservationMapper.class);

    @Mappings({
            @Mapping(source = "member.id", target = "memberId"),
            @Mapping(source = "member.name", target = "memberName"),
            @Mapping(source = "car.barcodeNumber", target = "carBarcodeNumber"),
            @Mapping(source = "car.brand", target = "carBrand"),
            @Mapping(source = "car.model", target = "carModel"),
            @Mapping(source = "pickUpLocation.code", target = "pickUpLocationCode"),
            @Mapping(source = "pickUpLocation.name", target = "pickUpLocationName"),
            @Mapping(source = "dropOffLocation.code", target = "dropOffLocationCode"),
            @Mapping(source = "dropOffLocation.name", target = "dropOffLocationName"),
            @Mapping(target = "additionalEquipmentIds", expression = "java(getEquipmentIds(reservation.getAdditionalEquipments()))"),
            @Mapping(target = "additionalEquipmentNames", expression = "java(getEquipmentNames(reservation.getAdditionalEquipments()))"),
            @Mapping(target = "additionalServiceIds", expression = "java(getServiceIds(reservation.getAdditionalServices()))"),
            @Mapping(target = "additionalServiceNames", expression = "java(getServiceNames(reservation.getAdditionalServices()))"),
            @Mapping(target = "totalAmount", expression = "java(calculateTotalAmount(reservation))")
    })
    ReservationDTO toDTO(Reservation reservation);

    @Mappings({
            @Mapping(source = "memberId", target = "member.id"),
            @Mapping(source = "carBarcodeNumber", target = "car.barcodeNumber"),
            @Mapping(source = "pickUpLocationCode", target = "pickUpLocation.code"),
            @Mapping(source = "dropOffLocationCode", target = "dropOffLocation.code"),
            @Mapping(target = "additionalEquipments", ignore = true),
            @Mapping(target = "additionalServices", ignore = true)
    })
    Reservation toEntity(ReservationDTO reservationDTO);

    default List<Long> getEquipmentIds(List<Equipment> equipments) {
        if (equipments == null) {
            return null;
        }
        return equipments.stream()
                .map(Equipment::getId)
                .collect(Collectors.toList());
    }

    default List<String> getEquipmentNames(List<Equipment> equipments) {
        if (equipments == null) {
            return null;
        }
        return equipments.stream()
                .map(Equipment::getName)
                .collect(Collectors.toList());
    }

    default List<Long> getServiceIds(List<AdditionalService> services) {
        if (services == null) {
            return null;
        }
        return services.stream()
                .map(AdditionalService::getId)
                .collect(Collectors.toList());
    }

    default List<String> getServiceNames(List<AdditionalService> services) {
        if (services == null) {
            return null;
        }
        return services.stream()
                .map(AdditionalService::getName)
                .collect(Collectors.toList());
    }

    default Double calculateTotalAmount(Reservation reservation) {
        if (reservation.getCar() != null && reservation.getCar().getDailyPrice() != null) {
            double carCost = reservation.getDayCount() * reservation.getCar().getDailyPrice().doubleValue();
            double equipmentCost = reservation.getAdditionalEquipments() != null
                    ? reservation.getAdditionalEquipments().stream()
                    .mapToDouble(e -> e.getPrice().doubleValue())
                    .sum()
                    : 0.0;
            double serviceCost = reservation.getAdditionalServices() != null
                    ? reservation.getAdditionalServices().stream()
                    .mapToDouble(s -> s.getPrice().doubleValue())
                    .sum()
                    : 0.0;
            return carCost + equipmentCost + serviceCost;
        }
        return 0.0;
    }
}
