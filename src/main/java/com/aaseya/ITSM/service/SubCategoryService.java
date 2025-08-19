package com.aaseya.ITSM.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aaseya.ITSM.dao.SubCategoryDAO;
import com.aaseya.ITSM.model.IncidentSubCategory;

@Service
public class SubCategoryService {

	@Autowired
	private SubCategoryDAO subCategoryDAO;

	public List<String> getIncidentSubCategoriesByIncidentCategory(String incidentCategory) {

		List<IncidentSubCategory> incidentSubCategories = subCategoryDAO.getIncidentSubCategories(incidentCategory);

		List<String> subCategoryNames = new ArrayList<>();

		for (IncidentSubCategory incidentSubCategory : incidentSubCategories) {
			subCategoryNames.add(incidentSubCategory.getIncidentSubCategory());
		}

		return subCategoryNames;
	}
}
