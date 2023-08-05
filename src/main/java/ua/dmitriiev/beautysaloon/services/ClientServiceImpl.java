package ua.dmitriiev.beautysaloon.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.dmitriiev.beautysaloon.entities.Client;
import ua.dmitriiev.beautysaloon.lib.exceptions.NotFoundException;
import ua.dmitriiev.beautysaloon.lib.exceptions.NotUniqueEmailException;
import ua.dmitriiev.beautysaloon.lib.exceptions.NotUniquePhoneNumberException;
import ua.dmitriiev.beautysaloon.repositories.ClientRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@Transactional(readOnly = true)
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    @Autowired
    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public List<Client> listClients() {

        return clientRepository.findAll();
    }

    @Override
    public Client findClientById(UUID id) {

        try {
            Optional<Client> foundClient = clientRepository.findClientById(id);
            return foundClient.orElseThrow(() -> new NotFoundException("Client not found"));
        } catch (NotFoundException exception) {
            log.error("Client not found with ID: {}", id, exception);
            throw exception;
        }

    }

    @Transactional
    @Override
    public Client saveClient(Client client) {
        // Check for duplicate email or phoneNumber before saving
        Client existingClientByEmail = clientRepository.findClientByClientEmailEqualsIgnoreCase(client.getClientEmail());
        Client existingClientByPhoneNumber = clientRepository.findClientByPhoneNumberEqualsIgnoreCase(client.getPhoneNumber());

        if (existingClientByEmail != null) {
            throw new NotUniqueEmailException("Email already exists");
        }

        if (existingClientByPhoneNumber != null) {
            throw new NotUniquePhoneNumberException("Phone number already exists");
        }

        return clientRepository.save(client);
    }

    @Transactional
    @Override
    public void updateClient(UUID id, Client updatedClient) {

        Client clientToBeUpdated = clientRepository.findClientById(id)
                .orElseThrow(() -> new NotFoundException("Client not found"));

        // Check for duplicate email and phoneNumber
        Client existingClientByEmail = clientRepository.findClientByClientEmailEqualsIgnoreCase(updatedClient.getClientEmail());
        Client existingClientByPhoneNumber = clientRepository.findClientByPhoneNumberEqualsIgnoreCase(updatedClient.getPhoneNumber());

        if (existingClientByEmail != null && !existingClientByEmail.getId().equals(clientToBeUpdated.getId())) {
            throw new NotUniqueEmailException("Email already exists");
        }

        if (existingClientByPhoneNumber != null && !existingClientByPhoneNumber.getId().equals(clientToBeUpdated.getId())) {
            throw new NotUniquePhoneNumberException("Phone number already exists");
        }

        // Update the client information
        clientToBeUpdated.setClientName(updatedClient.getClientName());
        clientToBeUpdated.setOrders(updatedClient.getOrders());
        clientToBeUpdated.setClientEmail(updatedClient.getClientEmail());
        clientToBeUpdated.setPhoneNumber(updatedClient.getPhoneNumber());

        clientRepository.save(clientToBeUpdated);
    }


    @Transactional
    @Override
    public void deleteById(UUID id) {

        clientRepository.deleteClientById(id);
    }

    @Override
    public Client findClientByName(String clientName) {
        Optional<Client> foundClient = clientRepository.findByClientNameEqualsIgnoreCase(clientName);
        return foundClient.orElse(null);
    }

    @Override
    public List<Client> findClientsByEmail(String clientEmail) {

        return clientRepository.findClientsByClientEmailEqualsIgnoreCase(clientEmail);
    }

    @Override
    public Page<Client> listAllClients(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.DESC, "updatedDate"));
        return clientRepository.findAll(pageable);
    }


}
