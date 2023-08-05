package ua.dmitriiev.beautysaloon.repositories;

import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;
import ua.dmitriiev.beautysaloon.entities.Master;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface MasterRepository extends JpaRepository<Master, UUID> {

    Optional<Master> findMasterById(UUID id);


    void deleteMasterById(UUID id);

    Optional<Master> findByMasterNameEqualsIgnoreCase(String masterName);
    List<Master> findMastersByMasterNameEqualsIgnoreCase(String masterName);

    Master findMasterByPhoneNumberEqualsIgnoreCase(String phoneNumber);

    Master findMasterByMasterEmailEqualsIgnoreCase(String masterEmail);


    Master findMastersByMasterEmailEqualsIgnoreCase(String masterEmail);
}
