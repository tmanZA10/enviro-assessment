package com.enviro.assessment.grad001.ThaboThobakgale.repository.mappers;

import com.enviro.assessment.grad001.ThaboThobakgale.model.Material;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MaterialMapper implements RowMapper<Material> {

    @Override
    public Material mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Material(
                rs.getInt(1),
                rs.getString(3),
                rs.getString(2)
        );
    }
}
