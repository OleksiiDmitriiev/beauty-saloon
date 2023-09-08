package ua.dmitriiev.beautysaloon.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.dmitriiev.beautysaloon.entities.Client;
import ua.dmitriiev.beautysaloon.lib.exceptions.ClientListException;
import ua.dmitriiev.beautysaloon.lib.exceptions.NotFoundException;
import ua.dmitriiev.beautysaloon.lib.exceptions.NotUniqueEmailException;
import ua.dmitriiev.beautysaloon.lib.exceptions.NotUniquePhoneNumberException;
import ua.dmitriiev.beautysaloon.repositories.ClientRepository;
import ua.dmitriiev.beautysaloon.services.ClientService;

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

        log.debug("Searching for client with ID: {}", id);
        Optional<Client> foundClient = clientRepository.findClientById(id);
        log.debug("Searching for client with ID: {}", foundClient);
        return foundClient.orElseThrow(() -> new NotFoundException("Client not found with ID: " + id));

    }

    @Transactional
    @Override
    public Client saveClient(Client client) {

        if (client == null ||
                client.getClientName() == null ||
                client.getPhoneNumber() == null ||
                client.getClientEmail() == null) {
            log.error("Invalid client data provided: {}", client);
            throw new IllegalArgumentException("Invalid client data provided.");
        }

        Client existingClientByEmail = clientRepository.findClientByClientEmailEqualsIgnoreCase(client.getClientEmail());
        Client existingClientByPhoneNumber = clientRepository.findClientByPhoneNumberEqualsIgnoreCase(client.getPhoneNumber());

        if (existingClientByEmail != null) {
            log.error("Duplicate email found while saving client: {}", client.getClientEmail());
            throw new NotUniqueEmailException("Email already exists");
        }

        if (existingClientByPhoneNumber != null) {
            log.error("Duplicate phone number found while saving client: {}", client.getPhoneNumber());
            throw new NotUniquePhoneNumberException("Phone number already exists");
        }

        return clientRepository.save(client);
    }

    @Transactional
    @Override
    public void updateClient(UUID id, Client updatedClient) {

        if (updatedClient == null ||
                updatedClient.getClientName() == null ||
                updatedClient.getPhoneNumber() == null ||
                updatedClient.getClientEmail() == null) {
            log.error("Invalid client data provided: {}", updatedClient);
            throw new IllegalArgumentException("Invalid client data provided.");
        }

        Client clientToBeUpdated = clientRepository.findClientById(id)
                .orElseThrow(() -> new NotFoundException("Client not found"));


        Client existingClientByEmail = clientRepository.findClientByClientEmailEqualsIgnoreCase(updatedClient.getClientEmail());
        Client existingClientByPhoneNumber = clientRepository.findClientByPhoneNumberEqualsIgnoreCase(updatedClient.getPhoneNumber());

        if (existingClientByEmail != null && !existingClientByEmail.getId().equals(clientToBeUpdated.getId())) {
            log.error("Duplicate email found while updating client: {}", updatedClient.getClientEmail());
            throw new NotUniqueEmailException("Email already exists");
        }

        if (existingClientByPhoneNumber != null && !existingClientByPhoneNumber.getId().equals(clientToBeUpdated.getId())) {
            log.error("Duplicate phone number found while updating client: {}", updatedClient.getPhoneNumber());
            throw new NotUniquePhoneNumberException("Phone number already exists");
        }

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
        return foundClient.orElseThrow(() -> new NotFoundException("Client with name '" + clientName + "' not found."));
    }

    @Override
    public List<Client> findClientsByName(String clientName) {


        return clientRepository.findClientsByClientNameEqualsIgnoreCase(clientName);
    }

    @Override
    public Page<Client> listAllClients(int pageNumber, int pageSize) {
        try {
            Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.DESC, "updatedDate"));
            return clientRepository.findAll(pageable);
        } catch (Exception ex) {
            log.error("An error occurred while listing all clients:", ex);
            throw new ClientListException("Error occurred while listing clients. Please try again later.");
        }
    }


}
