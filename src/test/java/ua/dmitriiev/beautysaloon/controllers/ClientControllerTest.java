package ua.dmitriiev.beautysaloon.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;
import ua.dmitriiev.beautysaloon.entities.Client;
import ua.dmitriiev.beautysaloon.services.impl.ClientServiceImpl;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClientControllerTest {

    @Mock
    private ClientServiceImpl clientService;

    @InjectMocks
    private ClientController clientController;

    @Mock
    private Model model;

    @Mock
    private BindingResult bindingResult;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void testListAllClients() {
        int pageNumber = 0;
        int pageSize = 5;

        // Create a mock Page<Client>
        Page<Client> mockPage = mock(Page.class);
        when(mockPage.getContent()).thenReturn(Collections.emptyList());
        when(mockPage.getTotalPages()).thenReturn(2); // Adjust the total pages as needed

        when(clientService.listAllClients(pageNumber, pageSize)).thenReturn(mockPage);

        String viewName = clientController.listAllClients(pageNumber, pageSize, model);

        verify(clientService, times(1)).listAllClients(pageNumber, pageSize);
        verify(model, times(1)).addAttribute(eq("clients"), anyList());
        verify(model, times(1)).addAttribute(eq("currentPage"), eq(pageNumber));
        verify(model, times(1)).addAttribute(eq("totalPages"), eq(2)); // Update to the expected total pages
        verify(model, times(1)).addAttribute(eq("nextPage"), eq(pageNumber + 1));
        verify(model, times(1)).addAttribute(eq("pageSize"), eq(pageSize));
        assertEquals("clients/index", viewName);
    }


    @Test
    public void testGetClientById() {
        // Create a sample UUID for the client
        UUID clientId = UUID.fromString("bcbf5827-af33-428f-8333-336d86d15dbf");

        // Create a real Client object with sample data
        Client realClient = new Client();
        realClient.setId(clientId);
        realClient.setClientName("Kolyan Client");
        realClient.setPhoneNumber("+380934343502");
        realClient.setClientEmail("michael3.williams@example.com");
        realClient.setCreatedDate(LocalDateTime.parse("2023-08-05T17:11:00"));
        realClient.setUpdatedDate(LocalDateTime.parse("2023-08-05T17:11:00"));

        // Mock the behavior of the clientService.findClientById method
        when(clientService.findClientById(clientId)).thenReturn(realClient);

        // Call the getClientById method of the ClientController
        String viewName = clientController.getClientById(clientId, model);

        // Verify that clientService.findClientById was called exactly once with the correct argument
        verify(clientService, times(1)).findClientById(clientId);

        // Use ArgumentCaptor to capture the argument passed to model.addAttribute
        ArgumentCaptor<Client> clientArgumentCaptor = ArgumentCaptor.forClass(Client.class);
        verify(model, times(1)).addAttribute(eq("client"), clientArgumentCaptor.capture());

        // Retrieve the captured client and assert its properties
        Client capturedClient = clientArgumentCaptor.getValue();
        assertEquals(realClient.getId(), capturedClient.getId());
        assertEquals(realClient.getClientName(), capturedClient.getClientName());
        assertEquals(realClient.getPhoneNumber(), capturedClient.getPhoneNumber());
        assertEquals(realClient.getClientEmail(), capturedClient.getClientEmail());
        assertEquals(realClient.getCreatedDate(), capturedClient.getCreatedDate());
        assertEquals(realClient.getUpdatedDate(), capturedClient.getUpdatedDate());

        // Assert that the returned view name matches the expected value
        assertEquals("clients/show", viewName);
    }


    @Test
    public void testNewClient() {
        String viewName = clientController.newClient(new Client());

        assertEquals("clients/new", viewName);
    }

    @Test
    public void testCreateClientWithValidData() {
        Client client = new Client();
        BindingResult bindingResult = new MapBindingResult(new HashMap<>(), "client");

        String viewName = clientController.createClient(client, bindingResult);

        verify(clientService, times(1)).saveClient(client);
        assertEquals("redirect:/clients", viewName);
    }

    @Test
    public void testCreateClientWithInvalidData() {
        Client client = new Client();
        Map<String, Object> target = new HashMap<>();
        BindingResult bindingResult = new MapBindingResult(target, "client");
        bindingResult.rejectValue("phoneNumber", "error.client", "Phone number error");

        String viewName = clientController.createClient(client, bindingResult);

        verify(clientService, never()).saveClient(client);
        assertEquals("clients/new", viewName);
    }

    @Test
    public void testEditClient() {
        UUID clientId = UUID.randomUUID();
        Client mockClient = new Client();
        when(clientService.findClientById(clientId)).thenReturn(mockClient);

        String viewName = clientController.editClient(model, clientId);

        verify(clientService, times(1)).findClientById(clientId);
        verify(model, times(1)).addAttribute(eq("client"), any(Client.class));
        assertEquals("clients/edit", viewName);
    }

    @Test
    public void testUpdateClientByIdWithValidData() {
        UUID clientId = UUID.randomUUID();
        Client client = new Client();
        Map<String, Object> target = new HashMap<>();
        BindingResult bindingResult = new MapBindingResult(target, "client");

        String viewName = clientController.updateClientById(client, bindingResult, clientId);

        verify(clientService, times(1)).updateClient(clientId, client);
        assertEquals("redirect:/clients", viewName);
    }

    @Test
    public void testUpdateClientByIdWithInvalidData() {
        UUID clientId = UUID.randomUUID();
        Client client = new Client();
        Map<String, Object> target = new HashMap<>();
        BindingResult bindingResult = new MapBindingResult(target, "client");
        bindingResult.rejectValue("phoneNumber", "error.client", "Phone number error");

        String viewName = clientController.updateClientById(client, bindingResult, clientId);

        verify(clientService, never()).updateClient(clientId, client);
        assertEquals("clients/edit", viewName);
    }

    @Test
    public void testDeleteClientById() {
        UUID clientId = UUID.randomUUID();

        String viewName = clientController.deleteClientById(clientId);

        verify(clientService, times(1)).deleteById(clientId);
        assertEquals("redirect:/clients", viewName);
    }

    @Test
    public void testGetClientsByName() {
        String clientName = "John Doe";

        String viewName = clientController.getClientsByName(clientName, model);

        verify(clientService, times(1)).findClientsByName(clientName);
        verify(model, times(1)).addAttribute(eq("clients"), anyList());
        assertEquals("clients/search", viewName);
    }


}