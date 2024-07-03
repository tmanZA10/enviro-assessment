package com.enviro.assessment.grad001.ThaboThobakgale.model;

public class Material {

    private int id;
    private String name, waste_category;

    public Material(int id, String name, String waste_category) {
        this.id = id;
        this.name = name;
        this.waste_category = waste_category;
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

    public String getWaste_category() {
        return waste_category;
    }

    public void setWaste_category(String waste_category) {
        this.waste_category = waste_category;
    }
}
