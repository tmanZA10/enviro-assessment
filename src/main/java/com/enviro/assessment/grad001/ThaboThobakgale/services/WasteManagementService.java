package com.enviro.assessment.grad001.ThaboThobakgale.services;

import com.enviro.assessment.grad001.ThaboThobakgale.repository.WasteManagementRepository;
import org.springframework.stereotype.Service;

@Service
public class WasteManagementService {

    private final WasteManagementRepository repository;

    public WasteManagementService(WasteManagementRepository repository) {
        this.repository = repository;
    }
}
