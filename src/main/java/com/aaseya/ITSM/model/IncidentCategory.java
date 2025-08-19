package com.aaseya.ITSM.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.List;

@Entity
@Table(name = "IncidentCategory")
public class IncidentCategory {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "incidentCategoryId")
	private long incidentCategoryId;

	@Column(name = "incidentCategory")
	private String incidentCategory;

	@ManyToOne
	@JoinColumn(name = "incidentTypeId", nullable = false)
	private IncidentType incidentType;

	@OneToMany(mappedBy = "incidentCategory", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	private List<IncidentSubCategory> incidentSubCategories;

	public long getIncidentCategoryId() {
		return incidentCategoryId;
	}

	public void setIncidentCategoryId(long incidentCategoryId) {
		this.incidentCategoryId = incidentCategoryId;
	}

	public String getIncidentCategory() {
		return incidentCategory;
	}

	public void setIncidentCategory(String incidentCategory) {
		this.incidentCategory = incidentCategory;
	}

	public IncidentType getIncidentType() {
		return incidentType;
	}

	public void setIncidentType(IncidentType incidentType) {
		this.incidentType = incidentType;
	}

	public List<IncidentSubCategory> getIncidentSubCategories() {
		return incidentSubCategories;
	}

	public void setIncidentSubCategories(List<IncidentSubCategory> incidentSubCategories) {
		this.incidentSubCategories = incidentSubCategories;
	}

	@Override
	public String toString() {
		return "IncidentCategory [incidentCategoryId=" + incidentCategoryId + ", incidentCategory=" + incidentCategory
				+ ", incidentType=" + incidentType + ", incidentSubCategories=" + incidentSubCategories + "]";
	}

}
