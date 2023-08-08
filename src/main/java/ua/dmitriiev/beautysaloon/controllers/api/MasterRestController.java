
package ua.dmitriiev.beautysaloon.controllers.api;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ua.dmitriiev.beautysaloon.entities.Client;
import ua.dmitriiev.beautysaloon.entities.Master;

import ua.dmitriiev.beautysaloon.mappers.SalonMapperImpl;
import ua.dmitriiev.beautysaloon.model.ClientDTO;
import ua.dmitriiev.beautysaloon.model.MasterDTO;
import ua.dmitriiev.beautysaloon.services.MasterService;

import java.util.List;
import java.util.UUID;

@Slf4j
@RequestMapping("/api/v1/masters")
@RestController
public class MasterRestController {

    private final MasterService masterService;
    private final SalonMapperImpl salonMapper;


    @Autowired
    public MasterRestController(MasterService masterService, SalonMapperImpl salonMapper) {
        this.masterService = masterService;
        this.salonMapper = salonMapper;
    }


    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public MasterDTO getMasterById(@PathVariable("id") UUID id) {
        Master master = masterService.findMasterById(id);
        return salonMapper.masterToMasterDto(master);
    }

    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public MasterDTO getMasterByName(@RequestParam(required = false) String masterName) {
        Master master = masterService.findMasterByName(masterName);
        return salonMapper.masterToMasterDto(master);
    }

//    @GetMapping()
//    @ResponseStatus(HttpStatus.OK)
//    public List<MasterDTO> listAllMasters() {
//        List<Master> masters = masterService.listMasters();
//        return salonMapper.mastersToMastersDto(masters);
//    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public Page<MasterDTO> listAllMasters(@RequestParam(value = "pageNumber", required = false, defaultValue = "0") Integer pageNumber,
                                          @RequestParam(value = "pageSize", required = false, defaultValue = "5") Integer pageSize) {
        Page<Master> masters = masterService.listAllMasters(pageNumber, pageSize);
        return salonMapper.pageMastersToPageMastersDto(masters);
    }

    @PostMapping("/save")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveMaster(@RequestBody @Valid MasterDTO masterDTO) {
        Master master = salonMapper.masterDtoToMaster(masterDTO);
        masterService.saveMaster(master);
    }

    @DeleteMapping({"/{id}"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMasterById(@PathVariable("id") UUID id) {
        masterService.deleteById(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateMasterById(@PathVariable("id") UUID id, @Valid @RequestBody MasterDTO masterDTO) {
        Master master = salonMapper.masterDtoToMaster(masterDTO);
        masterService.updateMaster(id, master);
    }
}
