package com.aaseya.ITSM.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "medication_class")
public class MedicationClass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long medicationClassId;

    @Column(name = "medication_class", nullable = false)
    private String medicationClass;

    // Getters and Setters
    public Long getMedicationId() {
        return medicationClassId;
    }

    public void setMedicationClassId(Long medicationId) {
        this.medicationClassId = medicationId;
    }

    public String getMedicationClass() {
        return medicationClass;
    }

    public void setMedicationClass(String medicationClass) {
        this.medicationClass = medicationClass;
    }
}
