package com.enviro.assessment.grad001.ThaboThobakgale.controllers;

import com.enviro.assessment.grad001.ThaboThobakgale.model.RecyclingTip;
import com.enviro.assessment.grad001.ThaboThobakgale.services.RecyclingService;
import org.springframework.web.bind.annotation.*;


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
        return service.getTip(id);
    }

    @PostMapping("/new/recyclingtip")
    public void addNewTip(@RequestBody RecyclingTip tip){
//        System.out.println(tip.getId());
//        System.out.println(tip.getTip());
        service.addTip(tip);
    }

    @PutMapping("/update/recyclingtip")
    public void updateTip(@RequestBody RecyclingTip tip){
//        System.out.println(tip.getId()+">>>>"+tip.getTip());
        service.updateTip(tip);
    }

    @DeleteMapping("/delete/recyclingtip/{id}")
    public void deleteTip(@PathVariable Integer id){

        service.deleteTip(id);

    }
}
