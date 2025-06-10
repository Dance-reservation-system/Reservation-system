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
        String userMail = clientDto.email();
        if (clientRepository.existsByEmail(userMail)){
            throw ClientException.clientAlreadyExist(userMail);
        }

        Client client = new Client(clientDto.name(), userMail);
        Client savedClient = clientRepository.save(client);
        return mapToDto(savedClient);
    }

    @Transactional
    ClientResponseDto updateClient(Long id, ClientRequestDto clientDto) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> ClientException.clientNotFound(id));

        Client updatedClient = new Client(
                client.getId(),
                clientDto.name(),
                clientDto.email(),
                client.getCreatedAt()
        );

        Client savedClient = clientRepository.save(updatedClient);
        return mapToDto(savedClient);
    }

    @Transactional
    void deleteClient(Long id) {
        if (!clientRepository.existsById(id)) {
            throw ClientException.clientNotFound(id);
        }
        clientRepository.deleteById(id);
    }

}
