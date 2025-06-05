package com.reservation.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.reservation.AbstractIT;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ClientControllerIT extends AbstractIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ClientRepository clientRepository;

    private final long expectedClientId = 1L;
    private Client expectedClient;
    private ClientRequestDto testClientDto;

    @BeforeEach
    void setUp() {

        expectedClient = Client.builder()
                .name("client-1")
                .email("client-1@example.com")
                .build();

        testClientDto = new ClientRequestDto(
                "client-2",
                "client-2@example.com"
        );
    }

    @Test
    @SneakyThrows
    void shouldCreateClient() {
        String requestBody = objectMapper.writeValueAsString(testClientDto);

        String responseContent = mockMvc.perform(post("/api/v1/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(testClientDto.name()))
                .andExpect(jsonPath("$.email").value(testClientDto.email()))
                .andExpect(jsonPath("$.createdAt").exists())
                .andReturn()
                .getResponse()
                .getContentAsString();

        ClientResponseDto createdClient = objectMapper.readValue(responseContent, ClientResponseDto.class);

        assertThat(clientRepository.findById(createdClient.id()))
                .isPresent()
                .get()
                .satisfies(client -> {
                    assertThat(client.getName()).isEqualTo(testClientDto.name());
                    assertThat(client.getEmail()).isEqualTo(testClientDto.email());
                });
    }

    @Test
    @SneakyThrows
    void shouldRetrieveClientById() {
        mockMvc.perform(get("/api/v1/clients/{id}", expectedClientId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(expectedClient.getName()))
                .andExpect(jsonPath("$.email").value(expectedClient.getEmail()));
    }

    @Test
    @SneakyThrows
    void shouldUpdateClient() {
        ClientRequestDto updateDto = new ClientRequestDto(
                "client-2",
                "client-2@example.com"
        );
        mockMvc.perform(put("/api/v1/clients/{id}", expectedClientId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(updateDto.name()))
                .andExpect(jsonPath("$.email").value(updateDto.email()));

        assertThat(clientRepository.findById(expectedClientId))
                .isPresent()
                .get()
                .satisfies(client -> {
                    assertThat(client.getName()).isEqualTo(updateDto.name());
                    assertThat(client.getEmail()).isEqualTo(updateDto.email());
                });
    }

    @Test
    @SneakyThrows
    void shouldDeleteClient() {
        mockMvc.perform(delete("/api/v1/clients/{id}", expectedClientId))
                .andExpect(status().isNoContent());

        assertThat(clientRepository.findById(expectedClientId)).isEmpty();
    }

    @Test
    @SneakyThrows
    void shouldReturnNotFoundForNonExistentClient() {
        mockMvc.perform(get("/api/v1/clients/{id}", 999L))
                .andExpect(status().isNotFound());
    }
}