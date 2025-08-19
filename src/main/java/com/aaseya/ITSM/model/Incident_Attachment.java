package com.aaseya.ITSM.model;

import java.util.Arrays;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Incident_Attachment")
public class Incident_Attachment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long attachmentId;

	@Column(name = "attachmentType")
	private String attachmentType;

	@Column(name = "attachment_data")
	private byte[] attachmentData;

	@ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "assessmentId", nullable = false)
	private AssessmentDetails assessmentDetails;

	@ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "caseId", nullable = false)
	private IncidentCase incidentCase;

	public Long getAttachmentId() {
		return attachmentId;
	}

	public void setAttachmentId(Long attachmentId) {
		this.attachmentId = attachmentId;
	}

	public String getAttachmentType() {
		return attachmentType;
	}

	public void setAttachmentType(String attachmentType) {
		this.attachmentType = attachmentType;
	}

	public byte[] getAttachmentData() {
		return attachmentData;
	}

	public void setAttachmentData(byte[] attachmentData) {
		this.attachmentData = attachmentData;
	}

	public AssessmentDetails getAssessmentDetails() {
		return assessmentDetails;
	}



	public void setAssessmentDetails(AssessmentDetails assessmentDetails) {
		this.assessmentDetails = assessmentDetails;
	}


	public IncidentCase getIncidentCase() {
		return incidentCase;
	}

	public void setIncidentCase(IncidentCase incidentCase) {
		this.incidentCase = incidentCase;
	}

	@Override
	public String toString() {
		return "Incident_Attachment [attachmentId=" + attachmentId + ", attachmentType=" + attachmentType
				+ ", attachmentData=" + Arrays.toString(attachmentData) + "]";
	}

}
