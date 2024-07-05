package com.enviro.assessment.grad001.ThaboThobakgale.repository.mappers;

import com.enviro.assessment.grad001.ThaboThobakgale.model.RecyclingTip;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RecyclingTipMapper implements RowMapper<RecyclingTip> {
    @Override
    public RecyclingTip mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new RecyclingTip(rs.getInt(1),rs.getString(2));
    }
}
