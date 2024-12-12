package com.boraandege.carrental.controller;

import com.boraandege.carrental.dto.ReservationDTO;
import com.boraandege.carrental.service.ReservationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @Operation(summary = "Create a new reservation", description = "Creates a reservation for a car and assigns it to a member.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reservation created successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ReservationDTO.class))),
            @ApiResponse(responseCode = "404", description = "Car or member not found"),
            @ApiResponse(responseCode = "406", description = "Car not available for reservation")
    })
    @PostMapping
    public ResponseEntity<ReservationDTO> makeReservation(@RequestBody ReservationDTO reservationDTO) {
        ReservationDTO createdReservation = reservationService.makeReservation(reservationDTO);
        return ResponseEntity.ok(createdReservation);
    }

    @Operation(summary = "Get reservation by number", description = "Retrieves the details of a reservation using its number.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reservation retrieved successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ReservationDTO.class))),
            @ApiResponse(responseCode = "404", description = "Reservation not found")
    })
    @GetMapping("/{reservationNumber}")
    public ResponseEntity<ReservationDTO> getReservationByNumber(
            @Parameter(description = "Number of the reservation to retrieve", example = "RES12345678")
            @PathVariable String reservationNumber) {
        ReservationDTO reservation = reservationService.getReservationByNumber(reservationNumber);
        return ResponseEntity.ok(reservation);
    }

    @Operation(summary = "Get all reservations", description = "Retrieves a list of all reservations.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of reservations retrieved successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ReservationDTO.class)))
    })
    @GetMapping
    public ResponseEntity<List<ReservationDTO>> getAllReservations() {
        List<ReservationDTO> reservations = reservationService.getAllReservations();
        return ResponseEntity.ok(reservations);
    }

    @Operation(summary = "Add a service to a reservation", description = "Adds an additional service to an existing reservation.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Service added successfully"),
            @ApiResponse(responseCode = "404", description = "Reservation or service not found")
    })
    @PostMapping("/{reservationNumber}/services/{serviceId}")
    public ResponseEntity<Boolean> addServiceToReservation(
            @Parameter(description = "Number of the reservation", example = "RES12345678")
            @PathVariable String reservationNumber,
            @Parameter(description = "ID of the service to add", example = "1")
            @PathVariable Long serviceId) {
        boolean result = reservationService.addServiceToReservation(reservationNumber, serviceId);
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "Add equipment to a reservation", description = "Adds additional equipment to an existing reservation.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Equipment added successfully"),
            @ApiResponse(responseCode = "404", description = "Reservation or equipment not found")
    })
    @PostMapping("/{reservationNumber}/equipments/{equipmentId}")
    public ResponseEntity<Boolean> addEquipmentToReservation(
            @Parameter(description = "Number of the reservation", example = "RES12345678")
            @PathVariable String reservationNumber,
            @Parameter(description = "ID of the equipment to add", example = "2")
            @PathVariable Long equipmentId) {
        boolean result = reservationService.addEquipmentToReservation(reservationNumber, equipmentId);
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "Return a car for a reservation", description = "Marks a car as returned for a given reservation.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Car returned successfully"),
            @ApiResponse(responseCode = "404", description = "Reservation not found")
    })
    @PutMapping("/{reservationNumber}/return")
    public ResponseEntity<Boolean> returnCar(
            @Parameter(description = "Number of the reservation", example = "RES12345678")
            @PathVariable String reservationNumber) {
        boolean result = reservationService.returnCar(reservationNumber);
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "Cancel a reservation", description = "Cancels an existing reservation.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reservation canceled successfully"),
            @ApiResponse(responseCode = "404", description = "Reservation not found")
    })
    @PutMapping("/{reservationNumber}/cancel")
    public ResponseEntity<Boolean> cancelReservation(
            @Parameter(description = "Number of the reservation to cancel", example = "RES12345678")
            @PathVariable String reservationNumber) {
        boolean result = reservationService.cancelReservation(reservationNumber);
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "Delete a reservation", description = "Deletes a reservation from the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reservation deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Reservation not found")
    })
    @DeleteMapping("/{reservationNumber}")
    public ResponseEntity<Boolean> deleteReservation(
            @Parameter(description = "Number of the reservation to delete", example = "RES12345678")
            @PathVariable String reservationNumber) {
        boolean result = reservationService.deleteReservation(reservationNumber);
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "Get reservations between two dates", description = "Retrieves reservations created between the specified start and end dates.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of reservations retrieved successfully")
    })
    @GetMapping("/between")
    public ResponseEntity<List<ReservationDTO>> getReservationsBetweenDates(
            @Parameter(description = "Start date for filtering reservations", example = "2023-01-01T00:00:00")
            @RequestParam LocalDateTime startDate,
            @Parameter(description = "End date for filtering reservations", example = "2023-12-31T23:59:59")
            @RequestParam LocalDateTime endDate) {
        List<ReservationDTO> reservations = reservationService.getReservationsBetweenDates(startDate, endDate);
        return ResponseEntity.ok(reservations);
    }
}
