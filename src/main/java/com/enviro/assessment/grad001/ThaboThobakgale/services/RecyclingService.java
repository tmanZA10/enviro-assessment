package com.enviro.assessment.grad001.ThaboThobakgale.services;

import com.enviro.assessment.grad001.ThaboThobakgale.model.RecyclingTip;
import com.enviro.assessment.grad001.ThaboThobakgale.repository.RecyclingTipsRepository;
import org.springframework.stereotype.Service;

@Service
public class RecyclingService {

    private final RecyclingTipsRepository repository;

    public RecyclingService(RecyclingTipsRepository repository) {
        this.repository = repository;

    }

    public Iterable<RecyclingTip> getTips(){
        return repository.findAll();
    }

    public void addTip(RecyclingTip tip){
        repository.save(tip);
    }

    public void updateTip(RecyclingTip tip){
        repository.save(tip);
    }

    public void deleteTip(int id){
        repository.deleteById(id);
    }

    public RecyclingTip getTip(int id){
       return repository.findById(id).get();
    }
}
