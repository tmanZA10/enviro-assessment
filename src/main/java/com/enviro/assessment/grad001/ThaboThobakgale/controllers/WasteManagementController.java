package com.enviro.assessment.grad001.ThaboThobakgale.controllers;

import com.enviro.assessment.grad001.ThaboThobakgale.services.WasteManagementService;
import org.springframework.web.bind.annotation.RestController;

@RestController
class WasteManagementController {

    private final WasteManagementService service;

    public WasteManagementController(WasteManagementService service) {
        this.service = service;
    }
}
