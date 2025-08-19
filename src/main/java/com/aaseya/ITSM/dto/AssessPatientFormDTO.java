package com.aaseya.ITSM.dto;

public class AssessPatientFormDTO {
	
	private String bloodPressure;
	private String heartRate;
	private String temperature;
	private String respiratoryRate;
	private String oxygenSaturation;
	private String levelOfConsciousnes;
	private String painLevel;
	private String mentalStatus;
	private String mobilityStatus;
	private Boolean injuriesSustained;
	private String medications;
	private String description;
	private String patientCondition;
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
	public String getPainLevel() {
		return painLevel;
	}
	public void setPainLevel(String painLevel) {
		this.painLevel = painLevel;
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
	public Boolean getInjuriesSustained() {
		return injuriesSustained;
	}
	public void setInjuriesSustained(Boolean injuriesSustained) {
		this.injuriesSustained = injuriesSustained;
	}
	public String getMedications() {
		return medications;
	}
	public void setMedications(String medications) {
		this.medications = medications;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getPatientCondition() {
		return patientCondition;
	}
	public void setPatientCondition(String patientCondition) {
		this.patientCondition = patientCondition;
	}
	@Override
	public String toString() {
		return "AssessPatientFormDTO [bloodPressure=" + bloodPressure + ", heartRate=" + heartRate + ", temperature="
				+ temperature + ", respiratoryRate=" + respiratoryRate + ", oxygenSaturation=" + oxygenSaturation
				+ ", levelOfConsciousnes=" + levelOfConsciousnes + ", painLevel=" + painLevel + ", mentalStatus="
				+ mentalStatus + ", mobilityStatus=" + mobilityStatus + ", injuriesSustained=" + injuriesSustained
				+ ", medications=" + medications + ", description=" + description + ", patientCondition="
				+ patientCondition + "]";
	}
	
	
}
