package com.enviro.assessment.grad001.ThaboThobakgale.services;

import com.enviro.assessment.grad001.ThaboThobakgale.model.RecyclingTip;
import com.enviro.assessment.grad001.ThaboThobakgale.repository.RecyclingTipsRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.IncorrectUpdateSemanticsDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecyclingService {

    private final RecyclingTipsRepository repository;

    public RecyclingService(RecyclingTipsRepository repository) {
        this.repository = repository;

    }

    public List<RecyclingTip> getTips(){
        return repository.getAll();
    }

    public int addTip(RecyclingTip tip){
        try{
            return repository.save(tip);
        }catch (DataAccessException x){
            return 0;
        }
    }

    public int updateTip(RecyclingTip tip){
        return repository.update(tip);
    }

    public int deleteTip(int id){
        return repository.deleteById(id);
    }

    public Optional<RecyclingTip> getTip(int id){
       return repository.findById(id);
    }

    public RecyclingTip getByTip(String tip){
        return repository.getByTip(tip);
    }
}
