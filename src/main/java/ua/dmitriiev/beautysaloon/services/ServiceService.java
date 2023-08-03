package ua.dmitriiev.beautysaloon.services;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import ua.dmitriiev.beautysaloon.entities.Master;
import ua.dmitriiev.beautysaloon.entities.Service;
import ua.dmitriiev.beautysaloon.repositories.ServiceRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;


@org.springframework.stereotype.Service
@Transactional(readOnly = true)
public class ServiceService {

    //TODO make interface + rename methods

    private final ServiceRepository serviceRepository;

    @Autowired
    public ServiceService(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    public List<Service> findAll() {

        return serviceRepository.findAll();

    }

    public Service findServiceById(UUID id) {
        Optional<Service> foundService = serviceRepository.findServiceById(id);
        return foundService.orElse(null);
    }

    @Transactional
    public void save(Service service) {
        serviceRepository.save(service);
    }


    @Transactional
    public void update(UUID id, Service updatedService) {
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
    public void delete(UUID id) {
        serviceRepository.deleteServiceById(id);
    }

    public List<Service> findServicesByName(String serviceName) {
        return serviceRepository.findServicesByServiceNameEqualsIgnoreCase(serviceName);
    }




    public Page<Service> listAllServices(Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.DESC, "updatedDate"));
        return serviceRepository.findAll(pageable);
    }
}
