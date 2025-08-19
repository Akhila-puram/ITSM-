package com.aaseya.ITSM.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import com.aaseya.ITSM.model.AssessmentDetails;
import com.aaseya.ITSM.model.PatientDetails;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;

public class IncidentCaseDTO {

	private String caseId;
	private String location;
	private String incidentCategory;
	private String incidentSubCategory;
	private String incidentPriority;
	private String status;
	private String incidentManager;
	private String investigator;
	private String incidentType;
	private String patientId;
	private String state;
	private String city;
	private String branch;
	private String department;
	private String name;
	private int age;
	private String reportedBy;
	private String gender;
	private String contactNo;
	private String familyContactNo;
	private String medicationClass;
	private String incidentDescription;
	private String blockRoomNo;
	private String floor;
	private String block;
	private String roomNo;
	
	private String correctiveActions;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private LocalDate creationDate;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
	private LocalTime incidentTime;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
	private LocalTime creationTime;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private LocalDate incidentDate;
	private String initialActionTaken;
	private List<String> attachments;
	 private List<String> staffIds;
	private String managerComments;
	private String preventiveActions;
	private List<AssessmentDetails> assesmentDetails;
	private PatientDetailsDTO patientDetails;
	
	public String getBlock() {
		return block;
	}
	public void setBlock(String block) {
		this.block = block;
	}
	public String getRoomNo() {
		return roomNo;
	}
	public void setRoomNo(String roomNo) {
		this.roomNo = roomNo;
	}
	public List<AssessmentDetails> getAssesmentDetails() {
		return assesmentDetails;
	}
	public void setAssesmentDetails(List<AssessmentDetails> assesmentDetails) {
		this.assesmentDetails = assesmentDetails;
	}
	public PatientDetailsDTO getPatientDetails() {
		return patientDetails;
	}
	public void setPatientDetails(PatientDetailsDTO patientDetails) {
		this.patientDetails = patientDetails;
	}
	public String getCaseId() {
		return caseId;
	}
	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}
	public String getLocation() {
		return location;
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
	public String getIncidentType() {
		return incidentType;
	}
	public void setIncidentType(String incidentType) {
		this.incidentType = incidentType;
	}
	public String getPatientId() {
		return patientId;
	}
	public void setPatientId(String patientId) {
		this.patientId = patientId;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getReportedBy() {
		return reportedBy;
	}
	public void setReportedBy(String reportedBy) {
		this.reportedBy = reportedBy;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getContactNo() {
		return contactNo;
	}
	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}
	public String getFamilyContactNo() {
		return familyContactNo;
	}
	public void setFamilyContactNo(String familyContactNo) {
		this.familyContactNo = familyContactNo;
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
	public String getCorrectiveActions() {
		return correctiveActions;
	}
	public void setCorrectiveActions(String correctiveActions) {
		this.correctiveActions = correctiveActions;
	}
	public LocalDate getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(LocalDate creationDate) {
		this.creationDate = creationDate;
	}
	public LocalTime getIncidentTime() {
		return incidentTime;
	}
	public void setIncidentTime(LocalTime incidentTime) {
		this.incidentTime = incidentTime;
	}
	public LocalTime getCreationTime() {
		return creationTime;
	}
	public void setCreationTime(LocalTime creationTime) {
		this.creationTime = creationTime;
	}
	public LocalDate getIncidentDate() {
		return incidentDate;
	}
	public void setIncidentDate(LocalDate incidentDate) {
		this.incidentDate = incidentDate;
	}
	public String getInitialActionTaken() {
		return initialActionTaken;
	}
	public void setInitialActionTaken(String initialActionTaken) {
		this.initialActionTaken = initialActionTaken;
	}
	public List<String> getAttachments() {
		return attachments;
	}
	public void setAttachments(List<String> attachments) {
		this.attachments = attachments;
	}
	public List<String> getStaffIds() {
		return staffIds;
	}
	public void setStaffIds(List<String> staffIds) {
		this.staffIds = staffIds;
	}
	public String getManagerComments() {
		return managerComments;
	}
	public void setManagerComments(String managerComments) {
		this.managerComments = managerComments;
	}
	public String getPreventiveActions() {
		return preventiveActions;
	}
	public void setPreventiveActions(String preventiveActions) {
		this.preventiveActions = preventiveActions;
	}
	public void setPatientDetailsDTO(PatientDetails patientDetails) {
		if (patientDetails != null) {
	        PatientDetailsDTO dto = new PatientDetailsDTO();
	        dto.setPatientId(patientDetails.getPatientId());
	        dto.setName(patientDetails.getName());
	        dto.setAge(patientDetails.getAge());
	        dto.setGender(patientDetails.getGender());
	        dto.setContactNo(patientDetails.getContactNo());
	        dto.setFamilyContactNo(patientDetails.getFamilyContactNo());
	        this.patientDetails = dto;
	    }
	
}
}
