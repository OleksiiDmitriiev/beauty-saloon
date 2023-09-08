package ua.dmitriiev.beautysaloon.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import ua.dmitriiev.beautysaloon.entities.Master;
import ua.dmitriiev.beautysaloon.lib.exceptions.NotUniqueEmailException;
import ua.dmitriiev.beautysaloon.lib.exceptions.NotUniquePhoneNumberException;
import ua.dmitriiev.beautysaloon.services.impl.MasterServiceImpl;

import java.util.Collections;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MasterControllerTest {
    @Mock
    private MasterServiceImpl masterService;

    @InjectMocks
    private MasterController masterController;

    private Model model;
    private BindingResult bindingResult;

    @BeforeEach
    void setUp() {
        model = mock(Model.class);
        bindingResult = mock(BindingResult.class);
    }

    @Test
    void testListAllMasters() {

        int pageNumber = 0;
        int pageSize = 5;


        Master master = new Master();
        when(masterService.listAllMasters(pageNumber, pageSize)).thenReturn((Page<Master>) Collections.singletonList(master));


        String viewName = masterController.listAllMasters(pageNumber, pageSize, model);


        verify(model).addAttribute("masters", Collections.singletonList(master));
        assertEquals("masters/index", viewName);
    }

    @Test
    void testGetMasterById() {

        UUID masterId = UUID.randomUUID();
        Master master = new Master();
        when(masterService.findMasterById(masterId)).thenReturn(master);


        String viewName = masterController.getMasterById(masterId, model);

        verify(model).addAttribute("master", master);
        assertEquals("masters/show", viewName);
    }

    @Test
    void testNewMaster() {

        String viewName = masterController.newMaster(new Master());


        assertEquals("masters/new", viewName);
    }

    @Test
    void testCreateNewMasterWithValidData() {

        Master master = new Master();


        String viewName = masterController.createNewMaster(master, bindingResult);


        verify(masterService).saveMaster(master);
        assertEquals("redirect:/masters", viewName);
    }

    @Test
    void testCreateNewMasterWithInvalidPhoneNumber() {

        Master master = new Master();
        when(bindingResult.hasErrors()).thenReturn(true);
        when(bindingResult.getFieldError("phoneNumber")).thenReturn(null);


        String viewName = masterController.createNewMaster(master, bindingResult);


        verify(masterService, never()).saveMaster(master);
        assertEquals("masters/new", viewName);
    }

    @Test
    void testCreateNewMasterWithNotUniquePhoneNumber() {

        Master master = new Master();
        when(bindingResult.hasErrors()).thenReturn(false);
        when(bindingResult.getFieldError("phoneNumber")).thenReturn(null);
        doThrow(NotUniquePhoneNumberException.class).when(masterService).saveMaster(master);


        String viewName = masterController.createNewMaster(master, bindingResult);


        verify(masterService).saveMaster(master);
        verify(bindingResult).rejectValue("phoneNumber", "error.master", "Phone number error");
        assertEquals("masters/new", viewName);
    }

    @Test
    void testCreateNewMasterWithNotUniqueEmail() {

        Master master = new Master();
        when(bindingResult.hasErrors()).thenReturn(false);
        when(bindingResult.getFieldError("phoneNumber")).thenReturn(null);
        doThrow(NotUniqueEmailException.class).when(masterService).saveMaster(master);


        String viewName = masterController.createNewMaster(master, bindingResult);


        verify(masterService).saveMaster(master);
        verify(bindingResult).rejectValue("masterEmail", "error.master", "Email error");
        assertEquals("masters/new", viewName);
    }

    @Test
    void testEditMaster() {

        UUID masterId = UUID.randomUUID();
        Master master = new Master();
        when(masterService.findMasterById(masterId)).thenReturn(master);


        String viewName = masterController.editMaster(model, masterId);


        verify(model).addAttribute("master", master);
        assertEquals("masters/edit", viewName);
    }

    @Test
    void testUpdateMasterByIdWithValidData() {

        UUID masterId = UUID.randomUUID();
        Master master = new Master();


        String viewName = masterController.updateMasterById(master, bindingResult, masterId);


        verify(masterService).updateMaster(masterId, master);
        assertEquals("redirect:/masters", viewName);
    }

    @Test
    void testUpdateMasterByIdWithInvalidPhoneNumber() {

        UUID masterId = UUID.randomUUID();
        Master master = new Master();
        when(bindingResult.hasErrors()).thenReturn(true);
        when(bindingResult.getFieldError("phoneNumber")).thenReturn(null);


        String viewName = masterController.updateMasterById(master, bindingResult, masterId);


        verify(masterService, never()).updateMaster(masterId, master);
        assertEquals("masters/edit", viewName);
    }

    @Test
    void testUpdateMasterByIdWithNotUniquePhoneNumber() {

        UUID masterId = UUID.randomUUID();
        Master master = new Master();
        when(bindingResult.hasErrors()).thenReturn(false);
        when(bindingResult.getFieldError("phoneNumber")).thenReturn(null);
        doThrow(NotUniquePhoneNumberException.class).when(masterService).updateMaster(masterId, master);


        String viewName = masterController.updateMasterById(master, bindingResult, masterId);


        verify(masterService).updateMaster(masterId, master);
        verify(bindingResult).rejectValue("phoneNumber", "error.master", "Phone number error");
        assertEquals("masters/edit", viewName);
    }

    @Test
    void testUpdateMasterByIdWithNotUniqueEmail() {

        UUID masterId = UUID.randomUUID();
        Master master = new Master();
        when(bindingResult.hasErrors()).thenReturn(false);
        when(bindingResult.getFieldError("phoneNumber")).thenReturn(null);
        doThrow(NotUniqueEmailException.class).when(masterService).updateMaster(masterId, master);


        String viewName = masterController.updateMasterById(master, bindingResult, masterId);


        verify(masterService).updateMaster(masterId, master);
        verify(bindingResult).rejectValue("masterEmail", "error.master", "Email error");
        assertEquals("masters/edit", viewName);
    }

    @Test
    void testDeleteMasterById() {

        UUID masterId = UUID.randomUUID();


        String viewName = masterController.deleteMasterById(masterId);

        verify(masterService).deleteById(masterId);
        assertEquals("redirect:/masters", viewName);
    }
}