package com.reservation.owner;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OwnerServiceTest {

    @Mock
    private OwnerRepository ownerRepository;

    @InjectMocks
    private OwnerService ownerService;

    private static final Instant fixedInstant = Instant.parse("2024-01-01T12:00:00Z");

    private static final Owner owner = new Owner(
            1L, "John", "Doe", "keycloak-user-123", fixedInstant);

    private static final OwnerResponseDto ownerResponse = new OwnerResponseDto(
            1L, "John", "Doe", fixedInstant);

    @Test
    void shouldCreateNewOwnerSuccessfully() {
        OwnerRequestDto inputDto = new OwnerRequestDto("John", "Doe");
        when(ownerRepository.save(any(Owner.class))).thenReturn(owner);

        OwnerResponseDto result = ownerService.createOwner(inputDto);

        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(ownerResponse);

        verify(ownerRepository).save(any(Owner.class));
        // TODO: When Keycloak is integrated, verify existsByKeycloakUserId call
    }

    @Test
    void shouldReturnOwnerWhenExists() {
        when(ownerRepository.findById(1L)).thenReturn(Optional.of(owner));

        OwnerResponseDto result = ownerService.getOwnerById(1L);

        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(ownerResponse);
    }

    @Test
    void shouldThrowOwnerExceptionWhenOwnerNotFoundWithCorrectMessage() {
        when(ownerRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> ownerService.getOwnerById(1L))
                .isInstanceOf(OwnerException.class)
                .hasMessage("Owner with id: 1 not found");
    }

    @Test
    void shouldReturnListOfAllOwners() {
        when(ownerRepository.findAll()).thenReturn(List.of(owner));

        List<OwnerResponseDto> result = ownerService.getAllOwners();

        assertThat(result)
                .hasSize(1)
                .first()
                .usingRecursiveComparison()
                .isEqualTo(ownerResponse);
    }

    @Test
    void shouldReturnEmptyListWhenNoOwnersExist() {
        when(ownerRepository.findAll()).thenReturn(List.of());

        List<OwnerResponseDto> result = ownerService.getAllOwners();

        assertThat(result).isEmpty();
    }

    // TODO: Re-enable this test when Keycloak integration is complete
    // @Test
    // void shouldThrowOwnerExceptionWhenOwnerAlreadyExists() {
    //     OwnerRequestDto inputDto = new OwnerRequestDto("John", "Doe");
    //     // Mock keycloakUserId extraction from security context
    //     when(ownerRepository.existsByKeycloakUserId("keycloak-user-123")).thenReturn(true);
    //
    //     assertThatThrownBy(() -> ownerService.createOwner(inputDto))
    //             .isInstanceOf(OwnerException.class);
    //
    //     verify(ownerRepository).existsByKeycloakUserId("keycloak-user-123");
    //     verify(ownerRepository, never()).save(any());
    // }
}
