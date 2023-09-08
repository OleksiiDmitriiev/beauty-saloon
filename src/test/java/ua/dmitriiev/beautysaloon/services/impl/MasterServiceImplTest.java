package ua.dmitriiev.beautysaloon.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.data.domain.*;
import ua.dmitriiev.beautysaloon.entities.Master;
import ua.dmitriiev.beautysaloon.lib.exceptions.NotFoundException;
import ua.dmitriiev.beautysaloon.lib.exceptions.NotUniqueEmailException;
import ua.dmitriiev.beautysaloon.lib.exceptions.NotUniquePhoneNumberException;
import ua.dmitriiev.beautysaloon.repositories.MasterRepository;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Slf4j
class MasterServiceImplTest {


    @Mock
    private MasterRepository masterRepository;

    @InjectMocks
    private MasterServiceImpl masterService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void testListMasters_ReturnsListOfMasters() {

        List<Master> expectedMasters = new ArrayList<>();
        expectedMasters.add(new Master());
        expectedMasters.add(new Master());
        expectedMasters.add(new Master());


        when(masterRepository.findAll()).thenReturn(expectedMasters);


        List<Master> actualMasters = masterService.listMasters();


        verify(masterRepository, times(1)).findAll();


        assertEquals(expectedMasters.size(), actualMasters.size());
        assertEquals(expectedMasters, actualMasters);
    }

    @Test
    public void testFindMasterById_ExistingId_ReturnsMaster() {
        UUID existingId = UUID.randomUUID();
        System.out.println("Creating random UUID: " + existingId);

        Master existingMaster = new Master();

        existingMaster.setMasterName("Test master");
        System.out.println("Setting existing master's name: " + existingMaster.getMasterName());

        existingMaster.setId(existingId);
        System.out.println("Setting existing master's id: " + existingMaster.getId());

        when(masterRepository.findMasterById(existingId)).thenReturn(Optional.of(existingMaster));

        Master result = masterService.findMasterById(existingId);

        System.out.println("Checking found master's ID:" + result.getId());
        System.out.println("Checking found master's name: " + result.getMasterName());

        assertNotNull(result);
        assertEquals(existingId, result.getId());
    }


