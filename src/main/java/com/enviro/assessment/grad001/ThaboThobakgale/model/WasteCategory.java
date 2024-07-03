package com.enviro.assessment.grad001.ThaboThobakgale.model;

public class WasteCategory {
    private int id;
    private String name;

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
