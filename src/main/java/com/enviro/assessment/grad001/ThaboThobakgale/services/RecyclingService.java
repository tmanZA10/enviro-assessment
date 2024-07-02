package com.enviro.assessment.grad001.ThaboThobakgale.services;

import com.enviro.assessment.grad001.ThaboThobakgale.model.RecyclingTip;
import com.enviro.assessment.grad001.ThaboThobakgale.repository.RecyclingTipsRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.IncorrectUpdateSemanticsDataAccessException;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

    public void updateTip(RecyclingTip tip) throws IncorrectUpdateSemanticsDataAccessException{
        try{
            repository.save(tip);
        } catch (Exception e){
            throw new IncorrectUpdateSemanticsDataAccessException("Could not find record in table");
        }
    }

    public void deleteTip(int id){
        repository.deleteById(id);
    }

    public Optional<RecyclingTip> getTip(int id){
       return repository.findById(id);
    }
}
