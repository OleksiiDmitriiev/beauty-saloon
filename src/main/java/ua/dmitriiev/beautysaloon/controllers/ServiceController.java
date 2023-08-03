package ua.dmitriiev.beautysaloon.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.dmitriiev.beautysaloon.entities.Master;
import ua.dmitriiev.beautysaloon.entities.Service;
import ua.dmitriiev.beautysaloon.services.MasterService;
import ua.dmitriiev.beautysaloon.services.ServiceService;


import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/services")
public class ServiceController {

    private final ServiceService serviceService;
    private final MasterService masterService;


    @Autowired
    public ServiceController(ServiceService serviceService, MasterService masterService) {
        this.serviceService = serviceService;
        this.masterService = masterService;
    }


//    @GetMapping()
//    public String index(Model model) {
//        model.addAttribute("services", serviceService.findAll());
//        return "services/index";
//    }

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
        model.addAttribute("nextPage", pageNumber + 1); // Calculate the nextPage value
        model.addAttribute("pageSize", pageSize); // Add thi

        return "services/index";
    }



    @GetMapping("/{id}")
    public String show(@PathVariable("id") UUID id, Model model) {
        model.addAttribute("service", serviceService.findServiceById(id));

        return "services/show";
    }

    @GetMapping("/new")
    public String newService(Model model ) {
        model.addAttribute("service", new Service());
        model.addAttribute("allMasters", masterService.listMasters());
        return "services/new";
    }
//    @GetMapping("/new")
//    public String newService(@ModelAttribute("service") Service service) {
//        model.addAttribute("services", serviceService.findAll());
//        return "services/new";
//    }


    @PostMapping()
    public String create(@ModelAttribute("service") @Valid @RequestBody Service service, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "services/new";

        serviceService.save(service);
        return "redirect:/services";

    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") UUID id) {
        model.addAttribute("service", serviceService.findServiceById(id));
        model.addAttribute("services", serviceService.findAll());
        return "services/edit";
    }


    @PatchMapping("/{id}")
    public String update(@ModelAttribute("service") @Valid Service service, BindingResult bindingResult,
                         @PathVariable("id") UUID id) {

        if (bindingResult.hasErrors())
            return "services/edit";

//        serviceService.update(id, service);
        serviceService.update(id,service);
        return "redirect:/services";
    }


    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") UUID id) {
        serviceService.delete(id);
        return "redirect:/services";
    }

    @GetMapping("/search")
    public String getServicesByName(@RequestParam(required = false) String serviceName, Model model) {

        model.addAttribute("services", serviceService.findServicesByName(serviceName));

        return "services/search";
    }


}
