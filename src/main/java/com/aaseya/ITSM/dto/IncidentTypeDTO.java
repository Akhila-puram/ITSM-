package com.aaseya.ITSM.dto;

import java.util.List;

public class IncidentTypeDTO {

    private List<String> incidentCategories;

    // Getters and setters
    public List<String> getIncidentCategories() {
        return incidentCategories;
    }

    public void setIncidentCategories(List<String> incidentCategories) {
        this.incidentCategories = incidentCategories;
    }
}