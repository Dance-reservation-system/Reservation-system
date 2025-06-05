package com.reservation.client;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.reservation.client.ClientTestFactory.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClientServiceTest {

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ClientService clientService;

    private static final ClientResponseDto clientResponse = new ClientResponseDto(
            ID, "client-1", "client-1@example.com", FIXED_INSTANT);
    private final Client client = expectedTestClient();

    @Test
    void shouldReturnListOfAllClients() {
        when(clientRepository.findAll()).thenReturn(List.of(client));
        List<ClientResponseDto> result = clientService.getAllClients();

        assertThat(result)
                .hasSize(1)
                .first()
                .usingRecursiveComparison()
                .isEqualTo(clientResponse);
    }

    @Test
    void shouldReturnEmptyListWhenNoClientsExist() {
        when(clientRepository.findAll()).thenReturn(List.of());
        List<ClientResponseDto> result = clientService.getAllClients();

        assertThat(result).isEmpty();
    }


    @Test
    void shouldReturnClientWhenExists() {
        when(clientRepository.findById(ID)).thenReturn(Optional.of(client));
        ClientResponseDto result = clientService.getClientById(ID);

        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(clientResponse);
    }

    @Test
    void shouldThrowEntityNotFoundExceptionWhenClientNotFound() {
        when(clientRepository.findById(ID)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> clientService.getClientById(ID))
                .isInstanceOf(ClientException.class)
                .hasMessage("Client with id: " + ID + " not found");
    }

    @Test
    void shouldCreateNewClientSuccessfully() {
        // given
        ClientRequestDto inputDto = new ClientRequestDto("client-1", "client-1@example.com");
        when(clientRepository.save(any(Client.class))).thenReturn(client);

        ClientResponseDto result = clientService.createClient(inputDto);

        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(clientResponse);

        verify(clientRepository).save(any(Client.class));
    }


    @Test
    void shouldUpdateExistingClientSuccessfully() {
        ClientRequestDto updateDto = new ClientRequestDto("updated-client-1", "updated-client-1@example.com");
        Client updatedClient = Client.builder()
                .name("updated-client-1")
                .email("updated-client-1@example.com")
                .build();
        updatedClient.setId(ID);
        updatedClient.setCreatedAt(FIXED_INSTANT);

        when(clientRepository.findById(ID)).thenReturn(Optional.of(client));
        when(clientRepository.save(any(Client.class))).thenReturn(updatedClient);

        ClientResponseDto result = clientService.updateClient(ID, updateDto);

        assertThat(result.name()).isEqualTo("updated-client-1");
        assertThat(result.email()).isEqualTo("updated-client-1@example.com");
        assertThat(result.createdAt()).isEqualTo(FIXED_INSTANT);
    }

    @Test
    void shouldThrowEntityNotFoundExceptionWhenUpdatingNonExistingClient() {
        when(clientRepository.findById(ID)).thenReturn(Optional.empty());

        ClientRequestDto clientRequest = new ClientRequestDto("name", "name@example.com");

        assertThatThrownBy(() -> clientService.updateClient(ID, clientRequest))
                .isInstanceOf(ClientException.class)
                .hasMessage("Client with id: " + ID + " not found");

        verify(clientRepository, never()).save(any());
    }


    @Test
    void shouldDeleteExistingClientSuccessfully() {
        when(clientRepository.existsById(ID)).thenReturn(true);

        clientService.deleteClient(ID);

        verify(clientRepository).deleteById(ID);
    }

    @Test
    void shouldThrowEntityNotFoundExceptionWhenDeletingNonExistingClient() {
        when(clientRepository.existsById(ID)).thenReturn(false);

        assertThatThrownBy(() -> clientService.deleteClient(ID))
                .isInstanceOf(ClientException.class)
                .hasMessage("Client with id: " + ID + " not found");

        verify(clientRepository, never()).deleteById(any());
    }

}