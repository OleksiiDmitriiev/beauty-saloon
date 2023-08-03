package ua.dmitriiev.beautysaloon.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.dmitriiev.beautysaloon.entities.Client;
import ua.dmitriiev.beautysaloon.entities.Order;
import ua.dmitriiev.beautysaloon.services.ClientServiceImpl;


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

        if (pageNumber < 0) pageNumber = 0;


        Page<Client> clientPage = clientService.listAllClients(pageNumber, pageSize);
        List<Client> clients = clientPage.getContent();



        model.addAttribute("clients", clients);
        model.addAttribute("currentPage", pageNumber);
        model.addAttribute("totalPages", clientPage.getTotalPages());
        model.addAttribute("nextPage", pageNumber + 1); // Calculate the nextPage value
//        model.addAttribute("nextPage", pageNumber + 1); // Calculate the nextPage value
        model.addAttribute("pageSize", pageSize); // Add thi

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
        if (bindingResult.hasErrors())
            return "clients/new";

        clientService.saveClient(client);
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

        clientService.updateClient(id, client);
        return "redirect:/clients";
    }

    @DeleteMapping("/{id}")
    public String deleteClientById(@PathVariable("id") UUID id) {
        clientService.deleteById(id);
        return "redirect:/clients";
    }

    @GetMapping("/search")
    public String getClientsByEmail(@RequestParam(required = false) String clientEmail, Model model) {

        model.addAttribute("clients", clientService.findClientsByEmail(clientEmail));

        return "clients/search";
    }

}
