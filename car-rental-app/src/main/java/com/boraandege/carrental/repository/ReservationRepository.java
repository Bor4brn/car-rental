package com.boraandege.carrental.repository;

import com.boraandege.carrental.model.Reservation;
import com.boraandege.carrental.enums.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {


    List<Reservation> findByMemberId(Long memberId);


    List<Reservation> findByCarId(Long carId);


    List<Reservation> findByStatus(ReservationStatus status);


    List<Reservation> findByPickUpDateTimeBetween(LocalDateTime startDate, LocalDateTime endDate);


    List<Reservation> findByCarIdAndStatus(Long carId, ReservationStatus status);

    List<Reservation> findByMemberIdAndStatus(Long memberId, ReservationStatus status);

    Optional<Reservation> findByReservationNumber(String reservationNumber);

}

