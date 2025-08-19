package com.aaseya.ITSM.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "IncidentCase")
public class IncidentCase {

	@Id
	@Column(name = "caseId")
	private String caseId;

	@Column(name = "incidentType")
	private String incidentType;

	@Column(name = "incidentCategory")
	private String incidentCategory;

	@Column(name = "incidentSubCategory")
	private String incidentSubCategory;

	@Column(name = "state")
	private String state;

	@Column(name = "city")
	private String city;

	@Column(name = "branch")
	private String branch;

	@Column(name = "department")
	private String department;

	@Column(name = "block/roomNo")
	private String blockRoomNo;

	@Column(name = "floor")
	private String floor;

	@Column(name = "medicationClass")
	private String medicationClass;

	@Column(name = "incidentDescription")
	private String incidentDescription;

	@Column(name = "incidentPriority")
	private String incidentPriority;

	@Column(name = "creationDate")
	private LocalDate creationDate;

	@Column(name = "creationTime")
	private LocalTime creationTime;

	@Column(name = "incidentTime")
	private LocalTime incidentTime;

	@Column(name = "incidentDate")
	private LocalDate incidentDate;

	@Column(name = "reportedBy")
	private String reportedBy;

	@Column(name = "status")
	private String status;

	@Column(name = "initialActionTaken")
	private String initialActionTaken;

	@Column(name = "incidentManager")
	private String incidentManager;

	@Column(name = "investigator")
	private String investigator;

	@Column(name = "correctiveActions")
	private String correctiveActions;
	@Column(name = "preventiveActions")
	private String preventiveActions;
	@Column(name = "managerComments")
	private String managerComments;

	@Column(name = "finalReviewerComment")
	private String finalReviewerComment;

	@Column(name = "action_required")
	private String actionRequired;

	@ManyToMany(cascade = { CascadeType.MERGE }, fetch = FetchType.EAGER)
	@JoinTable(name = "incident_case_staff", joinColumns = @JoinColumn(name = "caseId"), inverseJoinColumns = @JoinColumn(name = "staffId"))
	private List<StaffDetails> staffDetails;

	@OneToMany(mappedBy = "incidentCase", fetch = FetchType.EAGER)
	@JsonManagedReference
	private List<AssessmentDetails> assessmentDetails;

	@ManyToOne
	@JsonBackReference
//	@JoinColumn(name = "patientId", referencedColumnName = "patientId") 																								// column
	private PatientDetails patientDetails;

	@OneToMany(mappedBy = "incidentCase", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Incident_Attachment> attachments;
	
	@OneToMany(mappedBy = "incidentCase", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<IncidentReport> incidentReports;  

	public String getCaseId() {
		return caseId;
	}

	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}

	public List<IncidentReport> getIncidentReports() {
		return incidentReports;
	}

	public void setIncidentReports(List<IncidentReport> incidentReports) {
		this.incidentReports = incidentReports;
	}

	public String getIncidentType() {
		return incidentType;
	}

	public void setIncidentType(String incidentType) {
		this.incidentType = incidentType;
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

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getBlockRoomNo() {
		return blockRoomNo;
	}

	public void setBlockRoomNo(String blockRoomNo) {
		this.blockRoomNo = blockRoomNo;
	}

	public String getFloor() {
		return floor;
	}

	public void setFloor(String floor) {
		this.floor = floor;
	}

	public String getMedicationClass() {
		return medicationClass;
	}

	public void setMedicationClass(String medicationClass) {
		this.medicationClass = medicationClass;
	}

	public String getIncidentDescription() {
		return incidentDescription;
	}

	public void setIncidentDescription(String incidentDescription) {
		this.incidentDescription = incidentDescription;
	}

	public String getIncidentPriority() {
		return incidentPriority;
	}

	public void setIncidentPriority(String incidentPriority) {
		this.incidentPriority = incidentPriority;
	}

	public LocalDate getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(LocalDate creationDate) {
		this.creationDate = creationDate;
	}

	public LocalTime getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(LocalTime creationTime) {
		this.creationTime = creationTime;
	}

	public LocalTime getIncidentTime() {
		return incidentTime;
	}

	public void setIncidentTime(LocalTime incidentTime) {
		this.incidentTime = incidentTime;
	}

	public LocalDate getIncidentDate() {
		return incidentDate;
	}

	public void setIncidentDate(LocalDate incidentDate) {
		this.incidentDate = incidentDate;
	}

	public String getReportedBy() {
		return reportedBy;
	}

	public void setReportedBy(String reportedBy) {
		this.reportedBy = reportedBy;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getInitialActionTaken() {
		return initialActionTaken;
	}

	public void setInitialActionTaken(String initialActionTaken) {
		this.initialActionTaken = initialActionTaken;
	}

	public String getIncidentManager() {
		return incidentManager;
	}

	public void setIncidentManager(String incidentManager) {
		this.incidentManager = incidentManager;
	}

	public String getInvestigator() {
		return investigator;
	}

	public void setInvestigator(String investigator) {
		this.investigator = investigator;
	}

	public String getCorrectiveActions() {
		return correctiveActions;
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

	public String getManagerComments() {
		return managerComments;
	}

	public void setManagerComments(String managerComments) {
		this.managerComments = managerComments;
	}

	public List<StaffDetails> getStaffDetails() {
		return staffDetails;
	}

	public void setStaffDetails(List<StaffDetails> staffDetails) {
		this.staffDetails = staffDetails;
	}

	public PatientDetails getPatientDetails() {
		return patientDetails;
	}

	public void setPatientDetails(PatientDetails patientDetails) {
		this.patientDetails = patientDetails;
	}

	public List<Incident_Attachment> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<Incident_Attachment> attachments) {
		this.attachments = attachments;
	}

	public String getFinalReviewerComment() {
		return finalReviewerComment;
	}

	public void setFinalReviewerComment(String finalReviewerComment) {
		this.finalReviewerComment = finalReviewerComment;
	}

	public List<AssessmentDetails> getAssessmentDetails() {
		return assessmentDetails;
	}

	public void setAssessmentDetails(List<AssessmentDetails> assessmentDetails) {
		this.assessmentDetails = assessmentDetails;
	}

	public String getActionRequired() {
		return actionRequired;
	}

	public void setActionRequired(String actionRequired) {
		this.actionRequired = actionRequired;
	}

	@Override
	public String toString() {
		return "IncidentCase [caseId=" + caseId + ", incidentType=" + incidentType + ", incidentCategory="
				+ incidentCategory + ", incidentSubCategory=" + incidentSubCategory + ", state=" + state + ", city="
				+ city + ", branch=" + branch + ", department=" + department + ", blockRoomNo=" + blockRoomNo
				+ ", floor=" + floor + ", medicationClass=" + medicationClass + ", incidentDescription="
				+ incidentDescription + ", incidentPriority=" + incidentPriority + ", creationDate=" + creationDate
				+ ", creationTime=" + creationTime + ", incidentTime=" + incidentTime + ", incidentDate=" + incidentDate
				+ ", reportedBy=" + reportedBy + ", status=" + status + ", initialActionTaken=" + initialActionTaken
				+ ", incidentManager=" + incidentManager + ", investigator=" + investigator + ", correctiveActions="
				+ correctiveActions + ", preventiveActions=" + preventiveActions + ", managerComments="
				+ managerComments + ", finalReviewerComment=" + finalReviewerComment + ", actionRequired="
				+ actionRequired + ", staffDetails=" + staffDetails + ", assessmentDetails=" + assessmentDetails
				+ ", patientDetails=" + patientDetails + ", attachments=" + attachments + ", incidentReports="
				+ incidentReports + "]";
	}

}
