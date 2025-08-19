package com.aaseya.ITSM.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aaseya.ITSM.dao.IncidentTypeDAO;
import com.aaseya.ITSM.dto.IncidentTypeDTO;
import com.aaseya.ITSM.dto.IncidentCategoryDTO;
import com.aaseya.ITSM.model.IncidentCategory;
import com.aaseya.ITSM.model.IncidentType;

@Service
public class IncidentTypeService {

    @Autowired
    private IncidentTypeDAO incidentTypeDAO;

    public IncidentTypeDTO getIncidentTypeWithCategories(String incidentTypeName) {
        List<IncidentCategory> categories = incidentTypeDAO.getIncidentCategoriesByIncidentType(incidentTypeName);

        // Map the categories to a list of strings (just the incident category name)
        List<String> categoryNames = categories.stream()
            .map(IncidentCategory::getIncidentCategory)
            .collect(Collectors.toList());

        // Prepare the DTO
        IncidentTypeDTO dto = new IncidentTypeDTO();
        dto.setIncidentCategories(categoryNames);

        return dto;
    }
}