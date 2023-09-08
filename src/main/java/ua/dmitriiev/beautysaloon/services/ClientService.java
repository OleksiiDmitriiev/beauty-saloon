package ua.dmitriiev.beautysaloon.services;

import org.springframework.data.domain.Page;
import ua.dmitriiev.beautysaloon.entities.Client;


import java.util.List;
import java.util.UUID;

public interface ClientService {

    List<Client> listClients();

    Client findClientById(UUID id);


    Client saveClient(Client client);


    void updateClient(UUID id, Client updatedClient);

    void deleteById(UUID id);


    Client findClientByName(String clientName);

    List<Client> findClientsByName(String clientName);

    Page<Client> listAllClients(int pageNumber, int pageSize);
}
