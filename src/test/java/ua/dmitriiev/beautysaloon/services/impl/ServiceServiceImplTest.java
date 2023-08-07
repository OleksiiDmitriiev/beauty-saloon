package ua.dmitriiev.beautysaloon.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.data.domain.*;
import ua.dmitriiev.beautysaloon.entities.Master;
import ua.dmitriiev.beautysaloon.entities.Service;

import ua.dmitriiev.beautysaloon.lib.exceptions.NotFoundException;
import ua.dmitriiev.beautysaloon.lib.exceptions.ServiceListException;
import ua.dmitriiev.beautysaloon.repositories.ServiceRepository;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class ServiceServiceImplTest {

    @Mock
    private ServiceRepository serviceRepository;

    @InjectMocks
    private ServiceServiceImpl serviceService;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void testListServices_ReturnsListOfServices() {
        // Create test data
        List<Service> expectedServices = new ArrayList<>();
        expectedServices.add(new Service());
        expectedServices.add(new Service());
        expectedServices.add(new Service());

        // Mock the behavior of serviceRepository
        when(serviceRepository.findAll()).thenReturn(expectedServices);

        // Call listServices method
        List<Service> actualServices = serviceService.listServices();

        // Verify that the serviceRepository method was called
        verify(serviceRepository, times(1)).findAll();

        // Verify the content of the returned list
        assertEquals(expectedServices.size(), actualServices.size());
        assertEquals(expectedServices, actualServices);
    }


    @Test
    public void testFindServiceById_ExistingId_ReturnsService() {


        UUID existingId = UUID.randomUUID();
        System.out.println("Creating random UUID: " + existingId);

        Service existingService = new Service();

        existingService.setServiceName("Test service");
        System.out.println("Setting existing service's name: " + existingService.getServiceName());

        existingService.setId(existingId);
        System.out.println("Setting existing service's id: " + existingService.getId());

        when(serviceRepository.findServiceById(existingId)).thenReturn(Optional.of(existingService));

        Service result = serviceService.findServiceById(existingId);

        System.out.println("Checking found service's ID:" + result.getId());
        System.out.println("Checking found service's name: " + result.getServiceName());

        assertNotNull(result);
        assertEquals(existingId, result.getId());
    }


    @Test
    public void testFindServiceById_NonExistingId_ThrowsNotFoundException() {

        UUID nonExistingId = UUID.randomUUID();
        System.out.println(nonExistingId);

        when(serviceRepository.findServiceById(nonExistingId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> serviceService.findServiceById(nonExistingId));

    }


    //TODO REFACTOR CONTINUE

    @Test
    public void testSaveService_ValidService_Success() {
        // Create a valid service
        Master existingMaster = new Master();
        existingMaster.setId(UUID.randomUUID());

        Service validService = new Service();
        validService.setServiceName("Test Service");
        validService.setDescription("Description");
        validService.setMasterOwner(existingMaster);

        // Call saveService method
        serviceService.saveService(validService);

        // Verify that the serviceRepository method was called
        verify(serviceRepository, times(1)).save(validService);
    }

    @Test
    public void testSaveService_NullServiceData_ThrowsIllegalArgumentException() {
        // Create a null service
        Service nullService = null;

        // Call saveService method and assert exception
        assertThrows(IllegalArgumentException.class, () -> serviceService.saveService(nullService));

        // Verify that the serviceRepository method was not called
        verify(serviceRepository, never()).save(nullService);
    }

    @Test
    public void testSaveServiceValidData() {
        Service validService = new Service();
        validService.setServiceName("Valid Service");
        validService.setDescription("Description");

        // Set other required properties for the valid service
        Master masterOwner = new Master();
        // Set properties for the masterOwner

        validService.setMasterOwner(masterOwner);

        serviceService.saveService(validService);

        // Verify that save method of serviceRepository was called with validService
        verify(serviceRepository, times(1)).save(validService);
    }


    @Test
    public void testSaveServiceInvalidData() {
        Service invalidService = new Service();
        // Do not set required properties

        try {
            serviceService.saveService(invalidService);
            // Expect an exception to be thrown
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Verify that the exception was thrown
            // (You can add more specific checks for the exception message if needed)
        }

        // Verify that save method of serviceRepository was not called
        verify(serviceRepository, never()).save(invalidService);
    }


    @Test
    public void testUpdateServiceValidData() {

        UUID id = UUID.randomUUID(); // Example ID
        Service existingService = new Service();
        existingService.setServiceName("Manicure");
        System.out.println("Existing name " + existingService.getServiceName());
        // Set properties for the existingService
        existingService.setId(id);

        Service updatedService = new Service();
        updatedService.setServiceName("Pedicure");
        System.out.println("Name we want to give: " + updatedService.getServiceName());
        // Set properties for the updatedService

        when(serviceRepository.findServiceById(id)).thenReturn(Optional.of(existingService));

        serviceService.updateService(id, updatedService);
        System.out.println("After update: " + existingService.getServiceName());


        // Verify that save method of serviceRepository was called with the updated existingService
        verify(serviceRepository, times(1)).save(existingService);
    }

    @Test
    public void testUpdateServiceNotFound() {
        UUID id = UUID.randomUUID(); // Example ID
        Service updatedService = new Service();
        // Set properties for the updatedService

        when(serviceRepository.findServiceById(id)).thenReturn(Optional.empty());

        try {
            serviceService.updateService(id, updatedService);
            // Expect an exception to be thrown
            fail("Expected NoSuchElementException");
        } catch (NoSuchElementException e) {
            // Verify that the exception was thrown
            // (You can add more specific checks for the exception message if needed)
        }

        // Verify that save method of serviceRepository was not called
        verify(serviceRepository, never()).save(any());
    }


    @Test
    public void testDeleteServiceById() {
        UUID idToDelete = UUID.randomUUID(); // Example ID
        Service service = new Service();
        service.setId(idToDelete);

        serviceService.deleteServiceById(idToDelete);

        // Verify that deleteServiceById method of serviceRepository was called with the provided ID
        verify(serviceRepository, times(1)).deleteServiceById(idToDelete);
    }


    @Test
    public void testFindServicesByName() {
        String serviceName = "Example Service Name";

        List<Service> mockServiceList = new ArrayList<>();
        // Create and add mock Service objects to mockServiceList
        Service service1 = new Service();
        service1.setServiceName(serviceName);
        mockServiceList.add(service1);

        Service service2 = new Service();
        service2.setServiceName(serviceName);
        mockServiceList.add(service2);

        Service service3 = new Service();
        service3.setServiceName(serviceName);
        mockServiceList.add(service3);

        when(serviceRepository.findServicesByServiceNameEqualsIgnoreCase(serviceName)).thenReturn(mockServiceList);

        List<Service> result = serviceService.findServicesByName(serviceName);

        // Verify that findServicesByServiceNameEqualsIgnoreCase method of serviceRepository was called with serviceName
        verify(serviceRepository, times(1)).findServicesByServiceNameEqualsIgnoreCase(serviceName);

        // Compare the expected list size with the actual list size
        assertEquals(mockServiceList.size(), result.size());
    }

//    @Test
//    public void testFindServicesByNameNoResults() {
//        String serviceName = "NonExistentService";
//
//        // Configure the mock to return an empty list when findServicesByServiceNameEqualsIgnoreCase is called
//        when(serviceRepository.findServicesByServiceNameEqualsIgnoreCase(serviceName)).thenReturn(Collections.emptyList());
//
//        // Verify that NotFoundException is thrown when no results are found
//        assertThrows(NotFoundException.class, () -> serviceService.findServicesByName(serviceName));
//    }


    @Test
    public void testListAllServices() {
        int pageNumber = 0;
        int pageSize = 5;

        List<Service> mockServiceList = new ArrayList<>();
        // Create and add mock Service objects to mockServiceList

        for (int i = 1; i <= 12; i++) {
            mockServiceList.add(new Service());
        }

        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.DESC, "updatedDate"));
        Page<Service> mockPage = new PageImpl<>(mockServiceList, pageable, mockServiceList.size());

        when(serviceRepository.findAll(pageable)).thenReturn(mockPage);

        Page<Service> result = serviceService.listAllServices(pageNumber, pageSize);

        // Verify that findAll method of serviceRepository was called with the specified pageable
        verify(serviceRepository, times(1)).findAll(pageable);

        // Compare the expected page content with the actual page content
        assertEquals(mockPage.getContent(), result.getContent());
    }

    @Test
    public void testListAllServicesException() {
        int pageNumber = 0;
        int pageSize = 5;

        when(serviceRepository.findAll(any(Pageable.class))).thenThrow(new RuntimeException());

        assertThrows(ServiceListException.class, () -> serviceService.listAllServices(pageNumber, pageSize));

        // Verify that findAll method of serviceRepository was called with the specified pageable
        verify(serviceRepository, times(1)).findAll(any(Pageable.class));
    }

}