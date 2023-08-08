package ua.dmitriiev.beautysaloon.controllers.api;

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
    @ResponseStatus(HttpStatus.OK)
    public ServiceDTO getServiceById(@PathVariable("id") UUID id) {
        Service service = serviceService.findServiceById(id);

        return salonMapper.serviceToServiceDto(service);
    }

//    @GetMapping()
//    @ResponseStatus(HttpStatus.OK)
//    public List<ServiceDTO> listAllServices() {
//        List<Service> services = serviceService.listServices();
//        return salonMapper.servicesToServicesDto(services);
//    }

//    @GetMapping()
//    @ResponseStatus(HttpStatus.OK)
//    public Page<ServiceDTO> listAllServices(@RequestParam(value = "pageNumber", required = false, defaultValue = "0") Integer pageNumber,
//                                            @RequestParam(value = "pageSize", required = false, defaultValue = "5") Integer pageSize) {
//
//        Page<Service> services = serviceService.listAllServices(pageNumber, pageSize);
//        return salonMapper.pageServicesToPageServicesDto(services);
//    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<ServiceDTO> listAllServices(@RequestParam(value = "pageNumber", required = false, defaultValue = "0") Integer pageNumber,
                                            @RequestParam(value = "pageSize", required = false, defaultValue = "5") Integer pageSize) {

        Page<Service> services = serviceService.listAllServices(pageNumber, pageSize);
        List<ServiceDTO> serviceDTOs = salonMapper.pageServicesToPageServicesDto(services).getContent();
        return serviceDTOs;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public void saveService(@RequestBody @Valid ServiceDTO serviceDTO) {
        Service service = salonMapper.serviceDtoToService(serviceDTO);
        service.setMasterOwner(service.getMasterOwner());

        serviceService.saveService(service);
    }

    @DeleteMapping({"/{id}"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteServiceById(@PathVariable("id") UUID id) {

        serviceService.deleteServiceById(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateServiceById(@PathVariable("id") UUID id, @Valid @RequestBody ServiceDTO serviceDTO) {

        Service service = salonMapper.serviceDtoToService(serviceDTO);
        serviceService.updateService(id, service);

    }

}


