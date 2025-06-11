package com.reservation.owner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.reservation.AbstractIT;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class OwnerControllerIT extends AbstractIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private OwnerRepository ownerRepository;

    private OwnerRequestDto testOwnerDto;

    @BeforeEach
    void setUp() {
        testOwnerDto = new OwnerRequestDto(
                "John",
                "Doe"
        );
    }

    @Test
    @SneakyThrows
    void shouldCreateOwner() {
        String requestBody = objectMapper.writeValueAsString(testOwnerDto);

        String responseContent = mockMvc.perform(post("/api/v1/owners")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value(testOwnerDto.firstName()))
                .andExpect(jsonPath("$.lastName").value(testOwnerDto.lastName()))
                .andExpect(jsonPath("$.createdAt").exists())
                .andReturn()
                .getResponse()
                .getContentAsString();

        OwnerResponseDto createdOwner = objectMapper.readValue(responseContent, OwnerResponseDto.class);

        assertThat(ownerRepository.findById(createdOwner.id()))
                .isPresent()
                .get()
                .satisfies(owner -> {
                    assertThat(owner.getFirstName()).isEqualTo(testOwnerDto.firstName());
                    assertThat(owner.getLastName()).isEqualTo(testOwnerDto.lastName());
                    // TODO: When Keycloak is integrated, verify keycloakUserId is populated from JWT claims
                    assertThat(owner.getKeycloakUserId()).isNull();
                });
    }

    @Test
    @SneakyThrows
    void shouldRetrieveOwnerById() {
        OwnerResponseDto createdOwner = createTestOwner();

        mockMvc.perform(get("/api/v1/owners/{id}", createdOwner.id()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value(createdOwner.firstName()))
                .andExpect(jsonPath("$.lastName").value(createdOwner.lastName()));
    }

    @Test
    @SneakyThrows
    void shouldReturnAllOwners() {
        OwnerResponseDto createdOwner = createTestOwner();

        mockMvc.perform(get("/api/v1/owners"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].firstName").value(createdOwner.firstName()))
                .andExpect(jsonPath("$[0].lastName").value(createdOwner.lastName()));
    }

    @Test
    @SneakyThrows
    void shouldReturnEmptyListWhenNoOwners() {
        mockMvc.perform(get("/api/v1/owners"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    @SneakyThrows
    void shouldReturnNotFoundForNonExistentOwner() {
        mockMvc.perform(get("/api/v1/owners/{id}", 999L))
                .andExpect(status().isNotFound());
    }

    @Test
    @SneakyThrows
    void shouldReturnBadRequestForInvalidInput() {
        OwnerRequestDto invalidOwnerDto = new OwnerRequestDto(
                "",
                "Doe"
        );

        mockMvc.perform(post("/api/v1/owners")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidOwnerDto)))
                .andExpect(status().isBadRequest());
    }

    // TODO: Re-enable this test when Keycloak integration is complete
    // @Test
    // @SneakyThrows
    // void shouldReturnConflictWhenKeycloakUserIdAlreadyExists() {
    //     createTestOwner();
    //
    //     OwnerRequestDto duplicateOwnerDto = new OwnerRequestDto(
    //             "Jane",
    //             "Smith"
    //     );
    //
    //     mockMvc.perform(post("/api/v1/owners")
    //                     .contentType(MediaType.APPLICATION_JSON)
    //                     .content(objectMapper.writeValueAsString(duplicateOwnerDto)))
    //             .andExpect(status().isConflict());
    // }

    private OwnerResponseDto createTestOwner() throws Exception {
        String createdOwner = mockMvc.perform(post("/api/v1/owners")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testOwnerDto)))
                .andReturn()
                .getResponse()
                .getContentAsString();

        return objectMapper.readValue(createdOwner, OwnerResponseDto.class);
    }
}
