package com.enviro.assessment.grad001.ThaboThobakgale.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("RECYCLINGTIPS")
public class RecyclingTip {

    @Id
    private int id;
    @NotBlank
    @NotNull
    private String tip;

    public RecyclingTip(int id, String tip) {
        this.id = id;
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
}
