package com.enviro.assessment.grad001.ThaboThobakgale.model;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;

public class WasteCategory {
    @NotNull
    @Digits(integer = Integer.MAX_VALUE, fraction = 0)
    @JsonView(WithId.class)
    private int id;
    @NotNull
    @JsonView(WithoutId.class)
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WasteCategory category = (WasteCategory) o;
        return id == category.id && name.equals(category.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
