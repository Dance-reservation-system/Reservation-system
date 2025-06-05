package com.reservation.client;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.reservation.client.ClientResponseDto.mapToDto;

@Service
@RequiredArgsConstructor
class ClientService {

    private final ClientRepository clientRepository;

    @Transactional(readOnly = true)
    List<ClientResponseDto> getAllClients() {
        return clientRepository.findAll()
                .stream()
                .map(ClientResponseDto::mapToDto)
                .toList();
    }

    @Transactional(readOnly = true)
    ClientResponseDto getClientById(Long id) {
        return clientRepository.findById(id)
                .map(ClientResponseDto::mapToDto)
                .orElseThrow(() -> ClientException.clientNotFound(id));
    }

    @Transactional
    ClientResponseDto createClient(ClientRequestDto clientDto) {
        Client client = new Client(clientDto.name(), clientDto.email());
        return mapToDto(clientRepository.save(client));
    }

    @Transactional
    ClientResponseDto updateClient(Long id, ClientRequestDto clientDto) {
        Client clientToUpdate = clientRepository.findById(id)
                .orElseThrow(() -> ClientException.clientNotFound(id));

        clientToUpdate.setName(clientDto.name());
        clientToUpdate.setEmail(clientDto.email());

        return mapToDto(clientRepository.save(clientToUpdate));
    }

    @Transactional
    void deleteClient(Long id) {
        if (!clientRepository.existsById(id)) {
            throw ClientException.clientNotFound(id);
        }
        clientRepository.deleteById(id);
    }

}
