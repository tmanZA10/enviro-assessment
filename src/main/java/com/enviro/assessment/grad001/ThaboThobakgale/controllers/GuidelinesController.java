package com.enviro.assessment.grad001.ThaboThobakgale.controllers;

import com.enviro.assessment.grad001.ThaboThobakgale.model.Guideline;
import com.enviro.assessment.grad001.ThaboThobakgale.model.Material;
import com.enviro.assessment.grad001.ThaboThobakgale.model.WasteCategory;
import com.enviro.assessment.grad001.ThaboThobakgale.services.WasteManagementService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
class GuidelinesController {

    private final WasteManagementService service;

    public GuidelinesController(WasteManagementService service) {
        this.service = service;
    }

    @GetMapping("/waste/guidelines/lookup/{material}")
    public List<Guideline> lookUpGuideline(@PathVariable String material){
        return service.getMaterialGuidelines(material);
    }

    @GetMapping("/waste/guidelines")
    public List<Guideline> getGuidelines(){
        return service.getAllGuidelines();
    }

    @PostMapping("/waste/new/guideline")
    public Guideline addWasteGuideline(@RequestBody @Validated Guideline guideline){

        int lines = service.addGuideline(guideline);
        if (lines==-1 ) throw  new ResponseStatusException(HttpStatus.BAD_REQUEST);
        return service.getGuideline(guideline.getGuideline());
    }

    @PutMapping("/waste/update/guideline")
    public Guideline updateWasteGuideline(@RequestBody @Validated Guideline guideline){
        if (guideline.getId() <=0) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        int lines = service.updateGuideline(guideline);
        if (lines == 0) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        return guideline;
    }
    @DeleteMapping("/waste/guideline/delete/{id}")
    public void deleteWasteGuideline(@PathVariable Integer id){
        if (id <=0) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        int lines = service.deleteGuideline(id);
        if (lines ==0) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
}
