//package com.aaseya.ITSM.model;
//
//import java.util.Arrays;
//
//import jakarta.persistence.Column;
//import jakarta.persistence.Entity;
//import jakarta.persistence.FetchType;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import jakarta.persistence.JoinColumn;
//import jakarta.persistence.ManyToOne;
//import jakarta.persistence.Table;
//
//@Entity
//@Table(name = "EvidenceAttachment")
//public class EvidenceAttachment {
//	
//	
//	 
//	   
//	 
//		@Id
//	    @GeneratedValue(strategy = GenerationType.IDENTITY)
//	    private Long attachmentId;
//	 
//	   
//	    @Column(name = "attachment")
//	    private byte[] attachment;
//	    
//	    @Column(name = "attachmentType")
//	    private String attachmentType;
//	 
//	    @ManyToOne(fetch = FetchType.LAZY)
//	    @JoinColumn(name = "caseId", nullable = false)
//	    private IncidentCase incidentCase;
//
//		public Long getAttachmentId() {
//			return attachmentId;
//		}
//
//		public void setAttachmentId(Long attachmentId) {
//			this.attachmentId = attachmentId;
//		}
//
//		public byte[] getEvidenceAttachment() {
//			return evidenceAttachment;
//		}
//
//		public void setEvidenceAttachment(byte[] evidenceAttachment) {
//			this.evidenceAttachment = evidenceAttachment;
//		}
//
//		public IncidentCase getIncidentCase() {
//			return incidentCase;
//		}
//
//		public void setIncidentCase(IncidentCase incidentCase) {
//			this.incidentCase = incidentCase;
//		}
//
//		@Override
//		public String toString() {
//		    return "EvidenceAttachment [attachmentId=" + attachmentId + ", attachmentSize=" + (evidenceAttachment != null ? evidenceAttachment.length : 0) + " bytes]";
//		}
//
//
//}
