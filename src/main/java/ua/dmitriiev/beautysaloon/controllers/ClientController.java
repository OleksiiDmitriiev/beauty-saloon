package ua.dmitriiev.beautysaloon.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.dmitriiev.beautysaloon.entities.Client;
import ua.dmitriiev.beautysaloon.lib.exceptions.NotUniqueEmailException;
import ua.dmitriiev.beautysaloon.lib.exceptions.NotUniquePhoneNumberException;
import ua.dmitriiev.beautysaloon.model.ClientDTO;
import ua.dmitriiev.beautysaloon.services.impl.ClientServiceImpl;


import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/clients")
public class ClientController {

    private final ClientServiceImpl clientService;

    @Autowired
    public ClientController(ClientServiceImpl clientService) {
        this.clientService = clientService;
    }


    @GetMapping()
    public String listAllClients(@RequestParam(value = "pageNumber", required = false, defaultValue = "0") Integer pageNumber,
                                 @RequestParam(value = "pageSize", required = false, defaultValue = "5") Integer pageSize, Model model) {

        if (pageNumber < 0) {
            pageNumber = 0;
        }

        Page<Client> clientPage = clientService.listAllClients(pageNumber, pageSize);

        model.addAttribute("clients", clientPage.getContent());
        model.addAttribute("currentPage", pageNumber);
        model.addAttribute("totalPages", clientPage.getTotalPages());
        model.addAttribute("nextPage", pageNumber + 1);
        model.addAttribute("pageSize", pageSize);

        return "clients/index";
    }

    @GetMapping("/{id}")
    public String getClientById(@PathVariable("id") UUID id, Model model) {
        model.addAttribute("client", clientService.findClientById(id));

        return "clients/show";
    }

    @GetMapping("/new")
    public String newClient(@ModelAttribute("client") Client client) {

        return "clients/new";
    }

    @PostMapping()
    public String createClient(@ModelAttribute("client") @Valid Client client, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "clients/new";
        }

        try {
            clientService.saveClient(client);
        } catch (NotUniquePhoneNumberException ex) {
            bindingResult.rejectValue("phoneNumber", "error.client", ex.getMessage());
            return "clients/new";
        } catch (NotUniqueEmailException ex) {
            bindingResult.rejectValue("clientEmail", "error.client", ex.getMessage());
            return "clients/new";
        }

        return "redirect:/clients";
    }

    @GetMapping("/{id}/edit")
    public String editClient(Model model, @PathVariable("id") UUID id) {
        model.addAttribute("client", clientService.findClientById(id));
        return "clients/edit";
    }

    @PatchMapping("/{id}")
    public String updateClientById(@ModelAttribute("client") @Valid Client client, BindingResult bindingResult,
                                   @PathVariable("id") UUID id) {

        if (bindingResult.hasErrors())
            return "clients/edit";

        try {
            clientService.updateClient(id, client);
        } catch (NotUniquePhoneNumberException ex) {
            bindingResult.rejectValue("phoneNumber", "error.client", ex.getMessage());
            return "clients/edit";
        } catch (NotUniqueEmailException ex) {
            bindingResult.rejectValue("clientEmail", "error.client", ex.getMessage());
            return "clients/edit";
        }

        return "redirect:/clients";


    }

    @DeleteMapping("/{id}")
    public String deleteClientById(@PathVariable("id") UUID id) {
        clientService.deleteById(id);
        return "redirect:/clients";
    }

    @GetMapping("/search")
    public String getClientsByName(@RequestParam(required = false) String clientName, Model model) {

        model.addAttribute("clients", clientService.findClientsByName(clientName));

        return "clients/search";
    }

}


