package ua.dmitriiev.beautysaloon.controllers.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import ua.dmitriiev.beautysaloon.entities.Service;

import ua.dmitriiev.beautysaloon.mappers.SalonMapperImpl;

import ua.dmitriiev.beautysaloon.model.ServiceDTO;
import ua.dmitriiev.beautysaloon.services.impl.ServiceServiceImpl;


import java.util.List;
import java.util.UUID;

@Slf4j
@RequestMapping("/api/v1/services")
@Tag(name = "Services", description = "API endpoints for managing saloon services")
@RestController
public class ServiceRestController {


    private final SalonMapperImpl salonMapper;
    private final ServiceServiceImpl serviceService;


    @Autowired
    public ServiceRestController(SalonMapperImpl salonMapper, ServiceServiceImpl serviceService) {
        this.salonMapper = salonMapper;
        this.serviceService = serviceService;
    }


    @GetMapping("/{id}")
    @Operation(summary = "Get service by ID",
            description = "Retrieve an service by unique ID.")
    @ResponseStatus(HttpStatus.OK)
    public ServiceDTO getServiceById(@PathVariable("id") UUID id) {
        Service service = serviceService.findServiceById(id);

        return salonMapper.serviceToServiceDto(service);
    }


    @GetMapping()
    @Operation(summary = "Get a list of all services",
            description = "Retrieve a paginated list of saloon services.")
    @ResponseStatus(HttpStatus.OK)
    public List<ServiceDTO> listAllServices(@RequestParam(value = "pageNumber", required = false, defaultValue = "0") Integer pageNumber,
                                            @RequestParam(value = "pageSize", required = false, defaultValue = "5") Integer pageSize) {

        Page<Service> services = serviceService.listAllServices(pageNumber, pageSize);
        return salonMapper.pageServicesToPageServicesDto(services).getContent();
    }

    @PostMapping()
    @Operation(summary = "Create a new service",
            description = "Create a new service.")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveService(@RequestBody @Valid ServiceDTO serviceDTO) {
        Service service = salonMapper.serviceDtoToService(serviceDTO);
        service.setMasterOwner(service.getMasterOwner());

        serviceService.saveService(service);
    }

    @DeleteMapping({"/{id}"})
    @Operation(summary = "Delete service by ID",
            description = "Delete service by ID")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteServiceById(@PathVariable("id") UUID id) {

        serviceService.deleteServiceById(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update service by ID",
            description = "Update service by ID")
    @ResponseStatus(HttpStatus.OK)
    public void updateServiceById(@PathVariable("id") UUID id, @Valid @RequestBody ServiceDTO serviceDTO) {

        Service service = salonMapper.serviceDtoToService(serviceDTO);
        serviceService.updateService(id, service);

    }

}


