package com.enviro.assessment.grad001.ThaboThobakgale.controllers;

import com.enviro.assessment.grad001.ThaboThobakgale.services.WasteManagementService;
import org.springframework.web.bind.annotation.RestController;

@RestController
class WasteCategoriesController {

    private final WasteManagementService service;

    public WasteCategoriesController(WasteManagementService service) {
        this.service = service;
    }
}
