package ua.dmitriiev.beautysaloon.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.dmitriiev.beautysaloon.entities.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ServiceRepository extends JpaRepository<Service, UUID> {

    Optional<Service> findServiceById(UUID id);

    void deleteServiceById(UUID id);

    Optional<Service> findByServiceNameEqualsIgnoreCase(String serviceName);

    List<Service> findServicesByServiceNameEqualsIgnoreCase(String serviceName);
}