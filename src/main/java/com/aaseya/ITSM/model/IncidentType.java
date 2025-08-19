package com.aaseya.ITSM.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;

@Entity
@Table(name = "IncidentType")
public class IncidentType {

    @Id
    @Column(name = "incidentTypeId")
    private long incidentTypeId;

    @Column(name = "incidentType")
    private String incidentType;

    @OneToMany(mappedBy = "incidentType", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<IncidentCategory> incidentCategories;

    public long getIncidentTypeId() {
        return incidentTypeId;
    }

    public void setIncidentTypeId(long incidentTypeId) {
        this.incidentTypeId = incidentTypeId;
    }

    public String getIncidentType() {
        return incidentType;
    }

    public void setIncidentType(String incidentType) {
        this.incidentType = incidentType;
    }

    public List<IncidentCategory> getIncidentCategories() {
        return incidentCategories;
    }

    public void setIncidentCategories(List<IncidentCategory> incidentCategories) {
        this.incidentCategories = incidentCategories;
    }

    @Override
    public String toString() {
        return "IncidentType [incidentTypeId=" + incidentTypeId + ", incidentType=" + incidentType + "]";
    }
}
