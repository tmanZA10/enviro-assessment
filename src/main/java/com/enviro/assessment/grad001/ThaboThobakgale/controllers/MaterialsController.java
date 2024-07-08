package com.enviro.assessment.grad001.ThaboThobakgale.controllers;

import com.enviro.assessment.grad001.ThaboThobakgale.model.Material;
import com.enviro.assessment.grad001.ThaboThobakgale.model.WasteCategory;
import com.enviro.assessment.grad001.ThaboThobakgale.services.WasteManagementService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

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
        Optional<Material> result = service.lookUpMaterial(material);
        if (result.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return result.get();
    }


    @PostMapping("/waste/new/material")
    public Material addWasteCategory(@RequestBody @Validated Material material){
        int lines = service.addMaterial(material);
        if (lines == -1) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        if (lines == 0) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        return service.lookUpMaterial(material.getName()).get();
    }

    @PutMapping("/waste/update/material")
    public Material updateWasteCategory(@RequestBody @Validated Material material){
        if (material.getId() <=0) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        int rows = service.updateMaterial(material);
        if (rows == 0) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        return material;

    }
    @DeleteMapping("/waste/material/delete/{id}")
    public void deleteWasteCategory(@PathVariable Integer id){
        if(id<=0) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        int lines = service.deleteMaterial(id);
        if (lines == 0) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
}