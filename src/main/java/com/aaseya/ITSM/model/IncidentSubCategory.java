package com.aaseya.ITSM.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "IncidentSubCategory")
public class IncidentSubCategory {

    @Id
    @Column(name = "incidentSubCategoryId")
    private long incidentSubCategoryId;

    @Column(name = "incidentSubCategory")
    private String incidentSubCategory;

    @ManyToOne
    @JoinColumn(name = "incidentCategoryId", nullable = false)
    private IncidentCategory incidentCategory;

    public long getIncidentSubCategoryId() {
        return incidentSubCategoryId;
    }

    public void setIncidentSubCategoryId(long incidentSubCategoryId) {
        this.incidentSubCategoryId = incidentSubCategoryId;
    }

    public String getIncidentSubCategory() {
        return incidentSubCategory;
    }

    public void setIncidentSubCategory(String incidentSubCategory) {
        this.incidentSubCategory = incidentSubCategory;
    }

    public IncidentCategory getIncidentCategory() {
        return incidentCategory;
    }

    public void setIncidentCategory(IncidentCategory incidentCategory) {
        this.incidentCategory = incidentCategory;
    }

    @Override
    public String toString() {
        return "IncidentSubCategory [incidentSubCategoryId=" + incidentSubCategoryId + ", incidentSubCategory="
                + incidentSubCategory + "]";
    }
}
