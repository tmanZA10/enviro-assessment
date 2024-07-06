package com.enviro.assessment.grad001.ThaboThobakgale.controllers;

import com.enviro.assessment.grad001.ThaboThobakgale.model.RecyclingTip;
import com.enviro.assessment.grad001.ThaboThobakgale.model.WithId;
import com.enviro.assessment.grad001.ThaboThobakgale.model.WithoutId;
import com.enviro.assessment.grad001.ThaboThobakgale.services.RecyclingService;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.Valid;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.IncorrectUpdateSemanticsDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;


@RestController
public class RecyclingTipsController {

    private final RecyclingService service;

    public RecyclingTipsController(RecyclingService service) {
        this.service = service;
    }

    @GetMapping("/recyclingtips")
    public List<RecyclingTip> getTips(){
        return service.getTips();
    }

    @GetMapping("/recyclingtip/{id}")
    @JsonView(WithoutId.class)
    public RecyclingTip getTips(@PathVariable Integer id){
        if(id <= 0) throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Tip id cannot be less than 1");
        Optional<RecyclingTip> tipOptional = service.getTip(id);
        if(tipOptional.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Tip id not found.");
        return tipOptional.get();
    }

    @PostMapping("/new/recyclingtip")
    @JsonView(WithId.class)
    public RecyclingTip  addNewTip(@RequestBody @Validated RecyclingTip tip){
        service.addTip(tip);
        return service.getByTip(tip.getTip());
    }

    @PutMapping("/update/recyclingtip")
    public RecyclingTip updateTip(@RequestBody @Validated RecyclingTip tip){
        int lines = service.updateTip(tip);
        if (lines == 0) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        return tip;
    }

    @DeleteMapping("/delete/recyclingtip/{id}")
    public void deleteTip(@PathVariable Integer id){

        int lines = service.deleteTip(id);
        if (lines == 0) throw new ResponseStatusException(HttpStatus.NOT_FOUND);

    }
}
