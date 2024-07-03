package com.enviro.assessment.grad001.ThaboThobakgale.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class WasteManagementRepository {

    private final JdbcTemplate template;

    public WasteManagementRepository(JdbcTemplate template) {
        this.template = template;
    }
}
