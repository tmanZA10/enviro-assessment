package com.enviro.assessment.grad001.ThaboThobakgale.controllers;

import com.enviro.assessment.grad001.ThaboThobakgale.services.WasteManagementService;
import org.springframework.web.bind.annotation.RestController;

@RestController
class MaterialsController {

    private final WasteManagementService service;

    public MaterialsController(WasteManagementService service) {
        this.service = service;
    }
}
