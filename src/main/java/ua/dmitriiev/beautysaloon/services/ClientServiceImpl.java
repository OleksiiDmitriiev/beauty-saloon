package ua.dmitriiev.beautysaloon.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import ua.dmitriiev.beautysaloon.entities.Client;
import ua.dmitriiev.beautysaloon.entities.Master;
import ua.dmitriiev.beautysaloon.lib.exceptions.NotFoundException;
import ua.dmitriiev.beautysaloon.lib.exceptions.NotUniqueValue;
import ua.dmitriiev.beautysaloon.repositories.ClientRepository;

import java.util.List;
import java.util.NoSuchElementException;
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

        try {
            return clientRepository.save(client);
        } catch (DataIntegrityViolationException ex) {
            String message = "Client with the same email or phone number already exists.";
            log.error(message, ex);
            throw new NotUniqueValue(message, ex);
        } catch (Exception ex) {
            log.error("Error while saving client.", ex);
            throw new RuntimeException("An error occurred while saving the client.");
        }
//        return clientRepository.save(client);

    }

    @Transactional
    @Override
    public void updateClient(UUID id, Client updatedClient) {

        Client clientToBeUpdated = clientRepository.findClientById(id)
                .orElseThrow(() -> new NotFoundException("Client not found"));


        clientToBeUpdated.setId(updatedClient.getId());
        clientToBeUpdated.setClientName(updatedClient.getClientName());
        clientToBeUpdated.setPhoneNumber(updatedClient.getPhoneNumber());
        clientToBeUpdated.setOrders(updatedClient.getOrders());

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
