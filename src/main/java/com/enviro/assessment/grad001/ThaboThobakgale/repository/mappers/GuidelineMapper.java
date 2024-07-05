package com.enviro.assessment.grad001.ThaboThobakgale.repository.mappers;

import com.enviro.assessment.grad001.ThaboThobakgale.model.Guideline;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GuidelineMapper implements RowMapper<Guideline> {
    @Override
    public Guideline mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Guideline(
                rs.getInt(1),
                rs.getString(2),
                rs.getString(3)

        );
    }
}
