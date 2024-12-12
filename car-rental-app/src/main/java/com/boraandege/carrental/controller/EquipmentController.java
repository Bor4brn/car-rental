package com.boraandege.carrental.controller;

import com.boraandege.carrental.dto.EquipmentDTO;
import com.boraandege.carrental.service.EquipmentService;
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
@RequestMapping("/equipments")
public class EquipmentController {

    private final EquipmentService equipmentService;

    public EquipmentController(EquipmentService equipmentService) {
        this.equipmentService = equipmentService;
    }

    @Operation(summary = "Add a new equipment", description = "Creates a new equipment and saves it to the database.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Equipment created successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = EquipmentDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content)
    })
    @PostMapping
    public ResponseEntity<EquipmentDTO> addEquipment(@RequestBody EquipmentDTO equipmentDTO) {
        EquipmentDTO newEquipment = equipmentService.addEquipment(equipmentDTO);
        return ResponseEntity.ok(newEquipment);
    }

    @Operation(summary = "Get equipment by ID", description = "Retrieves the details of an equipment by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Equipment retrieved successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = EquipmentDTO.class))),
            @ApiResponse(responseCode = "404", description = "Equipment not found", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<EquipmentDTO> getEquipmentById(
            @Parameter(description = "ID of the equipment to retrieve", example = "1")
            @PathVariable Long id) {
        EquipmentDTO equipment = equipmentService.getEquipmentById(id);
        return ResponseEntity.ok(equipment);
    }

    @Operation(summary = "Get all equipments", description = "Retrieves a list of all equipments.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of equipments retrieved successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = EquipmentDTO.class)))
    })
    @GetMapping
    public ResponseEntity<List<EquipmentDTO>> getAllEquipments() {
        List<EquipmentDTO> equipments = equipmentService.getAllEquipments();
        return ResponseEntity.ok(equipments);
    }

    @Operation(summary = "Update an equipment", description = "Updates the details of an existing equipment by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Equipment updated successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = EquipmentDTO.class))),
            @ApiResponse(responseCode = "404", description = "Equipment not found", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<EquipmentDTO> updateEquipment(
            @Parameter(description = "ID of the equipment to update", example = "1")
            @PathVariable Long id,
            @RequestBody EquipmentDTO equipmentDTO) {
        EquipmentDTO updatedEquipment = equipmentService.updateEquipment(id, equipmentDTO);
        return ResponseEntity.ok(updatedEquipment);
    }

    @Operation(summary = "Delete an equipment", description = "Deletes an equipment from the database by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Equipment deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Equipment not found", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEquipment(
            @Parameter(description = "ID of the equipment to delete", example = "1")
            @PathVariable Long id) {
        equipmentService.deleteEquipment(id);
        return ResponseEntity.noContent().build();
    }
}
