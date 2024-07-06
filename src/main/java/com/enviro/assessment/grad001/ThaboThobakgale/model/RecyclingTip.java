package com.enviro.assessment.grad001.ThaboThobakgale.model;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Objects;

public class RecyclingTip {

    @JsonView(WithId.class)
    private int id;
    @NotBlank
    @NotNull
    @JsonView(WithoutId.class)
    private String tip;

    public RecyclingTip(int id, String tip) {
        this.id = id;
        this.tip = tip;
    }

    public RecyclingTip(String tip) {
        this.tip = tip;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    @Override
    public String toString() {
        return "RecyclingTip{" +
                "id=" + id +
                ", tip='" + tip + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecyclingTip tip1 = (RecyclingTip) o;
        return id == tip1.id && tip.equals(tip1.tip);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, tip);
    }
}
