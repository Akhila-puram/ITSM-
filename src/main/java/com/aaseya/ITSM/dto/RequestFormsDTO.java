package com.aaseya.ITSM.dto;

import java.util.List;

public class RequestFormsDTO {
	
	private IncidentCreationDTO incidentCreationDTO;
	private PatientDetailsDTO patientDetailsDTO;
	private LocationDetailsDTO locationDetailsDTO;
	
	public IncidentCreationDTO getIncidentCreationDTO() {
		return incidentCreationDTO;
	}
	public LocationDetailsDTO getLocationDetailsDTO() {
		return locationDetailsDTO;
	}
	public void setLocationDetailsDTO(LocationDetailsDTO locationDetailsDTO) {
		this.locationDetailsDTO = locationDetailsDTO;
	}
	public void setIncidentCreationDTO(IncidentCreationDTO incidentCreationDTO) {
		this.incidentCreationDTO = incidentCreationDTO;
	}
	public PatientDetailsDTO getPatientDetailsDTO() {
		return patientDetailsDTO;
	}
	public void setPatientDetailsDTO(PatientDetailsDTO patientDetailsDTO) {
		this.patientDetailsDTO = patientDetailsDTO;
	}
	public LocationDetailsDTO getLocationDTO() {
		return locationDetailsDTO;
	}
	public void setLocationDTO(LocationDetailsDTO locationDTO) {
		this.locationDetailsDTO = locationDTO;
	}
	@Override
	public String toString() {
		return "RequestFormsDTO [incidentCreationDTO=" + incidentCreationDTO + ", patientDetailsDTO="
				+ patientDetailsDTO + ", locationDetailsDTO=" + locationDetailsDTO + "]";
	}
	
	
}
