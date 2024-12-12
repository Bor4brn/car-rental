package com.boraandege.carrental.controller;

import com.boraandege.carrental.dto.ServiceDTO;
import com.boraandege.carrental.service.ServiceService;
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
@RequestMapping("/services")
public class ServiceController {

    private final ServiceService serviceService;

    public ServiceController(ServiceService serviceService) {
        this.serviceService = serviceService;
    }

    @Operation(summary = "Add a new service", description = "Creates a new additional service and saves it to the database.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Service created successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServiceDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content)
    })
    @PostMapping
    public ResponseEntity<ServiceDTO> addService(@RequestBody ServiceDTO serviceDTO) {
        ServiceDTO newService = serviceService.addService(serviceDTO);
        return ResponseEntity.ok(newService);
    }

    @Operation(summary = "Get a service by ID", description = "Retrieves the details of a specific additional service by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Service retrieved successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServiceDTO.class))),
            @ApiResponse(responseCode = "404", description = "Service not found", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<ServiceDTO> getServiceById(
            @Parameter(description = "ID of the service to retrieve", example = "1")
            @PathVariable Long id) {
        ServiceDTO service = serviceService.getServiceById(id);
        return ResponseEntity.ok(service);
    }

    @Operation(summary = "Get all services", description = "Retrieves a list of all additional services.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of services retrieved successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServiceDTO.class)))
    })
    @GetMapping
    public ResponseEntity<List<ServiceDTO>> getAllServices() {
        List<ServiceDTO> services = serviceService.getAllServices();
        return ResponseEntity.ok(services);
    }

    @Operation(summary = "Update a service", description = "Updates the details of an existing additional service.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Service updated successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServiceDTO.class))),
            @ApiResponse(responseCode = "404", description = "Service not found", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<ServiceDTO> updateService(
            @Parameter(description = "ID of the service to update", example = "1")
            @PathVariable Long id,
            @RequestBody ServiceDTO serviceDTO) {
        ServiceDTO updatedService = serviceService.updateService(id, serviceDTO);
        return ResponseEntity.ok(updatedService);
    }

    @Operation(summary = "Delete a service", description = "Deletes a specific additional service by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Service deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Service not found", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteService(
            @Parameter(description = "ID of the service to delete", example = "1")
            @PathVariable Long id) {
        serviceService.deleteService(id);
        return ResponseEntity.noContent().build();
    }
}
