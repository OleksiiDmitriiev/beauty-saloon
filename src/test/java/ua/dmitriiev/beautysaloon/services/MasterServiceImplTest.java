//package ua.dmitriiev.beautysaloon.services;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.springframework.data.domain.Page;
//import ua.dmitriiev.beautysaloon.entities.Master;
//import ua.dmitriiev.beautysaloon.repositories.MasterRepository;
//import ua.dmitriiev.beautysaloon.services.MasterService;
//import ua.dmitriiev.beautysaloon.services.MasterServiceImpl;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//import java.util.UUID;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.*;
//
//public class MasterServiceImplTest {
//
//    @Test
//    public void testListMasters() {
//        // Create a mock MasterRepository
//        MasterRepository masterRepository = mock(MasterRepository.class);
//
//        // Create a list of masters to be returned by the mock repository
//        List<Master> masters = new ArrayList<>();
//        // Add some masters to the list
//
//        // Configure the mock repository to return the list of masters when findAll is called
//        when(masterRepository.findAll()).thenReturn(masters);
//
//        // Create an instance of the MasterServiceImpl and inject the mock repository
//        MasterService masterService = new MasterServiceImpl(masterRepository);
//
//        // Call the listMasters method
//        List<Master> result = masterService.listMasters();
//
//        // Verify that the findAll method of the repository was called once
//        verify(masterRepository, times(1)).findAll();
//
//        // Verify that the returned list of masters matches the expected list
//        assertEquals(masters, result);
//    }
//
//    @Test
//    public void testFindMasterById() {
//        // Create a mock MasterRepository
//        MasterRepository masterRepository = mock(MasterRepository.class);
//
//        // Create a master ID
//        UUID masterId = UUID.randomUUID();
//
//        // Create a master to be returned by the mock repository
//        Master master = new Master();
//        // Set the necessary properties of the master
//
//        // Configure the mock repository to return the master when findMasterById is called with the given ID
//        when(masterRepository.findMasterById(masterId)).thenReturn(Optional.of(master));
//
//        // Create an instance of the MasterServiceImpl and inject the mock repository
//        MasterService masterService = new MasterServiceImpl(masterRepository);
//
//        // Call the findMasterById method
//        Master result = masterService.findMasterById(masterId);
//
//        // Verify that the findMasterById method of the repository was called once with the given ID
//        verify(masterRepository, times(1)).findMasterById(masterId);
//
//        // Verify that the returned master matches the expected master
//        assertEquals(master, result);
//    }
//
//    @Test
//    public void testFindMasterByName() {
//        // Create a mock MasterRepository
//        MasterRepository masterRepository = mock(MasterRepository.class);
//
//        // Create a master name
//        String masterName = "John Doe";
//
//        // Create a master to be returned by the mock repository
//        Master master = new Master();
//        // Set the necessary properties of the master
//
//        // Configure the mock repository to return the master when findMasterByName is called with the given name
//        when(masterRepository.findByMasterNameEqualsIgnoreCase(masterName)).thenReturn(Optional.of(master));
//
//        // Create an instance of the MasterServiceImpl and inject the mock repository
//        MasterService masterService = new MasterServiceImpl(masterRepository);
//
//        // Call the findMasterByName method
//        Master result = masterService.findMasterByName(masterName);
//
//        // Verify that the findMasterByName method of the repository was called once with the given name
//        verify(masterRepository, times(1)).findByMasterNameEqualsIgnoreCase(masterName);
//
//        // Verify that the returned master matches the expected master
//        assertEquals(master, result);
//    }
//
//    @Test
//    public void testSaveMaster() {
//        // Create a mock MasterRepository
//        MasterRepository masterRepository = mock(MasterRepository.class);
//
//        // Create a master to be saved
//        Master master = new Master();
//        // Set the necessary properties of the master
//
//        // Create an instance of the MasterServiceImpl and inject the mock repository
//        MasterService masterService = new MasterServiceImpl(masterRepository);
//
//        // Call the saveMaster method
//        masterService.saveMaster(master);
//
//        // Verify that the save method of the repository was called once with the master
//        verify(masterRepository, times(1)).save(master);
//    }
//
//    @Test
//    public void testUpdateMaster() {
//        // Create a mock MasterRepository
//        MasterRepository masterRepository = mock(MasterRepository.class);
//
//        // Create a master ID
//        UUID masterId = UUID.randomUUID();
//
//        // Create an updated master
//        Master updatedMaster = new Master();
//        // Set the necessary properties of the updated master
//
//        // Create a master to be updated
//        Master masterToBeUpdated = new Master();
//        // Set the necessary properties of the master to be updated
//
//        // Configure the mock repository to return the master to be updated when findMasterById is called with the given ID
//        when(masterRepository.findMasterById(masterId)).thenReturn(Optional.of(masterToBeUpdated));
//
//        // Create an instance of the MasterServiceImpl and inject the mock repository
//        MasterService masterService = new MasterServiceImpl(masterRepository);
//
//        // Assign the properties of the updated master to the master to be updated
//        masterToBeUpdated.setId(updatedMaster.getId());
//        // Assign other properties
//
//        // Call the updateMaster method
//        masterService.updateMaster(masterId, updatedMaster);
//
//        // Verify that the findMasterById method of the repository was called once with the given ID
//        verify(masterRepository, times(1)).findMasterById(masterId);
//
//        // Verify that the set properties and save method of the repository were called on the master to be updated
//        assertEquals(updatedMaster.getId(), masterToBeUpdated.getId());
//        // Verify other properties
//
//        verify(masterRepository, times(1)).save(masterToBeUpdated);
//    }
//
//
//    @Test
//    public void testDeleteById() {
//        // Create a mock MasterRepository
//        MasterRepository masterRepository = mock(MasterRepository.class);
//
//        // Create a master ID
//        UUID masterId = UUID.randomUUID();
//
//        // Create an instance of the MasterServiceImpl and inject the mock repository
//        MasterService masterService = new MasterServiceImpl(masterRepository);
//
//        // Call the deleteById method
//        masterService.deleteById(masterId);
//
//        // Verify that the deleteMasterById method of the repository was called once with the given ID
//        verify(masterRepository, times(1)).deleteMasterById(masterId);
//    }
//
//    @Test
//    public void testListAllMasters() {
//        // Create a mock MasterRepository
//        MasterRepository masterRepository = mock(MasterRepository.class);
//
//        // Create a list of masters to be returned by the mock repository
//        List<Master> masters = new ArrayList<>();
//        // Add some masters to the list
//
//        // Configure the mock repository to return the list of masters when findAll is called
//        when(masterRepository.findAll()).thenReturn(masters);
//
//        // Create an instance of the MasterServiceImpl and inject the mock repository
//        MasterService masterService = new MasterServiceImpl(masterRepository);
//
//        // Call the listAllMasters method
//        Page<Master> result = masterService.listAllMasters("", "", "", 1, 10);
//
//        // Verify that the findAll method of the repository was called once
//        verify(masterRepository, times(1)).findAll();
//
//        // Verify that the returned page of masters is not null and contains the expected masters
//        assertNotNull(result);
//        assertEquals(masters, result.getContent());
//    }
//}
