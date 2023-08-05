package ua.dmitriiev.beautysaloon.services;

import org.springframework.data.domain.Page;
import ua.dmitriiev.beautysaloon.entities.Service;
import java.util.List;
import java.util.UUID;

public interface ServiceService {

    List<Service> listServices();

    Service findServiceById(UUID id);

    void saveService(Service service);


    void updateService(UUID id, Service updatedService);

    void deleteServiceById(UUID id);

    List<Service> findServicesByName(String serviceName);


    Page<Service> listAllServices(Integer pageNumber, Integer pageSize);
}
