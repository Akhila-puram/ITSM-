package com.aaseya.ITSM.service;

import com.aaseya.ITSM.dao.IncidentCaseDAO;
import com.aaseya.ITSM.dto.IncidentCommentAttachmentDTO;
import com.aaseya.ITSM.model.IncidentCase;
import com.aaseya.ITSM.model.Incident_Attachment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
public class IncidentCommentAttachmentService {

    @Autowired
    private IncidentCaseDAO incidentCaseDAO;

    @Transactional
    public boolean addIncidentCommentAndAttachments(IncidentCommentAttachmentDTO commentAttachmentDTO) {
        try {
            // Find the incident case using the caseId from the DTO
            IncidentCase incidentCase = incidentCaseDAO.getCaseById(commentAttachmentDTO.getCaseId());

            if (incidentCase == null) {
                throw new RuntimeException("Incident case not found for caseId: " + commentAttachmentDTO.getCaseId());
            }

            // Create a list to hold the attachments
            List<Incident_Attachment> attachmentList = new ArrayList<>();
            if (commentAttachmentDTO.getAttachments() != null && !commentAttachmentDTO.getAttachments().isEmpty()) {
                for (String base64Attachment : commentAttachmentDTO.getAttachments()) {
                    // Decode the base64 string to byte array
                    byte[] attachmentData = Base64.getDecoder().decode(base64Attachment);

                    // Create a new Incident_Attachment object
                    Incident_Attachment incidentAttachment = new Incident_Attachment();
                    incidentAttachment.setAttachmentData(attachmentData);
                    incidentAttachment.setIncidentCase(incidentCase);

                    // Set the attachment type if it's not null
                    if (commentAttachmentDTO.getAttachmentTypes() != null) {
                        incidentAttachment.setAttachmentType(commentAttachmentDTO.getAttachmentTypes());
                    }

                    // Add the attachment to the list
                    attachmentList.add(incidentAttachment);
                }
                // Set the list of attachments in the incident case
                incidentCase.setAttachments(attachmentList);
            }

            // Set the final reviewer comment in the incident case
            incidentCase.setFinalReviewerComment(commentAttachmentDTO.getFinalReviewerComment());

            // Save the updated incident case
            incidentCaseDAO.saveIncidentCase(incidentCase);
            return true;

        } catch (Exception e) {
            System.out.println("Error updating incident case: " + e.getMessage());
            return false;
        }
    }
}