    @Test
    public void testFindMasterById_NonExistingId_ThrowsNotFoundException() {
        UUID nonExistingId = UUID.randomUUID();
        System.out.println(nonExistingId);

        when(masterRepository.findMasterById(nonExistingId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> masterService.findMasterById(nonExistingId));

    }

    @Test
    public void testFindMasterByName_ExistingName_ReturnsMaster() {


        UUID existingId = UUID.randomUUID();
        String existingMasterName = "Anton Master";

        System.out.println("Creating random UUID: " + existingId);

        Master existingMaster = new Master();

        existingMaster.setMasterName(existingMasterName);
        System.out.println("Setting existing master's name: " + existingMaster.getMasterName());

        existingMaster.setId(existingId);
        System.out.println("Setting existing master's id: " + existingMaster.getId());

        when(masterRepository.findByMasterNameEqualsIgnoreCase(existingMasterName)).thenReturn(Optional.of(existingMaster));

        Master result = masterService.findMasterByName(existingMasterName);

        System.out.println("Checking found master's ID:" + result.getId());
        System.out.println("Checking found master's name: " + result.getMasterName());

        assertNotNull(result);
        assertEquals(existingMasterName, result.getMasterName());
    }


    @Test
    public void testFindMasterByName_NonExistingName_ThrowsNotFoundException() {

        String nonExistingName = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        System.out.println(nonExistingName);

        when(masterRepository.findByMasterNameEqualsIgnoreCase(nonExistingName)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> masterService.findMasterByName(nonExistingName));

    }


    @Test
    public void testSaveMaster_ValidMaster_Success() {
        Master validMaster = new Master();
        validMaster.setMasterName("John Doe");
        validMaster.setPhoneNumber("1234567890");
        validMaster.setMasterEmail("john.doe@example.com");

        when(masterRepository.findMastersByMasterEmailEqualsIgnoreCase(validMaster.getMasterEmail())).thenReturn(null);
        when(masterRepository.findMasterByPhoneNumberEqualsIgnoreCase(validMaster.getPhoneNumber())).thenReturn(null);

        masterService.saveMaster(validMaster);

        verify(masterRepository, times(1)).save(validMaster);
    }

    @Test
    public void testSaveMaster_DuplicateEmail_ThrowsNotUniqueEmailException() {
        Master duplicateEmailMaster = new Master();
        duplicateEmailMaster.setMasterName("Jane Doe");
        duplicateEmailMaster.setPhoneNumber("9876543210");
        duplicateEmailMaster.setMasterEmail("jane.doe@example.com");

        when(masterRepository.findMastersByMasterEmailEqualsIgnoreCase(duplicateEmailMaster.getMasterEmail()))
                .thenReturn(duplicateEmailMaster);

        assertThrows(NotUniqueEmailException.class, () -> masterService.saveMaster(duplicateEmailMaster));
    }


    @Test
    public void testSaveMaster_DuplicatePhoneNumber_ThrowsNotUniquePhoneNumberException() {
        Master duplicatePhoneNumberMaster = new Master();
        duplicatePhoneNumberMaster.setMasterName("Jane Doe");
        duplicatePhoneNumberMaster.setPhoneNumber("+380996211547");
        duplicatePhoneNumberMaster.setMasterEmail("jane.doe@example.com");

        when(masterRepository.findMasterByPhoneNumberEqualsIgnoreCase(duplicatePhoneNumberMaster.getPhoneNumber()))
                .thenReturn(duplicatePhoneNumberMaster);

        assertThrows(NotUniquePhoneNumberException.class, () -> masterService.saveMaster(duplicatePhoneNumberMaster));
    }


    @Test
    public void testSaveMaster_InvalidMasterData_ThrowsIllegalArgumentException() {

        Master invalidMaster = new Master();

        assertThrows(IllegalArgumentException.class, () -> masterService.saveMaster(invalidMaster));
    }


    @Test
    public void testUpdateMaster_ValidUpdate_Success() {
        UUID existingId = UUID.randomUUID();
        Master existingMaster = new Master();
        existingMaster.setId(existingId);


        Master updatedMaster = new Master();
        updatedMaster.setId(existingId);
        updatedMaster.setMasterName("Updated Name");

        when(masterRepository.findMasterById(existingId)).thenReturn(Optional.of(existingMaster));
        when(masterRepository.findMasterByMasterEmailEqualsIgnoreCase(updatedMaster.getMasterEmail())).thenReturn(null);
        when(masterRepository.findMasterByPhoneNumberEqualsIgnoreCase(updatedMaster.getPhoneNumber())).thenReturn(null);

        masterService.updateMaster(existingId, updatedMaster);

        verify(masterRepository, times(1)).save(existingMaster);
        assertEquals(updatedMaster.getMasterName(), existingMaster.getMasterName());
    }


    @Test
    public void testUpdateMaster_NonExistingId_ThrowsNotFoundException() {

        UUID nonExistingId = UUID.randomUUID();
        Master updatedMaster = new Master();

        when(masterRepository.findMasterById(nonExistingId)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> masterService.updateMaster(nonExistingId, updatedMaster));
    }


    @Test
    public void testUpdateMaster_DuplicateEmail_ThrowsNotUniqueEmailException() {
        UUID existingId = UUID.randomUUID();
        Master existingMaster = new Master();
        existingMaster.setId(existingId);
        existingMaster.setMasterEmail("master@gmail.com");

        Master updatedMaster = new Master();
        updatedMaster.setId(existingId);
        updatedMaster.setMasterName("Updated Name");
        updatedMaster.setMasterEmail("master@gmail.com");

        when(masterRepository.findMasterById(existingId)).thenReturn(Optional.of(existingMaster));
        when(masterRepository.findMasterByMasterEmailEqualsIgnoreCase(updatedMaster.getMasterEmail()))
                .thenThrow(NotUniqueEmailException.class);

        assertThrows(NotUniqueEmailException.class, () -> masterService.updateMaster(existingId, updatedMaster));
    }


    @Test
    public void testUpdateMaster_DuplicatePhoneNumber_ThrowsNotUniquePhoneNumberException() {
        UUID existingId = UUID.randomUUID();
        Master existingMaster = new Master();
        existingMaster.setId(existingId);
        existingMaster.setPhoneNumber("+380502133698");

        Master updatedMaster = new Master();
        updatedMaster.setId(existingId);
        updatedMaster.setMasterName("Updated Name");
        updatedMaster.setPhoneNumber("+380502133698");

        when(masterRepository.findMasterById(existingId)).thenReturn(Optional.of(existingMaster));
        when(masterRepository.findMasterByPhoneNumberEqualsIgnoreCase(updatedMaster.getPhoneNumber()))
                .thenThrow(NotUniquePhoneNumberException.class);

        assertThrows(NotUniquePhoneNumberException.class, () -> masterService.updateMaster(existingId, updatedMaster));

    }

    @Test
    public void testDeleteById_DeletesMaster() {

        UUID masterId = UUID.randomUUID();
        Master master = new Master();
        master.setId(masterId);

        when(masterRepository.findMasterById(masterId)).thenReturn(Optional.of(master));

        masterService.deleteById(masterId);


        verify(masterRepository, times(1)).deleteMasterById(masterId);
    }


    @Test
    public void testFindMastersByName_ReturnsListOfMasters() {

        String testMasterName = "Test Master";


        Master master1 = new Master();
        master1.setMasterName(testMasterName);

        Master master2 = new Master();
        master2.setMasterName(testMasterName);


        List<Master> expectedMasters = Arrays.asList(master1, master2);


        when(masterRepository.findMastersByMasterNameEqualsIgnoreCase(testMasterName)).thenReturn(expectedMasters);


        List<Master> actualMasters = masterService.findMastersByName(testMasterName);


        verify(masterRepository, times(1)).findMastersByMasterNameEqualsIgnoreCase(testMasterName);


        assertEquals(expectedMasters, actualMasters);
    }

    @Test
    public void testListAllMasters_ReturnsPageOfMasters() {

        int pageNumber = 0;
        int pageSize = 5;

        List<Master> expectedMasters = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            expectedMasters.add(new Master());
        }


        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.ASC, "masterName"));
        when(masterRepository.findAll(pageable)).thenReturn(new PageImpl<>(expectedMasters, pageable, expectedMasters.size()));


        Page<Master> actualPage = masterService.listAllMasters(pageNumber, pageSize);


        verify(masterRepository, times(1)).findAll(pageable);


        assertEquals(expectedMasters.size(), actualPage.getTotalElements());
        assertEquals(expectedMasters, actualPage.getContent());
    }


}