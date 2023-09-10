
package ua.dmitriiev.beautysaloon.controllers.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ua.dmitriiev.beautysaloon.entities.Master;
import ua.dmitriiev.beautysaloon.mappers.SalonMapperImpl;
import ua.dmitriiev.beautysaloon.model.MasterDTO;
import ua.dmitriiev.beautysaloon.services.MasterService;


import java.util.UUID;

@Slf4j
@RequestMapping("/api/v1/masters")
@RestController
@Tag(name = "Masters", description = "API endpoints for managing saloon masters")
public class MasterRestController {

    private final MasterService masterService;
    private final SalonMapperImpl salonMapper;


    @Autowired
    public MasterRestController(MasterService masterService, SalonMapperImpl salonMapper) {
        this.masterService = masterService;
        this.salonMapper = salonMapper;
    }


    @GetMapping("/{id}")
    @Operation(summary = "Get master by ID",
            description = "Retrieve a saloon master by their unique ID.")
    @ResponseStatus(HttpStatus.OK)
    public MasterDTO getMasterById(@PathVariable("id") UUID id) {
        Master master = masterService.findMasterById(id);
        return salonMapper.masterToMasterDto(master);
    }

    @GetMapping("/search")
    @Operation(summary = "Get master by name",
            description = "Retrieve a saloon master by their name.")
    @ResponseStatus(HttpStatus.OK)
    public MasterDTO getMasterByName(@RequestParam(required = false) String masterName) {
        Master master = masterService.findMasterByName(masterName);
        return salonMapper.masterToMasterDto(master);
    }


    @GetMapping()
    @Operation(summary = "Get a list of all masters",
            description = "Retrieve a paginated list of saloon masters.")
    @ResponseStatus(HttpStatus.OK)
    public Page<MasterDTO> listAllMasters(@RequestParam(value = "pageNumber", required = false, defaultValue = "0") Integer pageNumber,
                                          @RequestParam(value = "pageSize", required = false, defaultValue = "5") Integer pageSize) {
        Page<Master> masters = masterService.listAllMasters(pageNumber, pageSize);
        return salonMapper.pageMastersToPageMastersDto(masters);
    }

    @PostMapping("/save")
    @Operation(summary = "Create a new master",
            description = "Create a new saloon master.")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveMaster(@RequestBody @Valid MasterDTO masterDTO) {
        Master master = salonMapper.masterDtoToMaster(masterDTO);
        masterService.saveMaster(master);
    }

    @DeleteMapping({"/{id}"})
    @Operation(summary = "Delete master by ID",
            description = "Delete saloon master by their unique ID.")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMasterById(@PathVariable("id") UUID id) {
        masterService.deleteById(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update master by ID",
            description = "Update saloon master details by their unique ID.")
    @ResponseStatus(HttpStatus.OK)
    public void updateMasterById(@PathVariable("id") UUID id, @Valid @RequestBody MasterDTO masterDTO) {
        Master master = salonMapper.masterDtoToMaster(masterDTO);
        masterService.updateMaster(id, master);
    }
}
