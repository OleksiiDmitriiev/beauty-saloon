package ua.dmitriiev.beautysaloon.controllers.api;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import ua.dmitriiev.beautysaloon.entities.Client;
import ua.dmitriiev.beautysaloon.mappers.SalonMapperImpl;
import ua.dmitriiev.beautysaloon.model.ClientDTO;
import ua.dmitriiev.beautysaloon.services.impl.ClientServiceImpl;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/clients")
public class ClientRestController {

    private final ClientServiceImpl clientService;
    private final SalonMapperImpl salonMapper;

    @Autowired
    public ClientRestController(ClientServiceImpl clientService, SalonMapperImpl salonMapper) {
        this.clientService = clientService;
        this.salonMapper = salonMapper;
    }


    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public Page<ClientDTO> listAllClients(@RequestParam(value = "pageNumber", required = false, defaultValue = "0") Integer pageNumber,
                                          @RequestParam(value = "pageSize", required = false, defaultValue = "5") Integer pageSize) {
        Page<Client> clients = clientService.listAllClients(pageNumber, pageSize);
        return salonMapper.pageClientsToPageClientsDto(clients);
    }


    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ClientDTO getClientById(@PathVariable("id") UUID id) {
        Client client = clientService.findClientById(id);


        return salonMapper.clientToClientDto(client);
    }


    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public ClientDTO getClientByName(@RequestParam(required = false) String clientName) {
        Client client = clientService.findClientByName(clientName);
        return salonMapper.clientToClientDto(client);
    }


    @PostMapping("/save")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Client> saveClient(@RequestBody @Valid Client client) {

        var saveResult = clientService.saveClient(client);
        return ResponseEntity.ok(saveResult);
    }


    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateClientById(@PathVariable("id") UUID id, @Valid @RequestBody ClientDTO clientDTO) {
        Client client = salonMapper.clientDtoToClient(clientDTO);
        clientService.updateClient(id, client);
    }


    @DeleteMapping({"/{id}"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable("id") UUID id) {
        clientService.deleteById(id);
    }


}