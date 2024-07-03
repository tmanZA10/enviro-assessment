package com.enviro.assessment.grad001.ThaboThobakgale.controllers;

import com.enviro.assessment.grad001.ThaboThobakgale.services.WasteManagementService;
import org.springframework.web.bind.annotation.RestController;

@RestController
class GuidelinesController {

    private final WasteManagementService service;

    public GuidelinesController(WasteManagementService service) {
        this.service = service;
    }
}
