package com.aaseya.ITSM.dto;

import java.time.LocalDate;
import java.time.LocalTime;


public class IncidentCaseDTODashboard {

    private String caseId;
    private String incidentType;
    private String incidentCategory;
    private String incidentSubCategory;
    private String incidentPriority;
    private LocalDate creationDate;
    private LocalTime creationTime;
    private String reportedBy;
    private String status;
    private String incidentManager;
    private String correctiveActions;
    private String preventiveActions;
    private String assessmentType; // New field

    // Getters and Setters
    // Existing fields...
    
    public String getCorrectiveActions() {
        return correctiveActions;
    }

    public String getCaseId() {
		return caseId;
	}

	public String getIncidentType() {
		return incidentType;
	}

	public String getIncidentCategory() {
		return incidentCategory;
	}

	public String getIncidentSubCategory() {
		return incidentSubCategory;
	}

	public String getIncidentPriority() {
		return incidentPriority;
	}

	public LocalDate getCreationDate() {
		return creationDate;
	}

	public LocalTime getCreationTime() {
		return creationTime;
	}

	public String getReportedBy() {
		return reportedBy;
	}

	public String getStatus() {
		return status;
	}

	public String getIncidentManager() {
		return incidentManager;
	}

	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}

	public void setIncidentType(String incidentType) {
		this.incidentType = incidentType;
	}

	public void setIncidentCategory(String incidentCategory) {
		this.incidentCategory = incidentCategory;
	}

	public void setIncidentSubCategory(String incidentSubCategory) {
		this.incidentSubCategory = incidentSubCategory;
	}

	public void setIncidentPriority(String incidentPriority) {
		this.incidentPriority = incidentPriority;
	}

	public void setCreationDate(LocalDate creationDate) {
		this.creationDate = creationDate;
	}

	public void setCreationTime(LocalTime creationTime) {
		this.creationTime = creationTime;
	}

	public void setReportedBy(String reportedBy) {
		this.reportedBy = reportedBy;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setIncidentManager(String incidentManager) {
		this.incidentManager = incidentManager;
	}

	public void setCorrectiveActions(String correctiveActions) {
        this.correctiveActions = correctiveActions;
    }

    public String getPreventiveActions() {
        return preventiveActions;
    }

    public void setPreventiveActions(String preventiveActions) {
        this.preventiveActions = preventiveActions;
    }

    public String getAssessmentType() {
        return assessmentType;
    }

    public void setAssessmentType(String assessmentType) {
        this.assessmentType = assessmentType;
    }

    @Override
	public String toString() {
		return "IncidentCaseDTODashboard [caseId=" + caseId + ", incidentType=" + incidentType + ", incidentCategory="
				+ incidentCategory + ", incidentSubCategory=" + incidentSubCategory + ", incidentPriority="
				+ incidentPriority + ", creationDate=" + creationDate + ", creationTime=" + creationTime
				+ ", reportedBy=" + reportedBy + ", status=" + status + ", incidentManager=" + incidentManager
				+ ", correctiveActions=" + correctiveActions + ", preventiveActions=" + preventiveActions
				+ ", assessmentType=" + assessmentType + "]";
	}

	
}

		

