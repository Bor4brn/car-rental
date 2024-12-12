package com.boraandege.carrental.service.impl;

import com.boraandege.carrental.dto.ReservationDTO;
import com.boraandege.carrental.model.*;
import com.boraandege.carrental.enums.CarStatus;
import com.boraandege.carrental.enums.ReservationStatus;
import com.boraandege.carrental.exception.BusinessException;
import com.boraandege.carrental.exception.ResourceNotFoundException;
import com.boraandege.carrental.mapper.ReservationMapper;
import com.boraandege.carrental.repository.*;
import com.boraandege.carrental.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final CarRepository carRepository;
    private final MemberRepository memberRepository;
    private final LocationRepository locationRepository;
    private final EquipmentRepository equipmentRepository;
    private final ServiceRepository serviceRepository;

    private final ReservationMapper reservationMapper;

    @Autowired
    public ReservationServiceImpl(
            ReservationRepository reservationRepository,
            CarRepository carRepository,
            MemberRepository memberRepository,
            LocationRepository locationRepository,
            EquipmentRepository equipmentRepository,
            ServiceRepository serviceRepository,
            ReservationMapper reservationMapper) {
        this.reservationRepository = reservationRepository;
        this.carRepository = carRepository;
        this.memberRepository = memberRepository;
        this.locationRepository = locationRepository;
        this.equipmentRepository = equipmentRepository;
        this.serviceRepository = serviceRepository;
        this.reservationMapper = reservationMapper;
    }

    @Override
    @Transactional
    public ReservationDTO makeReservation(ReservationDTO reservationDTO) {
        Car car = carRepository.findByBarcodeNumber(reservationDTO.getCarBarcodeNumber())
                .orElseThrow(() -> new ResourceNotFoundException("Car not found"));

        if (car.getStatus() != CarStatus.AVAILABLE) {
            throw new BusinessException("Selected car is not available");
        }

        Member member = memberRepository.findById(reservationDTO.getMemberId())
                .orElseThrow(() -> new ResourceNotFoundException("Member not found"));

        Location pickUpLocation = locationRepository.findByCode(reservationDTO.getPickUpLocationCode())
                .orElseThrow(() -> new ResourceNotFoundException("Pick-up location not found"));
        Location dropOffLocation = locationRepository.findByCode(reservationDTO.getDropOffLocationCode())
                .orElseThrow(() -> new ResourceNotFoundException("Drop-off location not found"));

        List<Equipment> equipments = new ArrayList<>();
        if (reservationDTO.getAdditionalEquipmentIds() != null) {
            equipments = equipmentRepository.findAllById(reservationDTO.getAdditionalEquipmentIds());
        }


        List<AdditionalService> services = new ArrayList<>();
        if (reservationDTO.getAdditionalServiceIds() != null) {
            services = serviceRepository.findAllById(reservationDTO.getAdditionalServiceIds());
        }

        Reservation reservation = new Reservation();
        reservation.setReservationNumber(generateReservationNumber());
        reservation.setCreationDate(LocalDateTime.now());
        reservation.setPickUpDateTime(LocalDateTime.now().plusDays(1));
        reservation.setDropOffDateTime(reservation.getPickUpDateTime().plusDays(reservationDTO.getDayCount()));
        reservation.setStatus(ReservationStatus.ACTIVE);
        reservation.setDayCount(reservationDTO.getDayCount());
        reservation.setMember(member);
        reservation.setCar(car);
        reservation.setPickUpLocation(pickUpLocation);
        reservation.setDropOffLocation(dropOffLocation);
        reservation.setAdditionalEquipments(equipments);
        reservation.setAdditionalServices(services);

        car.setStatus(CarStatus.LOANED);
        carRepository.save(car);

        Reservation savedReservation = reservationRepository.save(reservation);


        double totalAmount = calculateTotalAmount(reservation);
        ReservationDTO resultDTO = reservationMapper.toDTO(savedReservation);
        resultDTO.setTotalAmount(totalAmount);

        return resultDTO;
    }

    @Override
    public ReservationDTO getReservationByNumber(String reservationNumber) {
        Reservation reservation = reservationRepository.findByReservationNumber(reservationNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation not found with number: " + reservationNumber));
        return reservationMapper.toDTO(reservation);
    }

    @Override
    public List<ReservationDTO> getAllReservations() {
        List<Reservation> reservations = reservationRepository.findAll();
        return reservations.stream()
                .map(reservationMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public boolean addServiceToReservation(String reservationNumber, Long serviceId) {
        Reservation reservation = reservationRepository.findByReservationNumber(reservationNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation not found"));
        AdditionalService service = serviceRepository.findById(serviceId)
                .orElseThrow(() -> new ResourceNotFoundException("Service not found"));

        if (reservation.getAdditionalServices().contains(service)) {
            return false;
        }

        reservation.getAdditionalServices().add(service);
        reservationRepository.save(reservation);
        return true;
    }

    @Override
    @Transactional
    public boolean addEquipmentToReservation(String reservationNumber, Long equipmentId) {
        Reservation reservation = reservationRepository.findByReservationNumber(reservationNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation not found"));
        Equipment equipment = equipmentRepository.findById(equipmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Equipment not found"));

        if (reservation.getAdditionalEquipments().contains(equipment)) {
            return false;
        }

        reservation.getAdditionalEquipments().add(equipment);
        reservationRepository.save(reservation);
        return true;
    }

    @Override
    @Transactional
    public boolean returnCar(String reservationNumber) {
        Reservation reservation = reservationRepository.findByReservationNumber(reservationNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation not found"));

        if (reservation.getStatus() != ReservationStatus.ACTIVE) {
            return false;
        }

        reservation.setStatus(ReservationStatus.COMPLETED);
        reservation.setReturnDate(LocalDateTime.now());

        Car car = reservation.getCar();
        car.setStatus(CarStatus.AVAILABLE);
        carRepository.save(car);

        reservationRepository.save(reservation);
        return true;
    }

    @Override
    @Transactional
    public boolean cancelReservation(String reservationNumber) {
        Reservation reservation = reservationRepository.findByReservationNumber(reservationNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation not found"));

        if (reservation.getStatus() != ReservationStatus.ACTIVE) {
            return false;
        }

        reservation.setStatus(ReservationStatus.CANCELLED);

        Car car = reservation.getCar();
        car.setStatus(CarStatus.AVAILABLE);
        carRepository.save(car);

        reservationRepository.save(reservation);
        return true;
    }

    @Override
    @Transactional
    public boolean deleteReservation(String reservationNumber) {
        Reservation reservation = reservationRepository.findByReservationNumber(reservationNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation not found"));

        // Disassociate related entities
        reservation.setCar(null);
        reservation.setMember(null);
        reservation.setAdditionalEquipments(null);
        reservation.setAdditionalServices(null);

        reservationRepository.delete(reservation);
        return true;
    }

    @Override
    public List<ReservationDTO> getReservationsBetweenDates(LocalDateTime startDate, LocalDateTime endDate) {
        List<Reservation> reservations = reservationRepository.findByPickUpDateTimeBetween(startDate, endDate);
        return reservations.stream()
                .map(reservationMapper::toDTO)
                .collect(Collectors.toList());
    }


    private String generateReservationNumber() {

        return String.format("%08d", new Random().nextInt(100_000_000));
    }

    private double calculateTotalAmount(Reservation reservation) {
        double total = reservation.getDayCount() * reservation.getCar().getDailyPrice().doubleValue();

        for (AdditionalService service : reservation.getAdditionalServices()) {
            total += service.getPrice().doubleValue();
        }

        for (Equipment equipment : reservation.getAdditionalEquipments()) {
            total += equipment.getPrice().doubleValue();
        }

        return total;
    }
}
