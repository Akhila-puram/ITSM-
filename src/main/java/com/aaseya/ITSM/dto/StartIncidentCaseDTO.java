package com.aaseya.ITSM.dto;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
public class StartIncidentCaseDTO {
    private String caseId;
    private String location;
    private String incidentCategory;
    private String incidentSubCategory;
    private String incidentPriority;
    private LocalDate date;
    private LocalTime time;
    private String createdBy;
    private String incidentManager;
    private String channel;
    private String incidentType;
    // Add a list to store Base64 encoded strings of attachments
    private List<String> attachments;
    // Getters and Setters
    public String getCaseId() {
        return caseId;
    }
    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }
    public String getLocation() {
        return location;
    }
    public String getChannel() {
		return channel;
	}
 
	public void setChannel(String channel) {
		this.channel = channel;
	}
 
	public void setLocation(String location) {
        this.location = location;
    }
    public String getIncidentCategory() {
        return incidentCategory;
    }
    public void setIncidentCategory(String incidentCategory) {
        this.incidentCategory = incidentCategory;
    }
    public String getIncidentSubCategory() {
        return incidentSubCategory;
    }
    public void setIncidentSubCategory(String incidentSubCategory) {
        this.incidentSubCategory = incidentSubCategory;
    }
    public String getIncidentPriority() {
        return incidentPriority;
    }
    public void setIncidentPriority(String incidentPriority) {
        this.incidentPriority = incidentPriority;
    }
    public LocalDate getDate() {
        return date;
    }
    public void setDate(LocalDate date) {
        this.date = date;
    }
    public LocalTime getTime() {
        return time;
    }
    public void setTime(LocalTime time) {
        this.time = time;
    }
    public String getCreatedBy() {
        return createdBy;
    }
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
    public String getIncidentManager() {
        return incidentManager;
    }
    public void setIncidentManager(String incidentManager) {
        this.incidentManager = incidentManager;
    }
    public List<String> getAttachments() {
        return attachments;
    }
    public void setAttachments(List<String> attachments) {
        this.attachments = attachments;
    }
    public String getIncidentType() {
		return incidentType;
	}
 
	public void setIncidentType(String incidentType) {
		this.incidentType = incidentType;
	}
 
	@Override
	public String toString() {
		return "StartIncidentCaseDTO [caseId=" + caseId + ", location=" + location + ", incidentCategory="
				+ incidentCategory + ", incidentSubCategory=" + incidentSubCategory + ", incidentPriority="
				+ incidentPriority + ", date=" + date + ", time=" + time + ", createdBy=" + createdBy
				+ ", incidentManager=" + incidentManager + ", channel=" + channel + ", incidentType=" + incidentType
				+ ", attachments=" + attachments + "]";
	}
}
 
