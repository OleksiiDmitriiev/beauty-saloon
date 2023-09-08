package ua.dmitriiev.beautysaloon.controllers;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.dmitriiev.beautysaloon.entities.Service;
import ua.dmitriiev.beautysaloon.lib.exceptions.InvalidMasterException;
import ua.dmitriiev.beautysaloon.services.MasterService;
import ua.dmitriiev.beautysaloon.services.impl.ServiceServiceImpl;


import java.util.List;
import java.util.UUID;

@Slf4j
@Controller
@RequestMapping("/services")
public class ServiceController {

    private final ServiceServiceImpl serviceService;
    private final MasterService masterService;


    @Autowired
    public ServiceController(ServiceServiceImpl serviceService, MasterService masterService) {
        this.serviceService = serviceService;
        this.masterService = masterService;
    }


    @GetMapping()
    public String listAllServices(@RequestParam(value = "pageNumber", required = false, defaultValue = "0") Integer pageNumber,
                                  @RequestParam(value = "pageSize", required = false, defaultValue = "5") Integer pageSize, Model model) {

        if (pageNumber < 0) {
            pageNumber = 0;
        }

        Page<Service> servicePage = serviceService.listAllServices(pageNumber, pageSize);
        List<Service> services = servicePage.getContent();

        model.addAttribute("services", services);
        model.addAttribute("currentPage", pageNumber);
        model.addAttribute("totalPages", servicePage.getTotalPages());
        model.addAttribute("nextPage", pageNumber + 1);
        model.addAttribute("pageSize", pageSize);

        return "services/index";
    }


    @GetMapping("/{id}")
    public String getServiceById(@PathVariable("id") UUID id, Model model) {
        model.addAttribute("service", serviceService.findServiceById(id));

        return "services/show";
    }

    @GetMapping("/new")
    public String newService(Model model) {
        model.addAttribute("service", new Service());
        model.addAttribute("allMasters", masterService.listMasters());
        return "services/new";
    }


    @PostMapping()
    public String createNewService(@ModelAttribute("service") @Valid @RequestBody Service service, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "services/new";

        try {
            serviceService.saveService(service);
        } catch (IllegalArgumentException ex) {
            log.error("IllegalArgumentException occurred:", ex);
            return "services/new";
        } catch (InvalidMasterException ex) {
            log.error("InvalidMasterException occurred:", ex);
            bindingResult.rejectValue("masterOwner", "error.service", ex.getMessage());
            return "services/new";
        }

        return "redirect:/services";

    }

    @GetMapping("/{id}/edit")
    public String editService(Model model, @PathVariable("id") UUID id) {
        model.addAttribute("service", serviceService.findServiceById(id));
        model.addAttribute("services", serviceService.listServices());
        return "services/edit";
    }


    @PatchMapping("/{id}")
    public String updateServiceById(@ModelAttribute("service") @Valid Service service, BindingResult bindingResult,
                                    @PathVariable("id") UUID id) {

        if (bindingResult.hasErrors())
            return "services/edit";

        try {
            serviceService.updateService(id, service);
        } catch (IllegalArgumentException ex) {
            log.error("IllegalArgumentException occurred:", ex);
            return "services/edit";
        } catch (InvalidMasterException ex) {
            log.error("InvalidMasterException occurred:", ex);
            bindingResult.rejectValue("masterOwner", "error.service", ex.getMessage());
            return "services/edit";
        }


        return "redirect:/services";
    }


    @DeleteMapping("/{id}")
    public String deleteServiceById(@PathVariable("id") UUID id) {
        serviceService.deleteServiceById(id);
        return "redirect:/services";
    }

    @GetMapping("/search")
    public String getServicesByName(@RequestParam(required = false) String serviceName, Model model) {

        model.addAttribute("services", serviceService.findServicesByName(serviceName));

        return "services/search";
    }


}
