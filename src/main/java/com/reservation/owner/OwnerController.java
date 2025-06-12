package com.reservation.owner;

import com.reservation.common.BaseController;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Log4j2
@RestController
@RequestMapping("/api/v1/owners")
@RequiredArgsConstructor
class OwnerController extends BaseController {
    private final OwnerService ownerService;

    @PostMapping
    public OwnerResponseDto createOwner(@Valid @RequestBody OwnerRequestDto ownerDto) {
        return ownerService.createOwner(ownerDto);
    }

    @GetMapping("/{id}")
    public OwnerResponseDto getOwnerById(@PathVariable Long id) {
        return ownerService.getOwnerById(id);
    }

    @GetMapping
    public List<OwnerResponseDto> getAllOwners() {
        return ownerService.getAllOwners();
    }

    @Override
    protected Logger getLogger() {
        return log;
    }
}
