package com.aaseya.ITSM.dto;

import java.util.List;

public class IncidentCommentAttachmentDTO {
	private String caseId;
	private List<String> attachments;
	private String attachmentTypes;
	private String finalReviewerComment;

	public String getCaseId() {
		return caseId;
	}

	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}

	public List<String> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<String> attachments) {
		this.attachments = attachments;
	}

	public String getAttachmentTypes() {
		return attachmentTypes;
	}

	public void setAttachmentTypes(String attachmentTypes) {
		this.attachmentTypes = attachmentTypes;
	}

	public String getFinalReviewerComment() {
		return finalReviewerComment;
	}

	public void setFinalReviewerComment(String finalReviewerComment) {
		this.finalReviewerComment = finalReviewerComment;
	}
}
