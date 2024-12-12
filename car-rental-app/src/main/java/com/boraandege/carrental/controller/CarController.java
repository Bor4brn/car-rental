package com.boraandege.carrental.controller;

import com.boraandege.carrental.dto.CarDTO;
import com.boraandege.carrental.enums.CarType;
import com.boraandege.carrental.enums.TransmissionType;
import com.boraandege.carrental.service.CarService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cars")
public class CarController {

    private final CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    @Operation(summary = "Add a new car", description = "Creates a new car and saves it to the database.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Car created successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CarDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content)
    })
    @PostMapping
    public ResponseEntity<CarDTO> addCar(@RequestBody CarDTO carDTO) {
        CarDTO newCar = carService.addCar(carDTO);
        return ResponseEntity.ok(newCar);
    }

    @Operation(summary = "Get car by ID", description = "Retrieves the details of a car using its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Car retrieved successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CarDTO.class))),
            @ApiResponse(responseCode = "404", description = "Car not found", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<CarDTO> getCarById(
            @Parameter(description = "ID of the car to retrieve", example = "1")
            @PathVariable Long id) {
        CarDTO car = carService.getCarById(id);
        return ResponseEntity.ok(car);
    }

    @Operation(summary = "Get all cars", description = "Retrieves a list of all cars.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of cars retrieved successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CarDTO.class)))
    })
    @GetMapping
    public ResponseEntity<List<CarDTO>> getAllCars() {
        List<CarDTO> cars = carService.getAllCars();
        return ResponseEntity.ok(cars);
    }

    @Operation(summary = "Update car details", description = "Updates the details of an existing car by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Car updated successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CarDTO.class))),
            @ApiResponse(responseCode = "404", description = "Car not found", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<CarDTO> updateCar(
            @Parameter(description = "ID of the car to update", example = "1")
            @PathVariable Long id,
            @RequestBody CarDTO carDTO) {
        CarDTO updatedCar = carService.updateCar(id, carDTO);
        return ResponseEntity.ok(updatedCar);
    }

    @Operation(summary = "Delete a car", description = "Deletes a car from the database by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Car deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Car not found", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCar(
            @Parameter(description = "ID of the car to delete", example = "1")
            @PathVariable Long id) {
        carService.deleteCar(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Search for available cars", description = "Finds cars that are available based on type and transmission.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of available cars retrieved successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CarDTO.class))),
            @ApiResponse(responseCode = "404", description = "No available cars found", content = @Content)
    })
    @GetMapping("/available")
    public ResponseEntity<List<CarDTO>> searchAvailableCars(
            @Parameter(description = "Type of car (e.g., STANDARD, SUV)", example = "SUV")
            @RequestParam CarType carType,
            @Parameter(description = "Transmission type (e.g., AUTOMATIC, MANUAL)", example = "AUTOMATIC")
            @RequestParam TransmissionType transmissionType) {
        List<CarDTO> availableCars = carService.searchAvailableCars(carType, transmissionType);
        return ResponseEntity.ok(availableCars);
    }
    @Operation(summary = "Get all rented cars", description = "Retrieves a list of all cars that are currently rented or reserved.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of rented cars retrieved successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CarDTO.class))),
            @ApiResponse(responseCode = "404", description = "No rented cars found", content = @Content)
    })
    @GetMapping("/rented")
    public ResponseEntity<List<CarDTO>> getRentedCars() {
        List<CarDTO> rentedCars = carService.getRentedCars();
        if (rentedCars.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(rentedCars);
    }
}
