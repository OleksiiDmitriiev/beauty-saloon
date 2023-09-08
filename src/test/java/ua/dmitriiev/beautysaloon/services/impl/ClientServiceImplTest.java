package ua.dmitriiev.beautysaloon.services.impl;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.data.domain.*;
import ua.dmitriiev.beautysaloon.entities.Client;

import ua.dmitriiev.beautysaloon.lib.exceptions.NotFoundException;
import ua.dmitriiev.beautysaloon.lib.exceptions.NotUniqueEmailException;
import ua.dmitriiev.beautysaloon.lib.exceptions.NotUniquePhoneNumberException;
import ua.dmitriiev.beautysaloon.repositories.ClientRepository;


import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import lombok.extern.slf4j.Slf4j;


@Slf4j
class ClientServiceImplTest {


    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ClientServiceImpl clientService;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

    }


    @Test
    public void testListClients() {

        Client client1 = new Client();
        client1.setId(UUID.randomUUID());
        client1.setClientName("Client 1");
        Client client2 = new Client();
        client2.setId(UUID.randomUUID());
        client2.setClientName("Client 2");

        List<Client> clients = Arrays.asList(client1, client2);


        when(clientRepository.findAll()).thenReturn(clients);


        List<Client> returnedClients = clientService.listClients();


        verify(clientRepository, times(1)).findAll();


        assertEquals(2, returnedClients.size());
        assertEquals(client1.getId(), returnedClients.get(0).getId());
        assertEquals(client2.getId(), returnedClients.get(1).getId());
    }


    @Test
    public void testFindClientById() {

        UUID clientId = UUID.randomUUID();


        Client client = new Client();
        client.setId(clientId);
        client.setClientName("Test Client");


        when(clientRepository.findClientById(clientId)).thenReturn(Optional.of(client));


        Client returnedClient = clientService.findClientById(clientId);


        verify(clientRepository, times(1)).findClientById(clientId);
        assertNotNull(returnedClient);
        assertEquals(client.getId(), returnedClient.getId());
        assertEquals(client.getClientName(), returnedClient.getClientName());
    }

    @Test
    public void testFindClientByIdNotFound() {

        UUID clientId = UUID.randomUUID();


        when(clientRepository.findClientById(clientId)).thenReturn(Optional.empty());


        assertThrows(NotFoundException.class, () -> clientService.findClientById(clientId));


        verify(clientRepository, times(1)).findClientById(clientId);
    }


    @Test
    public void testSaveClientInvalidDataNullName() {

        Client client = new Client();
        client.setPhoneNumber("1234567890");
        client.setClientEmail("test@example.com");


        assertThrows(IllegalArgumentException.class, () -> clientService.saveClient(client));


        verify(clientRepository, never()).findClientByClientEmailEqualsIgnoreCase(client.getClientEmail());
        verify(clientRepository, never()).findClientByPhoneNumberEqualsIgnoreCase(client.getPhoneNumber());
        verify(clientRepository, never()).save(client);
    }

    @Test
    public void testSaveClientInvalidDataNullPhoneNumber() {

        Client client = new Client();
        client.setClientName("Test Client");
        client.setClientEmail("test@example.com");


        assertThrows(IllegalArgumentException.class, () -> clientService.saveClient(client));


        verify(clientRepository, never()).findClientByClientEmailEqualsIgnoreCase(client.getClientEmail());
        verify(clientRepository, never()).findClientByPhoneNumberEqualsIgnoreCase(client.getPhoneNumber());
        verify(clientRepository, never()).save(client);
    }

    @Test
    public void testSaveClientInvalidDataNullEmail() {

        Client client = new Client();
        client.setClientName("Test Client");
        client.setPhoneNumber("1234567890");


        assertThrows(IllegalArgumentException.class, () -> clientService.saveClient(client));


        verify(clientRepository, never()).findClientByClientEmailEqualsIgnoreCase(client.getClientEmail());
        verify(clientRepository, never()).findClientByPhoneNumberEqualsIgnoreCase(client.getPhoneNumber());
        verify(clientRepository, never()).save(client);
    }

    @Test
    public void testSaveClient() {

        Client client = new Client();
        client.setClientName("Test Client");
        client.setPhoneNumber("1234567890");
        client.setClientEmail("test@example.com");


        when(clientRepository.findClientByClientEmailEqualsIgnoreCase(client.getClientEmail())).thenReturn(null);
        when(clientRepository.findClientByPhoneNumberEqualsIgnoreCase(client.getPhoneNumber())).thenReturn(null);
        when(clientRepository.save(client)).thenReturn(client);


        Client savedClient = clientService.saveClient(client);


        verify(clientRepository, times(1)).findClientByClientEmailEqualsIgnoreCase(client.getClientEmail());
        verify(clientRepository, times(1)).findClientByPhoneNumberEqualsIgnoreCase(client.getPhoneNumber());
        verify(clientRepository, times(1)).save(client);
        assertNotNull(savedClient);
        assertEquals(client, savedClient);
    }

    @Test
    public void testSaveClient_DuplicateEmail_ThrowsNotUniqueEmailException() {
        Client duplicateEmailClient = new Client();
        duplicateEmailClient.setClientName("Jane Doe");
        duplicateEmailClient.setPhoneNumber("9876543210");
        duplicateEmailClient.setClientEmail("jane.doe@example.com");

        when(clientRepository.findClientByClientEmailEqualsIgnoreCase(duplicateEmailClient.getClientEmail()))
                .thenReturn(duplicateEmailClient);

        assertThrows(NotUniqueEmailException.class, () -> clientService.saveClient(duplicateEmailClient));
    }

    @Test
    public void testSaveClientDuplicatePhoneNumber() {

        Client client = new Client();
        client.setClientName("Test Client");
        client.setPhoneNumber("1234567890");
        client.setClientEmail("test@example.com");


        when(clientRepository.findClientByClientEmailEqualsIgnoreCase(client.getClientEmail())).thenReturn(null);
        when(clientRepository.findClientByPhoneNumberEqualsIgnoreCase(client.getPhoneNumber())).thenReturn(client);


        assertThrows(NotUniquePhoneNumberException.class, () -> clientService.saveClient(client));


        verify(clientRepository, times(1)).findClientByClientEmailEqualsIgnoreCase(client.getClientEmail());
        verify(clientRepository, times(1)).findClientByPhoneNumberEqualsIgnoreCase(client.getPhoneNumber());
        verify(clientRepository, never()).save(client);
    }


    @Test
    public void testUpdateClient() {

        UUID clientId = UUID.randomUUID();
        Client updatedClient = new Client();
        updatedClient.setId(clientId);
        updatedClient.setClientName("Updated Client");
        updatedClient.setPhoneNumber("9876543210");
        updatedClient.setClientEmail("updated@example.com");


        Client existingClient = new Client();
        existingClient.setId(clientId);
        existingClient.setClientName("Existing Client");
        existingClient.setPhoneNumber("1234567890");
        existingClient.setClientEmail("existing@example.com");


        when(clientRepository.findClientById(clientId)).thenReturn(Optional.of(existingClient));
        when(clientRepository.findClientByClientEmailEqualsIgnoreCase(updatedClient.getClientEmail())).thenReturn(null);
        when(clientRepository.findClientByPhoneNumberEqualsIgnoreCase(updatedClient.getPhoneNumber())).thenReturn(null);


        clientService.updateClient(clientId, updatedClient);


        verify(clientRepository, times(1)).findClientById(clientId);
        verify(clientRepository, times(1)).findClientByClientEmailEqualsIgnoreCase(updatedClient.getClientEmail());
        verify(clientRepository, times(1)).findClientByPhoneNumberEqualsIgnoreCase(updatedClient.getPhoneNumber());
        verify(clientRepository, times(1)).save(existingClient);


        assertEquals(updatedClient.getClientName(), existingClient.getClientName());
        assertEquals(updatedClient.getPhoneNumber(), existingClient.getPhoneNumber());
        assertEquals(updatedClient.getClientEmail(), existingClient.getClientEmail());

    }

    @Test
    public void testUpdateClientNotFound() {

        UUID clientId = UUID.randomUUID();
        Client updatedClient = new Client();
        updatedClient.setId(clientId);
        updatedClient.setClientName("Updated Client");


        when(clientRepository.findClientById(clientId)).thenReturn(Optional.empty());


        assertThrows(NotFoundException.class, () -> clientService.updateClient(clientId, updatedClient));


        verify(clientRepository, times(1)).findClientById(clientId);
        verify(clientRepository, never()).findClientByClientEmailEqualsIgnoreCase(any());
        verify(clientRepository, never()).findClientByPhoneNumberEqualsIgnoreCase(any());
        verify(clientRepository, never()).save(any());
    }

    @Test
    public void testUpdateClient_DuplicateEmail_ThrowsNotUniqueEmailException() {
        UUID existingId = UUID.randomUUID();
        Client existingClient = new Client();
        existingClient.setId(existingId);
        existingClient.setClientEmail("client@gmail.com");

        Client updatedClient = new Client();
        updatedClient.setId(existingId);
        updatedClient.setClientName("Updated Name");
        updatedClient.setClientEmail("client@gmail.com");

        when(clientRepository.findClientById(existingId)).thenReturn(Optional.of(existingClient));
        when(clientRepository.findClientByClientEmailEqualsIgnoreCase(updatedClient.getClientEmail()))
                .thenThrow(NotUniqueEmailException.class);

        assertThrows(NotUniqueEmailException.class, () -> clientService.updateClient(existingId, updatedClient));
    }

    @Test
    public void testUpdateClient_DuplicatePhoneNumber_ThrowsNotUniquePhoneNumberException() {

        UUID existingId = UUID.randomUUID();
        Client existingClient = new Client();
        existingClient.setId(existingId);
        existingClient.setPhoneNumber("+380502133698");

        Client updatedClient = new Client();
        updatedClient.setId(existingId);
        updatedClient.setClientName("Updated Name");
        updatedClient.setPhoneNumber("+380502133698");

        when(clientRepository.findClientById(existingId)).thenReturn(Optional.of(existingClient));
        when(clientRepository.findClientByPhoneNumberEqualsIgnoreCase(updatedClient.getPhoneNumber()))
                .thenThrow(NotUniquePhoneNumberException.class);

        assertThrows(NotUniquePhoneNumberException.class, () -> clientService.updateClient(existingId, updatedClient));

    }


    @Test
    public void testDeleteById_DeletesClient() {

        UUID clientId = UUID.randomUUID();
        Client client = new Client();
        client.setId(clientId);


        when(clientRepository.findClientById(clientId)).thenReturn(Optional.of(client));


        clientService.deleteById(clientId);


        verify(clientRepository, times(1)).deleteClientById(clientId);
    }


    @Test
    public void testFindClientByName_ExistingName_ReturnsClient() {


        UUID existingId = UUID.randomUUID();
        String existingClientName = "Anton Client";

        System.out.println("Creating random UUID: " + existingId);

        Client existingClient = new Client();

        existingClient.setClientName(existingClientName);
        System.out.println("Setting existing client's name: " + existingClient.getClientName());

        existingClient.setId(existingId);
        System.out.println("Setting existing client's id: " + existingClient.getId());

        when(clientRepository.findByClientNameEqualsIgnoreCase(existingClientName)).thenReturn(Optional.of(existingClient));

        Client result = clientService.findClientByName(existingClientName);

        System.out.println("Checking found client's ID:" + result.getId());
        System.out.println("Checking found client's name: " + result.getClientName());

        assertNotNull(result);
        assertEquals(existingClientName, result.getClientName());
    }


    @Test
    public void testFindClientByName_NonExistingName_ThrowsNotFoundException() {

        String nonExistingName = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        System.out.println(nonExistingName);

        when(clientRepository.findByClientNameEqualsIgnoreCase(nonExistingName)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> clientService.findClientByName(nonExistingName));

    }


    @Test
    public void testFindClientsByName_ReturnsListOfClients() {

        String testClientName = "Test Client";


        Client client1 = new Client();
        client1.setClientName(testClientName);

        Client client2 = new Client();
        client2.setClientName(testClientName);


        List<Client> expectedClients = Arrays.asList(client1, client2);


        when(clientRepository.findClientsByClientNameEqualsIgnoreCase(testClientName)).thenReturn(expectedClients);


        List<Client> actualClients = clientService.findClientsByName(testClientName);


        verify(clientRepository, times(1)).findClientsByClientNameEqualsIgnoreCase(testClientName);


        assertEquals(expectedClients, actualClients);
    }


    @Test
    public void testListAllClients_ReturnsPageOfClients() {

        int pageNumber = 0;
        int pageSize = 5;

        List<Client> expectedClients = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            expectedClients.add(new Client());
        }


        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.DESC, "updatedDate"));
        when(clientRepository.findAll(pageable)).thenReturn(new PageImpl<>(expectedClients, pageable, expectedClients.size()));


        Page<Client> actualPage = clientService.listAllClients(pageNumber, pageSize);


        verify(clientRepository, times(1)).findAll(pageable);


        assertEquals(expectedClients.size(), actualPage.getTotalElements());
        assertEquals(expectedClients, actualPage.getContent());
    }


}