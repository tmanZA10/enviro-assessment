package com.enviro.assessment.grad001.ThaboThobakgale.controllers;

import com.enviro.assessment.grad001.ThaboThobakgale.model.Guideline;
import com.enviro.assessment.grad001.ThaboThobakgale.model.Material;
import com.enviro.assessment.grad001.ThaboThobakgale.model.WasteCategory;
import com.enviro.assessment.grad001.ThaboThobakgale.services.WasteManagementService;
import org.springframework.web.bind.annotation.*;

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
    public void addWasteGuideline(@RequestBody Guideline guideline){
        service.addGuideline(guideline);
    }

    @PutMapping("/waste/update/guideline")
    public void updateWasteGuideline(@RequestBody Guideline guideline){
        service.updateGuideline(guideline);
    }
    @DeleteMapping("/waste/guideline/delete/{id}")
    public void deleteWasteGuideline(@PathVariable Integer id){
        service.deleteGuideline(id);
    }
}
