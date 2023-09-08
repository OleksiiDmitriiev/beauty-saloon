package ua.dmitriiev.beautysaloon.services;


import org.springframework.data.domain.Page;
import ua.dmitriiev.beautysaloon.entities.Master;


import java.util.List;
import java.util.UUID;

public interface MasterService {

    List<Master> listMasters();

    Master findMasterById(UUID id);


    Master findMasterByName(String masterName);


    void saveMaster(Master master);


    void updateMaster(UUID id, Master updatedMaster);

    void deleteById(UUID id);

    List<Master> findMastersByName(String masterName);


    Page<Master> listAllMasters(int pageNumber, int pageSize);


}
