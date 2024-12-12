package com.boraandege.carrental.controller;

import com.boraandege.carrental.dto.LocationDTO;
import com.boraandege.carrental.service.LocationService;
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
@RequestMapping("/locations")
public class LocationController {

    private final LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @Operation(summary = "Add a new location", description = "Creates a new location and saves it to the database.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Location created successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = LocationDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content)
    })
    @PostMapping
    public ResponseEntity<LocationDTO> addLocation(@RequestBody LocationDTO locationDTO) {
        LocationDTO newLocation = locationService.addLocation(locationDTO);
        return ResponseEntity.ok(newLocation);
    }

    @Operation(summary = "Get location by ID", description = "Retrieves the details of a location by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Location retrieved successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = LocationDTO.class))),
            @ApiResponse(responseCode = "404", description = "Location not found", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<LocationDTO> getLocationById(
            @Parameter(description = "ID of the location to retrieve", example = "1")
            @PathVariable Long id) {
        LocationDTO location = locationService.getLocationById(id);
        return ResponseEntity.ok(location);
    }

    @Operation(summary = "Get location by code", description = "Retrieves the details of a location by its code.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Location retrieved successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = LocationDTO.class))),
            @ApiResponse(responseCode = "404", description = "Location not found", content = @Content)
    })
    @GetMapping("/code/{code}")
    public ResponseEntity<LocationDTO> getLocationByCode(
            @Parameter(description = "Code of the location to retrieve", example = "LOC1")
            @PathVariable String code) {
        LocationDTO location = locationService.getLocationByCode(code);
        return ResponseEntity.ok(location);
    }

    @Operation(summary = "Get all locations", description = "Retrieves a list of all locations.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of locations retrieved successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = LocationDTO.class)))
    })
    @GetMapping
    public ResponseEntity<List<LocationDTO>> getAllLocations() {
        List<LocationDTO> locations = locationService.getAllLocations();
        return ResponseEntity.ok(locations);
    }

    @Operation(summary = "Update a location", description = "Updates the details of an existing location by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Location updated successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = LocationDTO.class))),
            @ApiResponse(responseCode = "404", description = "Location not found", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<LocationDTO> updateLocation(
            @Parameter(description = "ID of the location to update", example = "1")
            @PathVariable Long id,
            @RequestBody LocationDTO locationDTO) {
        LocationDTO updatedLocation = locationService.updateLocation(id, locationDTO);
        return ResponseEntity.ok(updatedLocation);
    }

    @Operation(summary = "Delete a location", description = "Deletes a location from the database by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Location deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Location not found", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLocation(
            @Parameter(description = "ID of the location to delete", example = "1")
            @PathVariable Long id) {
        locationService.deleteLocation(id);
        return ResponseEntity.noContent().build();
    }
}
