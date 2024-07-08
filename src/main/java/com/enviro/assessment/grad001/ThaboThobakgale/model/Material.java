package com.enviro.assessment.grad001.ThaboThobakgale.model;

import jakarta.validation.constraints.NotNull;

import java.util.Objects;

public class Material {

    @NotNull
    private int id;
    @NotNull
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Material material = (Material) o;
        return id == material.id &&
                name.equals(material.name) &&
                waste_category.equals(material.waste_category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, waste_category);
    }

    @Override
    public String toString() {
        return "Material{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", waste_category='" + waste_category + '\'' +
                '}';
    }
}
