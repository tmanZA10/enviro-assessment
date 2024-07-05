package com.enviro.assessment.grad001.ThaboThobakgale.controllers;

import com.enviro.assessment.grad001.ThaboThobakgale.model.Material;
import com.enviro.assessment.grad001.ThaboThobakgale.model.WasteCategory;
import com.enviro.assessment.grad001.ThaboThobakgale.services.WasteManagementService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class MaterialsController {

    private final WasteManagementService service;

    public MaterialsController(WasteManagementService service) {
        this.service = service;
    }

    @GetMapping("/waste/materials")
    public List<Material> getMaterials(){
        return service.getMaterials();
    }

    @GetMapping("/waste/materials/lookup/{material}")
    public Material getMaterials(@PathVariable String material){
        return service.lookUpMaterial(material);
    }


    @PostMapping("/waste/new/material")
    public void addWasteCategory(@RequestBody @Validated Material material){
        service.addMaterial(material);
    }

    @PutMapping("/waste/update/material")
    public void updateWasteCategory(@RequestBody @Validated Material material){
        int rows = service.updateMaterial(material);

    }
    @DeleteMapping("/waste/material/delete/{id}")
    public void deleteWasteCategory(@PathVariable Integer id){
        service.deleteMaterial(id);
    }
}