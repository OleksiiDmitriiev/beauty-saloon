package ua.dmitriiev.beautysaloon.services;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.dmitriiev.beautysaloon.entities.Client;
import ua.dmitriiev.beautysaloon.entities.Master;

import ua.dmitriiev.beautysaloon.lib.exceptions.NotFoundException;
import ua.dmitriiev.beautysaloon.lib.exceptions.NotUniqueEmailException;
import ua.dmitriiev.beautysaloon.lib.exceptions.NotUniquePhoneNumberException;
import ua.dmitriiev.beautysaloon.repositories.MasterRepository;

import java.util.*;

@Slf4j
@Service
@Transactional(readOnly = true)
public class MasterServiceImpl implements MasterService {

    private static final int DEFAULT_PAGE_NUMBER = 0;
    private static final int DEFAULT_PAGE_SIZE = 5;
    private final MasterRepository masterRepository;

    @Autowired
    public MasterServiceImpl(MasterRepository masterRepository) {

        this.masterRepository = masterRepository;
    }


    @Override
    public List<Master> listMasters() {

        return masterRepository.findAll();
    }

    @Override
    public Master findMasterById(UUID id) {
        Optional<Master> foundMaster = masterRepository.findMasterById(id);
        log.debug("Get Master by Id - in service. Id: " + id.toString());
        return foundMaster.orElse(null);
    }

    @Override
    public Master findMasterByName(String masterName) {
        Optional<Master> foundMaster = masterRepository.findByMasterNameEqualsIgnoreCase(masterName);
        return foundMaster.orElse(null);
    }


    @Transactional
    @Override
    public void saveMaster(Master master) {


        // Check for duplicate email or phoneNumber before savingg

        Master existingMasterByEmail = masterRepository.findMastersByMasterEmailEqualsIgnoreCase(master.getMasterEmail());
        Master existingMasterByPhoneNumber = masterRepository.findMasterByPhoneNumberEqualsIgnoreCase(master.getPhoneNumber());

        if (existingMasterByEmail != null) {
            throw new NotUniqueEmailException("Email already exists");
        }

        if (existingMasterByPhoneNumber != null) {
            throw new NotUniquePhoneNumberException("Phone number already exists");
        }

        masterRepository.save(master);

    }

    @Transactional
    @Override
    public void updateMaster(UUID id, Master updatedMaster) {

        Master masterToBeUpdated = masterRepository.findMasterById(id)
                .orElseThrow(() -> new NoSuchElementException("Master not found"));

        Master existingMasterByEmail = masterRepository.findMasterByMasterEmailEqualsIgnoreCase(updatedMaster.getMasterEmail());
        Master existingMasterByPhoneNumber = masterRepository.findMasterByPhoneNumberEqualsIgnoreCase(updatedMaster.getPhoneNumber());


        if (existingMasterByEmail != null && !existingMasterByEmail.getId().equals(masterToBeUpdated.getId())) {
            throw new NotUniqueEmailException("Email already exists");
        }

        if (existingMasterByPhoneNumber != null && !existingMasterByPhoneNumber.getId().equals(masterToBeUpdated.getId())) {
            throw new NotUniquePhoneNumberException("Phone number already exists");
        }

        masterToBeUpdated.setMasterName(updatedMaster.getMasterName());
        masterToBeUpdated.setServices(updatedMaster.getServices());
        masterToBeUpdated.setMasterRating(updatedMaster.getMasterRating());
        masterToBeUpdated.setPhoneNumber(updatedMaster.getPhoneNumber());
        masterToBeUpdated.setMasterEmail(updatedMaster.getMasterEmail());


        masterRepository.save(masterToBeUpdated);
    }


    @Transactional
    @Override
    public void deleteById(UUID id) {

        masterRepository.deleteMasterById(id);
    }

    @Override
    public List<Master> findMastersByName(String masterName) {
        return masterRepository.findMastersByMasterNameEqualsIgnoreCase(masterName);
    }

    //TODO Refactor
//    @Override
//    public Page<Master> listAllMasters(String masterName, String phoneNumber, String masterEmail, Integer pageNumber, Integer pageSize) {
//        return new PageImpl<>(new ArrayList<>(masterRepository.findAll()));
//    }

    //THIS WORKS
//    @Override
//    public Page<Master> listAllMasters() {
////        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.ASC, "masterName"));
//
//        Pageable pageable = PageRequest.of(DEFAULT_PAGE_NUMBER, DEFAULT_PAGE_SIZE, Sort.by(Sort.Direction.ASC, "masterName"));
//
//        List<Master> masters = masterRepository.findAll(pageable).getContent();
//
//        return new PageImpl<>(masters, pageable, masters.size());
//    }
    @Override
    public Page<Master> listAllMasters(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.ASC, "masterName"));
        return masterRepository.findAll(pageable);
    }

    //IT WORKS
//    @Override
//    public Page<Master> listAllMasters() {
//        Pageable pageable = PageRequest.of(DEFAULT_PAGE_NUMBER, DEFAULT_PAGE_SIZE, Sort.by(Sort.Direction.ASC, "masterName"));
//        return masterRepository.findAll(pageable);
//    }

//    @Override
//    public Page<Master> listMasters(String masterName, Integer masterRating, String phoneNumber, String masterEmail, Integer pageNumber, Integer pageSize) {
//        return new PageImpl<>(new ArrayList<>(masterRepository.findAll()));
////        return new PageImpl<>(new ArrayList<>(beerMap.values()));
//    }

}
