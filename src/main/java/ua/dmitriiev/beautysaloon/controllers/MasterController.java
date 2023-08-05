package ua.dmitriiev.beautysaloon.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.dmitriiev.beautysaloon.entities.Master;
import ua.dmitriiev.beautysaloon.lib.exceptions.NotUniqueEmailException;
import ua.dmitriiev.beautysaloon.lib.exceptions.NotUniquePhoneNumberException;
import ua.dmitriiev.beautysaloon.services.impl.MasterServiceImpl;


import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/masters")
public class MasterController {

    //TODO change methods name

    private final MasterServiceImpl masterService;


    @Autowired
    public MasterController(MasterServiceImpl masterService) {

        this.masterService = masterService;
    }

//
//    @GetMapping()
//    public String listAllMasters(@RequestParam(value = "pageNumber", required = false, defaultValue = "0") Integer pageNumber,
//                                 @RequestParam(value = "pageSize", required = false, defaultValue = "5") Integer pageSize, Model model) {
////        model.addAttribute("masters", masterService.listMasters());
//        Page<Master> masterPage = masterService.listAllMasters(pageNumber, pageSize);
//        model.addAttribute("masters", masterPage);
////        model.addAttribute("masters", masterService.listAllMasters());
//        return "masters/index";
//    }

    @GetMapping()
    public String listAllMasters(@RequestParam(value = "pageNumber", required = false, defaultValue = "0") Integer pageNumber,
                                 @RequestParam(value = "pageSize", required = false, defaultValue = "5") Integer pageSize, Model model) {

        if (pageNumber < 0) {
            pageNumber = 0;
        }

        Page<Master> masterPage = masterService.listAllMasters(pageNumber, pageSize);
        List<Master> masters = masterPage.getContent();


        model.addAttribute("masters", masters);
        model.addAttribute("currentPage", pageNumber);
        model.addAttribute("totalPages", masterPage.getTotalPages());
        model.addAttribute("nextPage", pageNumber + 1); // Calculate the nextPage value
//        model.addAttribute("nextPage", pageNumber + 1); // Calculate the nextPage value
        model.addAttribute("pageSize", pageSize); // Add thi

        return "masters/index";
    }


    @GetMapping("/{id}")
    public String getMasterById(@PathVariable("id") UUID id, Model model) {
        model.addAttribute("master", masterService.findMasterById(id));

        return "masters/show";
    }

    @GetMapping("/new")
    public String newMaster(@ModelAttribute("master") Master master) {

        return "masters/new";
    }

    @PostMapping()
    public String createNewMaster(@ModelAttribute("master") @Valid Master master, BindingResult bindingResult) {


        if (bindingResult.hasErrors()) {
            return "masters/new";
        }

        try {
            masterService.saveMaster(master);
        } catch (NotUniquePhoneNumberException ex) {
            bindingResult.rejectValue("phoneNumber", "error.master", ex.getMessage());
            return "masters/new";
        } catch (NotUniqueEmailException ex) {
            bindingResult.rejectValue("masterEmail", "error.master", ex.getMessage());
            return "masters/new";
        }

        return "redirect:/masters";

    }

    @GetMapping("/{id}/edit")
    public String editMaster(Model model, @PathVariable("id") UUID id) {
        model.addAttribute("master", masterService.findMasterById(id));
        return "masters/edit";
    }

    @PatchMapping("/{id}")
    public String updateMasterById(@ModelAttribute("master") @Valid Master master, BindingResult bindingResult,
                                   @PathVariable("id") UUID id) {

        if (bindingResult.hasErrors())
            return "masters/edit";


        try {
            masterService.updateMaster(id, master);
        } catch (NotUniquePhoneNumberException ex) {
            bindingResult.rejectValue("phoneNumber", "error.master", ex.getMessage());
            return "masters/edit";
        } catch (NotUniqueEmailException ex) {
            bindingResult.rejectValue("masterEmail", "error.master", ex.getMessage());
            return "masters/edit";
        }

        return "redirect:/masters";
    }

    @DeleteMapping("/{id}")
    public String deleteMasterById(@PathVariable("id") UUID id) {
        masterService.deleteById(id);
        return "redirect:/masters";
    }

    //    @GetMapping("/search")
//    public String getMasterByName(@RequestParam(required = false) String masterName, Model model) {
//
//        model.addAttribute("master", masterService.findMasterByName(masterName));
//
//        return "masters/search";
//    }
    @GetMapping("/search")
    public String getMastersByName(@RequestParam(required = false) String masterName, Model model) {

        model.addAttribute("masters", masterService.findMastersByName(masterName));

        return "masters/search";
    }


}


