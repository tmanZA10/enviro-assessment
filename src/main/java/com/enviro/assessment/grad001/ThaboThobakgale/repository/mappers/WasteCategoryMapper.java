package com.enviro.assessment.grad001.ThaboThobakgale.repository.mappers;

import com.enviro.assessment.grad001.ThaboThobakgale.model.WasteCategory;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class WasteCategoryMapper implements RowMapper<WasteCategory> {
    @Override
    public WasteCategory mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new WasteCategory(rs.getInt(1), rs.getString(2));
    }
}
