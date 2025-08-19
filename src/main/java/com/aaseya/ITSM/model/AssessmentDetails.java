package com.aaseya.ITSM.model;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
@Entity
@Table(name = "AssessmentDetails")
public class AssessmentDetails {
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	@Column(name = "assessmentId")
	private int assessmentId;
	
	@Column(name = "assessmentType")
	private String assessmentType;
	
	@Column(name = "assessmentDate")
	private LocalDate assessmentDate;
	
	@Column(name = "bloodPressure")
	private String bloodPressure;
	
	@Column(name = "heartRate")
	private String heartRate;
	
	@Column(name = "temperature")
	private String temperature;
	
	@Column(name = "respiratoryRate")
	private String respiratoryRate;
	
	@Column(name = "oxygenSaturation")
	private String oxygenSaturation;
	
	@Column(name = "levelOfConsciousnes")
	private String levelOfConsciousnes;
	
	@Column(name = "painLevel")
	private Integer painLevel;

	
	@Column(name = "mentalStatus")
    private String mentalStatus;
	
	@Column(name = "mobilityStatus")
    private String mobilityStatus;
	
	@Column(name = "injuriesSustained")
    private String injuriesSustained;
	
	@Column(name = "medications")
    private List<String> medications;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "patientStatus")
	private String patientStatus;
	
	@Column(name = "patientCondition")
    private String patientCondition;  // Added field

    @Column(name = "incidentDescription")
    private String incidentDescription;  // Added field
	
	 @OneToMany(mappedBy = "assessmentDetails", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	    private List<Incident_Attachment> attachments;
	    
	    
	    @ManyToOne
	    @JsonBackReference
//	    @JoinColumn(name = "caseId", nullable = false)
	    @JoinColumn(name = "incident_case_case_id", referencedColumnName = "caseId", nullable = false)
	    private IncidentCase incidentCase;


		public int getAssessmentId() {
			return assessmentId;
		}


		public void setAssessmentId(int assessmentId) {
			this.assessmentId = assessmentId;
		}


		public String getAssessmentType() {
			return assessmentType;
		}


		public void setAssessmentType(String assessmentType) {
			this.assessmentType = assessmentType;
		}


		public Integer getPainLevel() {
			return painLevel;
		}


		public void setPainLevel(Integer painLevel) {
			this.painLevel = painLevel;
		}


		public LocalDate getAssessmentDate() {
			return assessmentDate;
		}


		public void setAssessmentDate(LocalDate assessmentDate) {
			this.assessmentDate = assessmentDate;
		}


		public String getBloodPressure() {
			return bloodPressure;
		}


		public void setBloodPressure(String bloodPressure) {
			this.bloodPressure = bloodPressure;
		}


		public String getHeartRate() {
			return heartRate;
		}


		public void setHeartRate(String heartRate) {
			this.heartRate = heartRate;
		}


		public String getTemperature() {
			return temperature;
		}


		public void setTemperature(String temperature) {
			this.temperature = temperature;
		}


		public String getRespiratoryRate() {
			return respiratoryRate;
		}


		public void setRespiratoryRate(String respiratoryRate) {
			this.respiratoryRate = respiratoryRate;
		}


		public String getOxygenSaturation() {
			return oxygenSaturation;
		}


		public void setOxygenSaturation(String oxygenSaturation) {
			this.oxygenSaturation = oxygenSaturation;
		}


		public String getLevelOfConsciousnes() {
			return levelOfConsciousnes;
		}


		public void setLevelOfConsciousnes(String levelOfConsciousnes) {
			this.levelOfConsciousnes = levelOfConsciousnes;
		}


	

	


		


		public String getMentalStatus() {
			return mentalStatus;
		}


		public void setMentalStatus(String mentalStatus) {
			this.mentalStatus = mentalStatus;
		}


		public String getMobilityStatus() {
			return mobilityStatus;
		}


		public void setMobilityStatus(String mobilityStatus) {
			this.mobilityStatus = mobilityStatus;
		}


		public String getInjuriesSustained() {
			return injuriesSustained;
		}


		public void setInjuriesSustained(String injuriesSustained) {
			this.injuriesSustained = injuriesSustained;
		}


		public List<String> getMedications() {
			return medications;
		}


		public void setMedications(List<String> medications) {
			this.medications = medications;
		}


		public String getDescription() {
			return description;
		}


		public void setDescription(String description) {
			this.description = description;
		}


		public String getPatientStatus() {
			return patientStatus;
		}


		public void setPatientStatus(String patientStatus) {
			this.patientStatus = patientStatus;
		}


		public List<Incident_Attachment> getAttachments() {
			return attachments;
		}


		public void setAttachments(List<Incident_Attachment> attachments) {
			this.attachments = attachments;
		}


		public IncidentCase getIncidentCase() {
			return incidentCase;
		}


		public void setIncidentCase(IncidentCase incidentCase) {
			this.incidentCase = incidentCase;
		}


		public String getPatientCondition() {
			return patientCondition;
		}


		public void setPatientCondition(String patientCondition) {
			this.patientCondition = patientCondition;
		}


		public String getIncidentDescription() {
			return incidentDescription;
		}
		

		public void setIncidentDescription(String incidentDescription) {
			this.incidentDescription = incidentDescription;
		}


		@Override
		public String toString() {
			return "AssessmentDetails [assessmentId=" + assessmentId + ", assessmentType=" + assessmentType
					+ ", assessmentDate=" + assessmentDate + ", bloodPressure=" + bloodPressure + ", heartRate="
					+ heartRate + ", temperature=" + temperature + ", respiratoryRate=" + respiratoryRate
					+ ", oxygenSaturation=" + oxygenSaturation + ", levelOfConsciousnes=" + levelOfConsciousnes
					+ ", painLevel=" + painLevel + ", mentalStatus=" + mentalStatus + ", mobilityStatus="
					+ mobilityStatus + ", injuriesSustained=" + injuriesSustained + ", medications=" + medications
					+ ", description=" + description + ", patientStatus=" + patientStatus + ", patientCondition="
					+ patientCondition + ", incidentDescription=" + incidentDescription + ", attachments=" + attachments
					+ ", incidentCase=" + incidentCase + "]";
		}


		
}