package com.example.MultiPlatform.entities;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;


@Entity
@Data
public class Fields {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String type; // E.g., text, number, date

    @ManyToOne
    @JoinColumn(name = "stage_id")
    private Stages stage;
    private String value;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Fields field = (Fields) o;
        return id != null && id.equals(field.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}