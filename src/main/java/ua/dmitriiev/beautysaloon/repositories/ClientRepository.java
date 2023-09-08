package ua.dmitriiev.beautysaloon.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.dmitriiev.beautysaloon.entities.Client;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ClientRepository extends JpaRepository<Client, UUID> {

    Optional<Client> findClientById(UUID id);

    void deleteClientById(UUID id);

    Optional<Client> findByClientNameEqualsIgnoreCase(String clientName);

    List<Client> findClientsByClientEmailEqualsIgnoreCase(String clientEmail);

    Client findClientByClientEmailEqualsIgnoreCase(String email);

    Client findClientByPhoneNumberEqualsIgnoreCase(String phoneNumber);

    List<Client> findClientsByClientNameEqualsIgnoreCase(String clientName);
}
