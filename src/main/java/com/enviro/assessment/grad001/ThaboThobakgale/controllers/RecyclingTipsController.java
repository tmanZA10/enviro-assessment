package com.enviro.assessment.grad001.ThaboThobakgale.controllers;

import com.enviro.assessment.grad001.ThaboThobakgale.model.RecyclingTip;
import com.enviro.assessment.grad001.ThaboThobakgale.services.RecyclingService;
import jakarta.validation.Valid;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.IncorrectUpdateSemanticsDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;


@RestController
public class RecyclingTipsController {

    private final RecyclingService service;

    public RecyclingTipsController(RecyclingService service) {
        this.service = service;
    }

    @GetMapping("/recyclingtips")
    public Iterable<RecyclingTip> getTips(){
        return service.getTips();
    }

    @GetMapping("/recyclingtip/{id}")
    public RecyclingTip getTips(@PathVariable Integer id){
        Optional<RecyclingTip> tipOptional = service.getTip(id);
        if(tipOptional.isEmpty()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Tip id not found.");
        return tipOptional.get();
    }

    @PostMapping("/new/recyclingtip")
    public void addNewTip(@RequestBody @Validated RecyclingTip tip){
        service.addTip(tip);
    }

    @PutMapping("/update/recyclingtip")
    public void updateTip(@RequestBody RecyclingTip tip){
        try{
            service.updateTip(tip);
        }catch (IncorrectUpdateSemanticsDataAccessException x){
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST, "tip id not found");
        }
    }

    @DeleteMapping("/delete/recyclingtip/{id}")
    public void deleteTip(@PathVariable Integer id){

        service.deleteTip(id);

    }
}
