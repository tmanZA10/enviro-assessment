package com.enviro.assessment.grad001.ThaboThobakgale.repository;

import com.enviro.assessment.grad001.ThaboThobakgale.model.RecyclingTip;
import com.enviro.assessment.grad001.ThaboThobakgale.repository.mappers.RecyclingTipMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class RecyclingTipsRepository{

    private JdbcTemplate template;

    public RecyclingTipsRepository(JdbcTemplate template) {
        this.template = template;
    }

    public List<RecyclingTip> getAll(){
        String sql = "SELECT * FROM RECYCLING_TIPS";
        return template.query(sql,new RecyclingTipMapper());
    }

    public int save(RecyclingTip tip){
        String sql = "INSERT INTO RECYCLING_TIPS(TIP) VALUES (?)";
        return template.update(sql,tip.getTip());
    }


    public int deleteById(int id) {
        String sql = "DELETE FROM RECYCLING_TIPS WHERE ID = ?";
        return template.update(sql,id);
    }

    public Optional<RecyclingTip> findById(int id) {
        String sql = "SELECT * FROM RECYCLING_TIPS WHERE ID = ?";
        RecyclingTip tip;
        try {
            tip = template.queryForObject(sql, new RecyclingTipMapper(), id);
            return Optional.of(tip);
        }catch (DataAccessException x){
            return Optional.empty();
        }
    }

    public RecyclingTip getByTip(String tip){
        String sql = "SELECT * FROM RECYCLING_TIPS WHERE TIP = ?";
        return template.queryForObject(sql, new RecyclingTipMapper(),tip);
    }



    public int update(RecyclingTip tip) {
        String sql = "UPDATE RECYCLING_TIPS SET " +
                "TIP = ? WHERE ID = ?";

        return template.update(sql,tip.getTip(),tip.getId());

    }
}
