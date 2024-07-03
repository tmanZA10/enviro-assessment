package com.enviro.assessment.grad001.ThaboThobakgale.model;

public class Guideline {
    private int id;
    private String material, guideline;

    public Guideline(int id, String material, String guideline) {
        this.id = id;
        this.material = material;
        this.guideline = guideline;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getGuideline() {
        return guideline;
    }

    public void setGuideline(String guideline) {
        this.guideline = guideline;
    }
}
