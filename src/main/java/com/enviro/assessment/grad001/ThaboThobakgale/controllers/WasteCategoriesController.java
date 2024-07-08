package com.enviro.assessment.grad001.ThaboThobakgale.controllers;

import com.enviro.assessment.grad001.ThaboThobakgale.model.WasteCategory;
//import com.enviro.assessment.grad001.ThaboThobakgale.model.WasteCategoryNoId;
import com.enviro.assessment.grad001.ThaboThobakgale.model.WithId;
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
    @JsonView(WithId.class)
    public WasteCategory addWasteCategory(@RequestBody @Validated  WasteCategory category){
        int lines = service.addWasteCategory(category);
        if (lines == 0) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        return service.getWasteCategory(category.getName()).get();
    }

    @PutMapping("/waste/update/category")
    @JsonView(WithId.class)
    public WasteCategory updateWasteCategory(@RequestBody @Validated WasteCategory category){
        if (category.getId() <=0) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        int linesUpdated = service.updateWasteCategory(category);
        if (linesUpdated == 0) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        return category;
    }
    @DeleteMapping("/waste/category/delete/{id}")
    public void deleteWasteCategory(@PathVariable Integer id){
        if(id<=0) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        int linesUpdated = service.deleteWasteCategory(id);
        if (linesUpdated == 0) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

}
