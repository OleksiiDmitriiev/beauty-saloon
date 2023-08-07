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
//@ExtendWith(MockitoExtension.class)
//@SpringBootTest
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
        // Create some test data
        Client client1 = new Client();
        client1.setId(UUID.randomUUID());
        client1.setClientName("Client 1");
        Client client2 = new Client();
        client2.setId(UUID.randomUUID());
        client2.setClientName("Client 2");

        List<Client> clients = Arrays.asList(client1, client2);

        // Mocking behavior of clientRepository
        when(clientRepository.findAll()).thenReturn(clients);

        // Call the method to be tested
        List<Client> returnedClients = clientService.listClients();

        // Verify the result
        verify(clientRepository, times(1)).findAll();

        // Add assertions to verify the correctness of the returnedClients list
        // For example:
        assertEquals(2, returnedClients.size());
        assertEquals(client1.getId(), returnedClients.get(0).getId());
        assertEquals(client2.getId(), returnedClients.get(1).getId());
    }


    @Test
    public void testFindClientById() {
        // Create a test UUID
        UUID clientId = UUID.randomUUID();

        // Create a mock Client object
        Client client = new Client();
        client.setId(clientId);
        client.setClientName("Test Client");

        // Mocking behavior of clientRepository
        when(clientRepository.findClientById(clientId)).thenReturn(Optional.of(client));

        // Call the method to be tested
        Client returnedClient = clientService.findClientById(clientId);

        // Verify the result
        verify(clientRepository, times(1)).findClientById(clientId);
        assertNotNull(returnedClient);
        assertEquals(client.getId(), returnedClient.getId());
        assertEquals(client.getClientName(), returnedClient.getClientName());
    }

    @Test
    public void testFindClientByIdNotFound() {
        // Create a test UUID
        UUID clientId = UUID.randomUUID();

        // Mocking behavior of clientRepository to return an empty Optional
        when(clientRepository.findClientById(clientId)).thenReturn(Optional.empty());

        // Call the method to be tested and expect an exception
        assertThrows(NotFoundException.class, () -> clientService.findClientById(clientId));

        // Verify the result
        verify(clientRepository, times(1)).findClientById(clientId);
    }


    @Test
    public void testSaveClientInvalidDataNullName() {
        // Create a test Client object with null name
        Client client = new Client();
        client.setPhoneNumber("1234567890");
        client.setClientEmail("test@example.com");

        // Call the method to be tested and expect an IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> clientService.saveClient(client));

        // Verify the result
        verify(clientRepository, never()).findClientByClientEmailEqualsIgnoreCase(client.getClientEmail());
        verify(clientRepository, never()).findClientByPhoneNumberEqualsIgnoreCase(client.getPhoneNumber());
        verify(clientRepository, never()).save(client);
    }

    @Test
    public void testSaveClientInvalidDataNullPhoneNumber() {
        // Create a test Client object with null phone number
        Client client = new Client();
        client.setClientName("Test Client");
        client.setClientEmail("test@example.com");

        // Call the method to be tested and expect an IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> clientService.saveClient(client));

        // Verify the result
        verify(clientRepository, never()).findClientByClientEmailEqualsIgnoreCase(client.getClientEmail());
        verify(clientRepository, never()).findClientByPhoneNumberEqualsIgnoreCase(client.getPhoneNumber());
        verify(clientRepository, never()).save(client);
    }

    @Test
    public void testSaveClientInvalidDataNullEmail() {
        // Create a test Client object with null email
        Client client = new Client();
        client.setClientName("Test Client");
        client.setPhoneNumber("1234567890");

        // Call the method to be tested and expect an IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> clientService.saveClient(client));

        // Verify the result
        verify(clientRepository, never()).findClientByClientEmailEqualsIgnoreCase(client.getClientEmail());
        verify(clientRepository, never()).findClientByPhoneNumberEqualsIgnoreCase(client.getPhoneNumber());
        verify(clientRepository, never()).save(client);
    }

    @Test
    public void testSaveClient() {
        // Create a test Client object
        Client client = new Client();
        client.setClientName("Test Client");
        client.setPhoneNumber("1234567890");
        client.setClientEmail("test@example.com");

        // Mocking behavior of clientRepository
        when(clientRepository.findClientByClientEmailEqualsIgnoreCase(client.getClientEmail())).thenReturn(null);
        when(clientRepository.findClientByPhoneNumberEqualsIgnoreCase(client.getPhoneNumber())).thenReturn(null);
        when(clientRepository.save(client)).thenReturn(client);

        // Call the method to be tested
        Client savedClient = clientService.saveClient(client);

        // Verify the result
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
        // Create a test Client object with a duplicate phone number
        Client client = new Client();
        client.setClientName("Test Client");
        client.setPhoneNumber("1234567890");
        client.setClientEmail("test@example.com");

        // Mocking behavior of clientRepository to return an existing client by phone number
        when(clientRepository.findClientByClientEmailEqualsIgnoreCase(client.getClientEmail())).thenReturn(null);
        when(clientRepository.findClientByPhoneNumberEqualsIgnoreCase(client.getPhoneNumber())).thenReturn(client);

        // Call the method to be tested and expect a NotUniquePhoneNumberException
        assertThrows(NotUniquePhoneNumberException.class, () -> clientService.saveClient(client));

        // Verify the result
        verify(clientRepository, times(1)).findClientByClientEmailEqualsIgnoreCase(client.getClientEmail());
        verify(clientRepository, times(1)).findClientByPhoneNumberEqualsIgnoreCase(client.getPhoneNumber());
        verify(clientRepository, never()).save(client);
    }


    @Test
    public void testUpdateClient() {
        // Create a test UUID and a mock updated client
        UUID clientId = UUID.randomUUID();
        Client updatedClient = new Client();
        updatedClient.setId(clientId);
        updatedClient.setClientName("Updated Client");
        updatedClient.setPhoneNumber("9876543210");
        updatedClient.setClientEmail("updated@example.com");

        // Create a mock existing client to be updated
        Client existingClient = new Client();
        existingClient.setId(clientId);
        existingClient.setClientName("Existing Client");
        existingClient.setPhoneNumber("1234567890");
        existingClient.setClientEmail("existing@example.com");

        // Mocking behavior of clientRepository
        when(clientRepository.findClientById(clientId)).thenReturn(Optional.of(existingClient));
        when(clientRepository.findClientByClientEmailEqualsIgnoreCase(updatedClient.getClientEmail())).thenReturn(null);
        when(clientRepository.findClientByPhoneNumberEqualsIgnoreCase(updatedClient.getPhoneNumber())).thenReturn(null);

        // Call the method to be tested
        clientService.updateClient(clientId, updatedClient);

        // Verify the result
        verify(clientRepository, times(1)).findClientById(clientId);
        verify(clientRepository, times(1)).findClientByClientEmailEqualsIgnoreCase(updatedClient.getClientEmail());
        verify(clientRepository, times(1)).findClientByPhoneNumberEqualsIgnoreCase(updatedClient.getPhoneNumber());
        verify(clientRepository, times(1)).save(existingClient);

        // Add assertions to verify that the existing client has been updated correctly
        assertEquals(updatedClient.getClientName(), existingClient.getClientName());
        assertEquals(updatedClient.getPhoneNumber(), existingClient.getPhoneNumber());
        assertEquals(updatedClient.getClientEmail(), existingClient.getClientEmail());
        // ... add more assertions based on your test case
    }

    @Test
    public void testUpdateClientNotFound() {
        // Create a test UUID and a mock updated client
        UUID clientId = UUID.randomUUID();
        Client updatedClient = new Client();
        updatedClient.setId(clientId);
        updatedClient.setClientName("Updated Client");

        // Mocking behavior of clientRepository to return an empty Optional
        when(clientRepository.findClientById(clientId)).thenReturn(Optional.empty());

        // Call the method to be tested and expect a NotFoundException
        assertThrows(NotFoundException.class, () -> clientService.updateClient(clientId, updatedClient));

        // Verify the result
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
        // Create a new Master instance with a specific ID
        UUID clientId = UUID.randomUUID();
        Client client = new Client();
        client.setId(clientId);

        // Mock the behavior of masterRepository
        when(clientRepository.findClientById(clientId)).thenReturn(Optional.of(client));

        // Call deleteById method
        clientService.deleteById(clientId);

        // Verify that the deleteMasterById method of masterRepository was called with the correct ID
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
        // Create a test master name
        String testClientName = "Test Client";

        // Create a list of Master instances for the test
        Client client1 = new Client();
        client1.setClientName(testClientName);

        Client client2 = new Client();
        client2.setClientName(testClientName);


        List<Client> expectedClients = Arrays.asList(client1, client2);

        // Mock the behavior of masterRepository
        when(clientRepository.findClientsByClientNameEqualsIgnoreCase(testClientName)).thenReturn(expectedClients);

        // Call findMastersByName method
        List<Client> actualClients = clientService.findClientsByName(testClientName);

        // Verify that the masterRepository method was called with the correct masterName
        verify(clientRepository, times(1)).findClientsByClientNameEqualsIgnoreCase(testClientName);

        // Verify that the returned list matches the expected list
        assertEquals(expectedClients, actualClients);
    }


    @Test
    public void testListAllClients_ReturnsPageOfClients() {
        // Create test data
        int pageNumber = 0;
        int pageSize = 5;

        List<Client> expectedClients = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            expectedClients.add(new Client());
        }

        // Mock the behavior of masterRepository
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.DESC, "updatedDate"));
        when(clientRepository.findAll(pageable)).thenReturn(new PageImpl<>(expectedClients, pageable, expectedClients.size()));

        // Call listAllMasters method
        Page<Client> actualPage = clientService.listAllClients(pageNumber, pageSize);

        // Verify that the masterRepository method was called with the correct pageable
        verify(clientRepository, times(1)).findAll(pageable);

        // Verify the content of the returned Page
        assertEquals(expectedClients.size(), actualPage.getTotalElements());
        assertEquals(expectedClients, actualPage.getContent());
    }


}