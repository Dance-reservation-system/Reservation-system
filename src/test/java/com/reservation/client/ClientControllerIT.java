package com.reservation.client;

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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class ClientControllerIT extends AbstractIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ClientRepository clientRepository;

    private ClientRequestDto testClientDto;

    @BeforeEach
    void setUp() {
        testClientDto = new ClientRequestDto(
                "client-1",
                "client-1@example.com"
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
        ClientResponseDto createdClient = createTestClient();

        mockMvc.perform(get("/api/v1/clients/{id}", createdClient.id()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(createdClient.name()))
                .andExpect(jsonPath("$.email").value(createdClient.email()));
    }

    @Test
    @SneakyThrows
    void shouldUpdateClient() {
        ClientResponseDto existingClient = createTestClient();
        ClientRequestDto updateDto = new ClientRequestDto(
                "client-2",
                "client-2@example.com"
        );
        mockMvc.perform(put("/api/v1/clients/{id}", existingClient.id())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(updateDto.name()))
                .andExpect(jsonPath("$.email").value(updateDto.email()));

        assertThat(clientRepository.findById(existingClient.id()))
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
        ClientResponseDto createdClient = createTestClient();

        mockMvc.perform(delete("/api/v1/clients/{id}", createdClient.id()))
                .andExpect(status().isNoContent());

        assertThat(clientRepository.findById(createdClient.id())).isEmpty();
    }

    @Test
    @SneakyThrows
    void shouldReturnNotFoundForNonExistentClient() {
        mockMvc.perform(get("/api/v1/clients/{id}", 999L))
                .andExpect(status().isNotFound());
    }

    private ClientResponseDto createTestClient() throws Exception {
        String createdClient = mockMvc.perform(post("/api/v1/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testClientDto)))
                .andReturn()
                .getResponse()
                .getContentAsString();

        return objectMapper.readValue(createdClient, ClientResponseDto.class);
    }
}