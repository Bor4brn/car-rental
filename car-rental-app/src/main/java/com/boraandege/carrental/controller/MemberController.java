package com.boraandege.carrental.controller;

import com.boraandege.carrental.dto.MemberDTO;
import com.boraandege.carrental.service.MemberService;
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
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @Operation(summary = "Register a new member", description = "Creates a new member and saves it to the database.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Member registered successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = MemberDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content)
    })
    @PostMapping
    public ResponseEntity<MemberDTO> registerMember(@RequestBody MemberDTO memberDTO) {
        MemberDTO newMember = memberService.registerMember(memberDTO);
        return ResponseEntity.ok(newMember);
    }

    @Operation(summary = "Get member by ID", description = "Retrieves the details of a member by their ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Member retrieved successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = MemberDTO.class))),
            @ApiResponse(responseCode = "404", description = "Member not found", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<MemberDTO> getMemberById(
            @Parameter(description = "ID of the member to retrieve", example = "1")
            @PathVariable Long id) {
        MemberDTO member = memberService.getMemberById(id);
        return ResponseEntity.ok(member);
    }

    @Operation(summary = "Get all members", description = "Retrieves a list of all registered members.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of members retrieved successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = MemberDTO.class)))
    })
    @GetMapping
    public ResponseEntity<List<MemberDTO>> getAllMembers() {
        List<MemberDTO> members = memberService.getAllMembers();
        return ResponseEntity.ok(members);
    }

    @Operation(summary = "Update a member", description = "Updates the details of an existing member by their ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Member updated successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = MemberDTO.class))),
            @ApiResponse(responseCode = "404", description = "Member not found", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<MemberDTO> updateMember(
            @Parameter(description = "ID of the member to update", example = "1")
            @PathVariable Long id,
            @RequestBody MemberDTO memberDTO) {
        MemberDTO updatedMember = memberService.updateMember(id, memberDTO);
        return ResponseEntity.ok(updatedMember);
    }

    @Operation(summary = "Delete a member", description = "Deletes a member from the database by their ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Member deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Member not found", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMember(
            @Parameter(description = "ID of the member to delete", example = "1")
            @PathVariable Long id) {
        memberService.deleteMember(id);
        return ResponseEntity.noContent().build();
    }
}
