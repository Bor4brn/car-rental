package com.boraandege.carrental.service;

import com.boraandege.carrental.dto.ReservationDTO;
import com.boraandege.carrental.enums.ReservationStatus;
import java.time.LocalDateTime;
import java.util.List;

public interface ReservationService {

    ReservationDTO makeReservation(ReservationDTO reservationDTO);

    ReservationDTO getReservationByNumber(String reservationNumber);

    List<ReservationDTO> getAllReservations();

    boolean addServiceToReservation(String reservationNumber, Long serviceId);

    boolean addEquipmentToReservation(String reservationNumber, Long equipmentId);

    boolean returnCar(String reservationNumber);

    boolean cancelReservation(String reservationNumber);

    boolean deleteReservation(String reservationNumber);

    List<ReservationDTO> getReservationsBetweenDates(LocalDateTime startDate, LocalDateTime endDate);
}
