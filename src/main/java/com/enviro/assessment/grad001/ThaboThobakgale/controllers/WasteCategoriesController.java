package com.enviro.assessment.grad001.ThaboThobakgale.controllers;

import com.enviro.assessment.grad001.ThaboThobakgale.model.WasteCategory;
//import com.enviro.assessment.grad001.ThaboThobakgale.model.WasteCategoryNoId;
import com.enviro.assessment.grad001.ThaboThobakgale.services.WasteManagementService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@RestController
class WasteCategoriesController {

    private final WasteManagementService service;

    public WasteCategoriesController(WasteManagementService service) {
        this.service = service;
    }

//    @JsonView(WasteCategory.WithID.class)
    @GetMapping("/waste/categories")
    public List<WasteCategory> getWasteCategory(){
        return service.getWasteCategories();
    }


    @PostMapping("/waste/new/category")
    public void addWasteCategory(@RequestBody @Validated  WasteCategory category){
        service.addWasteCategory(category);
    }

    @PutMapping("/waste/update/category")
    public WasteCategory updateWasteCategory(@RequestBody @Validated WasteCategory category){
        int linesUpdated = service.updateWasteCategory(category);
        if (category.getId() <=0) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        if (linesUpdated == 0) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return category;
    }
    @DeleteMapping("/waste/category/delete/{id}")
    public void deleteWasteCategory(@PathVariable Integer id){
        int linesUpdated = service.deleteWasteCategory(id);
        if (linesUpdated == 0) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

}
