package ua.dmitriiev.beautysaloon.services;

import org.junit.jupiter.api.Test;
import ua.dmitriiev.beautysaloon.entities.Service;
import ua.dmitriiev.beautysaloon.repositories.ServiceRepository;
import ua.dmitriiev.beautysaloon.services.impl.ServiceServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ServiceServiceImplTest {

    @Test
    public void testFindAll() {
        // Create a mock ServiceRepository
        ServiceRepository serviceRepository = mock(ServiceRepository.class);

        // Create a list of services to be returned by the mock repository
        List<Service> services = new ArrayList<>();
        // Add some services to the list

        // Configure the mock repository to return the list of services when findAll is called
        when(serviceRepository.findAll()).thenReturn(services);

        // Create an instance of the ServiceService and inject the mock repository
        ServiceServiceImpl serviceService = new ServiceServiceImpl(serviceRepository);

        // Call the findAll method
        List<Service> result = serviceService.findAll();

        // Verify that the findAll method of the repository was called once
        verify(serviceRepository, times(1)).findAll();

        // Verify that the returned list of services matches the expected list
        assertEquals(services, result);
    }

    @Test
    public void testFindServiceById() {
        // Create a mock ServiceRepository
        ServiceRepository serviceRepository = mock(ServiceRepository.class);

        // Create a service ID
        UUID serviceId = UUID.randomUUID();

        // Create a service to be returned by the mock repository
        Service service = new Service();
        // Set the necessary properties of the service

        // Configure the mock repository to return the service when findServiceById is called with the given ID
        when(serviceRepository.findServiceById(serviceId)).thenReturn(Optional.of(service));

        // Create an instance of the ServiceService and inject the mock repository
        ServiceServiceImpl serviceService = new ServiceServiceImpl(serviceRepository);

        // Call the findServiceById method
        Service result = serviceService.findServiceById(serviceId);

        // Verify that the findServiceById method of the repository was called once with the given ID
        verify(serviceRepository, times(1)).findServiceById(serviceId);

        // Verify that the returned service matches the expected service
        assertEquals(service, result);
    }

    @Test
    public void testSave() {
        // Create a mock ServiceRepository
        ServiceRepository serviceRepository = mock(ServiceRepository.class);

        // Create a new service
        Service service = new Service();
        // Set the necessary properties of the service

        // Create an instance of the ServiceService and inject the mock repository
        ServiceServiceImpl serviceService = new ServiceServiceImpl(serviceRepository);

        // Call the save method
        serviceService.save(service);

        // Verify that the save method of the repository was called once with the service
        verify(serviceRepository, times(1)).save(service);
    }

    @Test
    public void testUpdate() {
        // Create a mock ServiceRepository
        ServiceRepository serviceRepository = mock(ServiceRepository.class);

        // Create an existing service with an ID
        Service existingService = new Service();
        existingService.setId(UUID.randomUUID());
        // Set the necessary properties of the existing service

        // Create an updated service with the same ID
        Service updatedService = new Service();
        updatedService.setId(existingService.getId());
        // Set the necessary properties of the updated service

        // Configure the mock repository to return the existing service when findServiceById is called
        when(serviceRepository.findServiceById(existingService.getId())).thenReturn(Optional.of(existingService));

        // Create an instance of the ServiceService and inject the mock repository
        ServiceServiceImpl serviceService = new ServiceServiceImpl(serviceRepository);

        // Call the update method
        serviceService.update(existingService.getId(), updatedService);

        // Verify that the save method of the repository was called once with the updated service
        verify(serviceRepository, times(1)).save(updatedService);
    }

    @Test
    public void testDelete() {
        // Create a mock ServiceRepository
        ServiceRepository serviceRepository = mock(ServiceRepository.class);

        // Create a service ID
        UUID serviceId = UUID.randomUUID();

        // Create an instance of the ServiceService and inject the mock repository
        ServiceServiceImpl serviceService = new ServiceServiceImpl(serviceRepository);

        // Call the delete method
        serviceService.delete(serviceId);

        // Verify that the deleteServiceById method of the repository was called once with the given ID
        verify(serviceRepository, times(1)).deleteServiceById(serviceId);
    }
}
