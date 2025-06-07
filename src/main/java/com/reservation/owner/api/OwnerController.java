package com.reservation.owner.api;

import com.reservation.owner.application.dto.OwnerDto;
import com.reservation.owner.application.facade.OwnerFacade;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@RestController
@RequestMapping("owners")
@RequiredArgsConstructor
public class OwnerController {
    private final OwnerFacade ownerFacade;

    /**
     * Creates a new owner.
     *
     * @param ownerDto the owner data
     * @return the created owner with generated ID
     */
    @PostMapping
    public ResponseEntity<OwnerDto> createOwner(@Valid @RequestBody OwnerDto ownerDto) {
        OwnerDto createdOwner = ownerFacade.createOwner(ownerDto);
        return new ResponseEntity<>(createdOwner, HttpStatus.CREATED);
    }

    /**
     * Retrieves an owner by ID.
     *
     * @param id the owner ID
     * @return the owner data if found
     */
    @GetMapping("/{id}")
    public ResponseEntity<OwnerDto> getOwnerById(@PathVariable Long id) {
        OwnerDto owner = ownerFacade.getOwnerById(id);
        return ResponseEntity.ok(owner);
    }

    /**
     * Retrieves all owners.
     *
     * @return list of all owners
     */
    @GetMapping
    public ResponseEntity<List<OwnerDto>> getAllOwners() {
        List<OwnerDto> owners = ownerFacade.getAllOwners();
        return ResponseEntity.ok(owners);
    }
}
