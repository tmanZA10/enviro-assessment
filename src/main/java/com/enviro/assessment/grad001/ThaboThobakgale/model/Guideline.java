package com.enviro.assessment.grad001.ThaboThobakgale.model;

import jakarta.validation.constraints.NotNull;
import org.springframework.lang.NonNull;

import java.util.Objects;

public class Guideline {
    @NotNull
    private int id;
    @NotNull
    private String material;
    @NotNull
    private String guideline;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Guideline guideline1 = (Guideline) o;
        return id == guideline1.id && material.equals(guideline1.material) && guideline.equals(guideline1.guideline);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, material, guideline);
    }
}
