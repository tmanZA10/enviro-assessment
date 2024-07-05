package com.enviro.assessment.grad001.ThaboThobakgale.model;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
public class WasteCategory {
    @NotNull
    @Digits(integer = Integer.MAX_VALUE, fraction = 0)
    private int id;
    @NotNull
    private String name;

    public WasteCategory() {
    }

    public WasteCategory(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
