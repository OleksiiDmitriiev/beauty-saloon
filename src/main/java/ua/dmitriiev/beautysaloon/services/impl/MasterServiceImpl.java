package ua.dmitriiev.beautysaloon.services.impl;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.dmitriiev.beautysaloon.entities.Master;

import ua.dmitriiev.beautysaloon.lib.exceptions.*;
import ua.dmitriiev.beautysaloon.repositories.MasterRepository;
import ua.dmitriiev.beautysaloon.services.MasterService;

import java.util.*;

@Slf4j
@Service
@Transactional(readOnly = true)
public class MasterServiceImpl implements MasterService {

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

        try {
            Optional<Master> foundMaster = masterRepository.findMasterById(id);
            log.debug("Get Master by Id - in service. Id: " + id.toString());
            return foundMaster.orElseThrow(() -> new NotFoundException("Master not found"));
        } catch (NotFoundException exception) {
            log.error("Master not found with ID: {}", id, exception);
            throw exception;
        }
    }

    @Override
    public Master findMasterByName(String masterName) {
        Optional<Master> foundMaster = masterRepository.findByMasterNameEqualsIgnoreCase(masterName);
        return foundMaster.orElseThrow(() -> new NotFoundException("Master with name '" + masterName + "' not found."));
    }


    @Transactional
    @Override
    public void saveMaster(Master master) {

        if (master == null ||
                master.getMasterName() == null ||
                master.getPhoneNumber() == null ||
                master.getMasterEmail() == null) {
            log.error("Invalid master data provided: {}", master);
            throw new IllegalArgumentException("Invalid master data provided.");
        }

        Master existingMasterByEmail = masterRepository.findMastersByMasterEmailEqualsIgnoreCase(master.getMasterEmail());
        Master existingMasterByPhoneNumber = masterRepository.findMasterByPhoneNumberEqualsIgnoreCase(master.getPhoneNumber());

        if (existingMasterByEmail != null) {
            log.error("Duplicate email found while saving master: {}", master.getMasterEmail());
            throw new NotUniqueEmailException("Email already exists");
        }

        if (existingMasterByPhoneNumber != null) {
            log.error("Duplicate phone number found while saving master: {}", master.getPhoneNumber());
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
            log.error("Duplicate email found while saving master: {}", updatedMaster.getMasterEmail());
            throw new NotUniqueEmailException("Email already exists");
        }

        if (existingMasterByPhoneNumber != null && !existingMasterByPhoneNumber.getId().equals(masterToBeUpdated.getId())) {
            log.error("Duplicate phone number found while saving master: {}", updatedMaster.getPhoneNumber());
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
        //TODO for restAPI
//        List<Master> masters = masterRepository.findMastersByMasterNameEqualsIgnoreCase(masterName);
//
//        if (masters.isEmpty()) {
//            log.warn("No masters found with name: {}", masterName);
//            throw new NotFoundException("No masters found with name: " + masterName);
//        }
//
//        return masters;
        return masterRepository.findMastersByMasterNameEqualsIgnoreCase(masterName);

    }

    @Override
    public Page<Master> listAllMasters(int pageNumber, int pageSize) {
        try {
            Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.ASC, "masterName"));
            return masterRepository.findAll(pageable);
        } catch (Exception ex) {
            log.error("An error occurred while listing all masters:", ex);
            throw new MasterListException("Error occurred while listing masters. Please try again later.");
        }
    }

}
