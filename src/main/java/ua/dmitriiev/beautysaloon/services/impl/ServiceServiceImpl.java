package ua.dmitriiev.beautysaloon.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import ua.dmitriiev.beautysaloon.entities.Service;
import ua.dmitriiev.beautysaloon.lib.exceptions.NotFoundException;
import ua.dmitriiev.beautysaloon.lib.exceptions.ServiceListException;
import ua.dmitriiev.beautysaloon.lib.validations.ServiceValidator;
import ua.dmitriiev.beautysaloon.repositories.ServiceRepository;
import ua.dmitriiev.beautysaloon.services.ServiceService;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;


@Slf4j
@org.springframework.stereotype.Service
@Transactional(readOnly = true)
public class ServiceServiceImpl implements ServiceService {


    private final ServiceRepository serviceRepository;

    @Autowired
    public ServiceServiceImpl(ServiceRepository serviceRepository) {

        this.serviceRepository = serviceRepository;
    }

    @Override
    public List<Service> listServices() {

        return serviceRepository.findAll();

    }

    @Override
    public Service findServiceById(UUID id) {


        Optional<Service> foundService = serviceRepository.findServiceById(id);

        return foundService.orElseThrow(() -> new NotFoundException("Service not found with ID: " + id));
    }

    @Transactional
    @Override
    public void saveService(Service service) {

        ServiceValidator.validateService(service);

        serviceRepository.save(service);
    }


    @Transactional
    @Override
    public void updateService(UUID id, Service updatedService) {

        ServiceValidator.validateService(updatedService);

        Service serviceToBeUpdated = serviceRepository.findServiceById(id)
                .orElseThrow(() -> new NoSuchElementException("Service not found"));

        serviceToBeUpdated.setServiceName(updatedService.getServiceName());
        serviceToBeUpdated.setServiceRating(updatedService.getServiceRating());
        serviceToBeUpdated.setDescription(updatedService.getDescription());
        serviceToBeUpdated.setMasterOwner(updatedService.getMasterOwner());
        serviceToBeUpdated.setOrders(updatedService.getOrders());
        serviceRepository.save(serviceToBeUpdated);
    }

    @Transactional
    @Override
    public void deleteServiceById(UUID id) {
        serviceRepository.deleteServiceById(id);
    }

    @Override
    public List<Service> findServicesByName(String serviceName) {


        return serviceRepository.findServicesByServiceNameEqualsIgnoreCase(serviceName);
    }

    @Override
    public Page<Service> listAllServices(Integer pageNumber, Integer pageSize) {
        try {
            Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.DESC, "updatedDate"));
            return serviceRepository.findAll(pageable);
        } catch (Exception ex) {
            log.error("An error occurred while listing all services:", ex);
            throw new ServiceListException("Error occurred while listing services. Please try again later.");
        }
    }
}
