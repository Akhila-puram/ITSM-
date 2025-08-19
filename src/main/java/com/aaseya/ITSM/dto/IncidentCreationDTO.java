package com.aaseya.ITSM.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;

public class IncidentCreationDTO {

	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate creationDate;

	@JsonDeserialize(using = LocalTimeDeserializer.class)
	@JsonSerialize(using = LocalTimeSerializer.class)
	private LocalTime creationTime;

	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate incidentDate;

	@JsonDeserialize(using = LocalTimeDeserializer.class)
	@JsonSerialize(using = LocalTimeSerializer.class)
	private LocalTime incidentTime;
	private String incidentType;
	private String incidentCategory;
	private String incidentSubCategory;
	private String medicationClass;
	private String reportedBy;
	private String incidentDescription;
	private String initialActionTaken;
	private List<String> communicationChannel;

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

	public LocalDate getIncidentDate() {
		return incidentDate;
	}

	public void setIncidentDate(LocalDate incidentDate) {
		this.incidentDate = incidentDate;
	}

	public LocalTime getIncidentTime() {
		return incidentTime;
	}

	public void setIncidentTime(LocalTime incidentTime) {
		this.incidentTime = incidentTime;
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

	public String getMedicationClass() {
		return medicationClass;
	}

	public void setMedicationClass(String medicationClass) {
		this.medicationClass = medicationClass;
	}

	public String getReportedBy() {
		return reportedBy;
	}

	public void setReportedBy(String reportedBy) {
		this.reportedBy = reportedBy;
	}

	public String getIncidentDescription() {
		return incidentDescription;
	}

	public void setIncidentDescription(String incidentDescription) {
		this.incidentDescription = incidentDescription;
	}

	public String getInitialActionTaken() {
		return initialActionTaken;
	}

	public void setInitialActionTaken(String initialActionTaken) {
		this.initialActionTaken = initialActionTaken;
	}

	public List<String> getCommunicationChannel() {
		return communicationChannel;
	}

	public void setCommunicationChannel(List<String> communicationChannel) {
		this.communicationChannel = communicationChannel;
	}

	public String toFormattedJson() throws JsonProcessingException {
		List<Map<String, String>> nameValueList = new ArrayList<>();
		ObjectMapper objectMapper = new ObjectMapper();
		addToList(nameValueList, "creationDate", String.valueOf(creationDate));
		addToList(nameValueList, "creationTime", String.valueOf(creationTime));
		addToList(nameValueList, "incidentDate", String.valueOf(incidentDate));
		addToList(nameValueList, "incidentType", incidentType);
		// addToList(nameValueList, "contactNo", contactNo);
//		return objectMapper.writeValueAsString(nameValueList);
		String jsonString = objectMapper.writeValueAsString(nameValueList);
		return escapeJsonString(jsonString); // Escape the JSON string

	}

//	private void addToList(List<Map<String, String>> list, String key, String value) throws JsonProcessingException {
//		Map<String, String> map = new HashMap<>();
//		map.put(escapeQuotes("name"), escapeQuotes(key));
//		map.put(escapeQuotes("value"), value != null ? escapeQuotes(value) : null);
//		list.add(map);
//	}
//
//	private String escapeQuotes(String str) {
//		return "\"" + str + "\"";
//
//	}

	private void addToList(List<Map<String, String>> list, String key, String value) throws JsonProcessingException {
		Map<String, String> map = new HashMap<>();
		ObjectMapper objectMapper = new ObjectMapper();
		map.put("name", key);
		map.put("value", value != null ? objectMapper.writeValueAsString(value) : null);
		list.add(map);
	}

	private String escapeJsonString(String jsonString) {
		return jsonString.replace("\"", "\\\"");
		// Escape double quotes
	}

	@Override
	public String toString() {
		return "IncidentCreationDTO [creationDate=" + creationDate + ", creationTime=" + creationTime
				+ ", incidentDate=" + incidentDate + ", incidentTime=" + incidentTime + ", incidentType=" + incidentType
				+ ", incidentCategory=" + incidentCategory + ", incidentSubCategory=" + incidentSubCategory
				+ ", medicationClass=" + medicationClass + ", reportedBy=" + reportedBy + ", incidentDescription="
				+ incidentDescription + ", initialActionTaken=" + initialActionTaken + ", communicationChannel="
				+ communicationChannel + "]";
	}

}
