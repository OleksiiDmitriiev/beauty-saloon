package ua.dmitriiev.beautysaloon.services.impl;


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

        List<Service> expectedServices = new ArrayList<>();
        expectedServices.add(new Service());
        expectedServices.add(new Service());
        expectedServices.add(new Service());


        when(serviceRepository.findAll()).thenReturn(expectedServices);


        List<Service> actualServices = serviceService.listServices();


        verify(serviceRepository, times(1)).findAll();


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


    @Test
    public void testSaveService_ValidService_Success() {

        Master existingMaster = new Master();
        existingMaster.setId(UUID.randomUUID());

        Service validService = new Service();
        validService.setServiceName("Test Service");
        validService.setDescription("Description");
        validService.setMasterOwner(existingMaster);


        serviceService.saveService(validService);


        verify(serviceRepository, times(1)).save(validService);
    }

    @Test
    public void testSaveService_NullServiceData_ThrowsIllegalArgumentException() {

        Service nullService = null;


        assertThrows(IllegalArgumentException.class, () -> serviceService.saveService(nullService));


        verify(serviceRepository, never()).save(nullService);
    }

    @Test
    public void testSaveServiceValidData() {
        Service validService = new Service();
        validService.setServiceName("Valid Service");
        validService.setDescription("Description");


        Master masterOwner = new Master();


        validService.setMasterOwner(masterOwner);

        serviceService.saveService(validService);


        verify(serviceRepository, times(1)).save(validService);
    }


    @Test
    public void testSaveServiceInvalidData() {
        Service invalidService = new Service();


        try {
            serviceService.saveService(invalidService);

            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {

        }


        verify(serviceRepository, never()).save(invalidService);
    }


    @Test
    public void testUpdateServiceValidData() {

        UUID id = UUID.randomUUID();
        Service existingService = new Service();
        existingService.setServiceName("Manicure");
        System.out.println("Existing name " + existingService.getServiceName());

        existingService.setId(id);

        Service updatedService = new Service();
        updatedService.setServiceName("Pedicure");
        System.out.println("Name we want to give: " + updatedService.getServiceName());


        when(serviceRepository.findServiceById(id)).thenReturn(Optional.of(existingService));

        serviceService.updateService(id, updatedService);
        System.out.println("After update: " + existingService.getServiceName());


        verify(serviceRepository, times(1)).save(existingService);
    }

    @Test
    public void testUpdateServiceNotFound() {
        UUID id = UUID.randomUUID();
        Service updatedService = new Service();


        when(serviceRepository.findServiceById(id)).thenReturn(Optional.empty());

        try {
            serviceService.updateService(id, updatedService);

            fail("Expected NoSuchElementException");
        } catch (NoSuchElementException e) {

        }


        verify(serviceRepository, never()).save(any());
    }


    @Test
    public void testDeleteServiceById() {
        UUID idToDelete = UUID.randomUUID();
        Service service = new Service();
        service.setId(idToDelete);

        serviceService.deleteServiceById(idToDelete);


        verify(serviceRepository, times(1)).deleteServiceById(idToDelete);
    }


    @Test
    public void testFindServicesByName() {
        String serviceName = "Example Service Name";

        List<Service> mockServiceList = new ArrayList<>();

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


        verify(serviceRepository, times(1)).findServicesByServiceNameEqualsIgnoreCase(serviceName);


        assertEquals(mockServiceList.size(), result.size());
    }


    @Test
    public void testListAllServices() {
        int pageNumber = 0;
        int pageSize = 5;

        List<Service> mockServiceList = new ArrayList<>();


        for (int i = 1; i <= 12; i++) {
            mockServiceList.add(new Service());
        }

        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.DESC, "updatedDate"));
        Page<Service> mockPage = new PageImpl<>(mockServiceList, pageable, mockServiceList.size());

        when(serviceRepository.findAll(pageable)).thenReturn(mockPage);

        Page<Service> result = serviceService.listAllServices(pageNumber, pageSize);


        verify(serviceRepository, times(1)).findAll(pageable);


        assertEquals(mockPage.getContent(), result.getContent());
    }

    @Test
    public void testListAllServicesException() {
        int pageNumber = 0;
        int pageSize = 5;

        when(serviceRepository.findAll(any(Pageable.class))).thenThrow(new RuntimeException());

        assertThrows(ServiceListException.class, () -> serviceService.listAllServices(pageNumber, pageSize));


        verify(serviceRepository, times(1)).findAll(any(Pageable.class));
    }

}