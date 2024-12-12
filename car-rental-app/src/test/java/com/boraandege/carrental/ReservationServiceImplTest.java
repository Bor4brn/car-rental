package com.boraandege.carrental;

import com.boraandege.carrental.dto.ReservationDTO;
import com.boraandege.carrental.enums.CarStatus;
import com.boraandege.carrental.enums.CarType;
import com.boraandege.carrental.enums.ReservationStatus;
import com.boraandege.carrental.enums.TransmissionType;
import com.boraandege.carrental.exception.ResourceNotFoundException;
import com.boraandege.carrental.model.*;
import com.boraandege.carrental.repository.*;
import com.boraandege.carrental.service.impl.ReservationServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ReservationServiceImplTest {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private EquipmentRepository equipmentRepository;

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private ReservationServiceImpl reservationService;

    @Test
    void testMakeReservation() {
        reservationRepository.deleteAll();
        carRepository.deleteAll();
        memberRepository.deleteAll();
        locationRepository.deleteAll();

        Location pickUpLocation = new Location();
        pickUpLocation.setCode("LOC1");
        pickUpLocation.setName("Test Pickup Location");
        pickUpLocation.setAddress("123 Main St");
        locationRepository.save(pickUpLocation);

        Location dropOffLocation = new Location();
        dropOffLocation.setCode("LOC2");
        dropOffLocation.setName("Test Dropoff Location");
        dropOffLocation.setAddress("456 Main St");
        locationRepository.save(dropOffLocation);

        Member member = new Member();
        member.setName("Test Member");
        member.setAddress("789 Elm St");
        member.setEmail("test.member@example.com");
        member.setPhone("555-1234");
        member.setDrivingLicenseNumber("DL12345");
        memberRepository.save(member);

        Car car = new Car();
        car.setBarcodeNumber("CAR1");
        car.setLicensePlateNumber("34TEST34");
        car.setPassengerCapacity(4);
        car.setBrand("TestBrand");
        car.setModel("TestModel");
        car.setMileage(5000);
        car.setTransmissionType(TransmissionType.AUTOMATIC);
        car.setDailyPrice(BigDecimal.valueOf(100.00));
        car.setStatus(CarStatus.AVAILABLE);
        car.setCarType(CarType.ECONOMY);
        carRepository.save(car);

        ReservationDTO reservationDTO = new ReservationDTO();
        reservationDTO.setCarBarcodeNumber(car.getBarcodeNumber());
        reservationDTO.setMemberId(member.getId());
        reservationDTO.setPickUpLocationCode(pickUpLocation.getCode());
        reservationDTO.setDropOffLocationCode(dropOffLocation.getCode());
        reservationDTO.setDayCount(3);

        ReservationDTO result = reservationService.makeReservation(reservationDTO);

        assertNotNull(result);
        assertNotNull(result.getReservationNumber());
        assertEquals(3, result.getDayCount());
        assertEquals(ReservationStatus.ACTIVE, result.getStatus());

        Optional<Reservation> savedReservation = reservationRepository.findByReservationNumber(result.getReservationNumber());
        assertTrue(savedReservation.isPresent());
    }

    @Test
    void testGetReservationByNumber() {
        reservationRepository.deleteAll();

        Reservation reservation = new Reservation();
        reservation.setReservationNumber("RES123");
        reservation.setDayCount(2);
        reservation.setStatus(ReservationStatus.ACTIVE);
        reservation.setCreationDate(LocalDateTime.now());
        reservation.setPickUpDateTime(LocalDateTime.now().plusDays(1));
        reservation.setDropOffDateTime(LocalDateTime.now().plusDays(3));

        reservationRepository.save(reservation);

        ReservationDTO result = reservationService.getReservationByNumber("RES123");

        assertNotNull(result);
        assertEquals("RES123", result.getReservationNumber());
    }

    @Test
    void testGetReservationByNumber_NotFound() {
        reservationRepository.deleteAll();
        assertThrows(ResourceNotFoundException.class, () -> reservationService.getReservationByNumber("INVALID_RES"));
    }

    @Test
    void testCancelReservation() {
        reservationRepository.deleteAll();
        carRepository.deleteAll();

        Car car = new Car();
        car.setBarcodeNumber("CAR1");
        car.setStatus(CarStatus.LOANED);
        carRepository.save(car);

        Reservation reservation = new Reservation();
        reservation.setReservationNumber("RES789");
        reservation.setStatus(ReservationStatus.ACTIVE);
        reservation.setCar(car);

        reservationRepository.save(reservation);

        boolean cancelled = reservationService.cancelReservation("RES789");

        assertTrue(cancelled);

        Reservation cancelledReservation = reservationRepository.findByReservationNumber("RES789")
                .orElseThrow(() -> new ResourceNotFoundException("Reservation not found"));

        assertEquals(ReservationStatus.CANCELLED, cancelledReservation.getStatus());
        assertEquals(CarStatus.AVAILABLE, carRepository.findByBarcodeNumber("CAR1").get().getStatus());
    }


    @Test
    void testReturnCar() {
        reservationRepository.deleteAll();
        carRepository.deleteAll();

        Car car = new Car();
        car.setBarcodeNumber("CAR1");
        car.setStatus(CarStatus.LOANED);
        carRepository.save(car);

        Reservation reservation = new Reservation();
        reservation.setReservationNumber("RES789");
        reservation.setStatus(ReservationStatus.ACTIVE);
        reservation.setCar(car);

        reservationRepository.save(reservation);

        boolean returned = reservationService.returnCar("RES789");

        assertTrue(returned);

        Reservation returnedReservation = reservationRepository.findByReservationNumber("RES789")
                .orElseThrow(() -> new ResourceNotFoundException("Reservation not found"));

        // Compare enums directly
        assertEquals(ReservationStatus.COMPLETED, returnedReservation.getStatus());
        assertEquals(CarStatus.AVAILABLE, carRepository.findByBarcodeNumber("CAR1").get().getStatus());
    }


    @Test
    void testDeleteReservation() {
        reservationRepository.deleteAll();

        Reservation reservation = new Reservation();
        reservation.setReservationNumber("RES456");
        reservation.setStatus(ReservationStatus.PENDING);

        reservationRepository.save(reservation);

        boolean deleted = reservationService.deleteReservation("RES456");

        assertTrue(deleted);

        Optional<Reservation> deletedReservation = reservationRepository.findByReservationNumber("RES456");
        assertTrue(deletedReservation.isEmpty());
    }


}
