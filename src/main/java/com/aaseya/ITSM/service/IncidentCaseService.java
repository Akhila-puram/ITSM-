package com.aaseya.ITSM.service;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aaseya.ITSM.dao.IncidentCaseDAO;
import com.aaseya.ITSM.dto.IncidentCaseDTO;
import com.aaseya.ITSM.dto.IncidentCaseDTODashboard;
import com.aaseya.ITSM.dto.IncidentInvestigationDTO;
import com.aaseya.ITSM.model.IncidentCase;
import com.aaseya.ITSM.model.Incident_Attachment;
import com.aaseya.ITSM.model.PatientDetails;
import com.aaseya.ITSM.model.StaffDetails;

import jakarta.mail.Session;
import jakarta.transaction.Transactional;
import jakarta.transaction.Transactional.TxType;

@Service
public class IncidentCaseService {

	@Autowired
	private IncidentCaseDAO incidentCaseDAO;

    @Autowired
    private TaskListService taskListService;
    
    @Autowired
	private OperateService operateService;

//
//
//
//  //Get the incident details by created by user(nurse/doctor)//
//    public List<IncidentCaseDTO> getIncidentCasesByCreatedBy(String createdBy) {
//        List<IncidentCase> incidentCases = incidentCaseDAO.findByCreatedBy(createdBy);
//        return incidentCases.stream()
//                .map(this::convertToDTO)
//                .collect(Collectors.toList());
//    }
//
//    private IncidentCaseDTO convertToDTO(IncidentCase incidentCase) {
//        IncidentCaseDTO dto = new IncidentCaseDTO();
//        dto.setCaseId(incidentCase.getCaseId());
//        dto.setLocation(incidentCase.getLocation());
//        dto.setIncidentCategory(incidentCase.getIncidentCategory());
//        dto.setIncidentSubCategory(incidentCase.getIncidentSubCategory());
//        dto.setIncidentPriority(incidentCase.getIncidentPriority());
//        dto.setCreationDate(incidentCase.getCreationDate());
//        dto.setTime(incidentCase.getCreationTime());
//        dto.setCreatedBy(incidentCase.getCreatedBy());
//        dto.setStatus(incidentCase.getStatus());
//        dto.setIncidentManager(incidentCase.getIncidentManager());
//        dto.setInvestigator(incidentCase.getInvestigator());
//        return dto;
//    }
//  //Get the incident details by created by user(nurse/doctor)//
//    
//  //Add the attachement in incident case//
//    public boolean saveIncidentCase(StartIncidentCaseDTO startIncidentCaseDTO, String businessKey) {
//        boolean result = true;
//        try {
//            // Validate the DTO object
//            if (startIncidentCaseDTO == null || startIncidentCaseDTO.getLocation() == null || startIncidentCaseDTO.getIncidentCategory() == null) {
//                throw new IllegalArgumentException("Invalid DTO object");
//            }
// 
//            IncidentCase incidentCase = new IncidentCase();
//            // Generate CaseID using businessKey or other logic
//            String generatedCaseId =  businessKey;
//            incidentCase.setCaseId(generatedCaseId);
// 
//            // Set the remaining fields
//            incidentCase.setLocation(startIncidentCaseDTO.getLocation());
//            incidentCase.setIncidentCategory(startIncidentCaseDTO.getIncidentCategory());
//            incidentCase.setIncidentSubCategory(startIncidentCaseDTO.getIncidentSubCategory());
//            incidentCase.setIncidentPriority(startIncidentCaseDTO.getIncidentPriority());
//            incidentCase.setCreationDate(startIncidentCaseDTO.getDate());
//            incidentCase.setCreationTime(startIncidentCaseDTO.getTime());
//            incidentCase.setCreatedBy(startIncidentCaseDTO.getCreatedBy());
//            incidentCase.setIncidentManager(startIncidentCaseDTO.getIncidentManager());
// 
//            // Ensure status is set to "new"
//            incidentCase.setStatus("new");
// 
//            // Handle attachments
//            List<Incident_Attachment> attachmentList = new ArrayList<>();
//            if (startIncidentCaseDTO.getAttachments() != null && !startIncidentCaseDTO.getAttachments().isEmpty()) {
//                startIncidentCaseDTO.getAttachments().forEach(attachment -> {
//                    byte[] attachmentBytes = Base64.getDecoder().decode(attachment);
//                    Incident_Attachment incident_Attachment = new Incident_Attachment();
//                    incident_Attachment.setAttachmentData(attachmentBytes);
//                    incident_Attachment.setIncidentCase(incidentCase);
//                    attachmentList.add(incident_Attachment);
//                });
//                incidentCase.setAttachments(attachmentList);
//            }
// 
//            // Save the IncidentCase with attachments
//            incidentCaseDAO.saveIncidentCase(incidentCase);
//        } catch (IllegalArgumentException e) {
//            System.out.println("Invalid input: " + e.getMessage());
//            result = false;
//        } catch (Exception e) {
//            System.out.println("Error saving incident case: " + e.getMessage());
//            result = false;
//        }
//        return result;
//    }
//  //Add the attachement in incident case//
//  
//    // Submit the recommendation for the reviewer
//    @Transactional
//	public void completeCase(CompleteCaseRequestDTO requestDTO) {
//		try {
//
//			incidentCaseDAO.updateCaseStatus(requestDTO.getCaseId(), "final_reviewer_reviewed");
//			IncidentCase incidentCase = incidentCaseDAO.getCaseById(requestDTO.getCaseId());
//			if (incidentCase != null) {
//				for (PreventiveActions action : requestDTO.getPreventiveActions()) {
//					action.getIncidentCases().add(incidentCase);
//				}
//				incidentCase.setPreventiveActions(requestDTO.getPreventiveActions().stream().collect(Collectors.toSet()));
//				incidentCaseDAO.saveIncidentCase(incidentCase);
//			} else {
//				throw new RuntimeException("IncidentCase not found with ID: " + requestDTO.getCaseId());
//			}
//			String processId = requestDTO.getCaseId().replace("INC", "");
//			String taskID = taskListService.getActiveTaskID(processId);
//			if (taskID == null || taskID.isEmpty()) {
//				throw new RuntimeException("No active task found for Process ID: " + processId);
//			}
//			taskListService.CompleteTaskByID(taskID, Map.of("status", "final_reviewer_reviewed"));
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw new RuntimeException("Error completing case: " + e.getMessage(), e);
//		}
//	}
// 

	// Add Incident Details

	@Transactional
	public void addIncidentDetails(IncidentCaseDTO incidentCaseDTO) {
		IncidentCase incidentCase = new IncidentCase();
		incidentCase.setCaseId(incidentCaseDTO.getCaseId());
		incidentCase.setIncidentCategory(incidentCaseDTO.getIncidentCategory());
		incidentCase.setIncidentSubCategory(incidentCaseDTO.getIncidentSubCategory());
		incidentCase.setIncidentPriority(incidentCaseDTO.getIncidentPriority());
		incidentCase.setStatus(incidentCaseDTO.getStatus());
		incidentCase.setIncidentManager(incidentCaseDTO.getIncidentManager());
		incidentCase.setInvestigator(incidentCaseDTO.getInvestigator());
		incidentCase.setIncidentType(incidentCaseDTO.getIncidentType());

		incidentCase.setState(incidentCaseDTO.getState());
		incidentCase.setCity(incidentCaseDTO.getCity());
		incidentCase.setBranch(incidentCaseDTO.getBranch());
		incidentCase.setDepartment(incidentCaseDTO.getDepartment());

		incidentCase.setReportedBy(incidentCaseDTO.getReportedBy());

		incidentCase.setMedicationClass(incidentCaseDTO.getMedicationClass());
		incidentCase.setIncidentDescription(incidentCaseDTO.getIncidentDescription());
		incidentCase.setBlockRoomNo(incidentCaseDTO.getBlockRoomNo());
		incidentCase.setFloor(incidentCaseDTO.getFloor());
		incidentCase.setCreationDate(incidentCaseDTO.getCreationDate());
		incidentCase.setIncidentTime(incidentCaseDTO.getIncidentTime());
		incidentCase.setCreationTime(incidentCaseDTO.getCreationTime());
		incidentCase.setIncidentDate(incidentCaseDTO.getIncidentDate());
		incidentCase.setInitialActionTaken(incidentCaseDTO.getInitialActionTaken());
		incidentCase.setCorrectiveActions(incidentCaseDTO.getCorrectiveActions());
		incidentCase.setPreventiveActions(incidentCaseDTO.getPreventiveActions());
		incidentCase.setManagerComments(incidentCaseDTO.getManagerComments());

		PatientDetails patientDetails = incidentCaseDAO.findById1(incidentCaseDTO.getPatientId());
		if (patientDetails != null) {
			incidentCase.setPatientDetails(patientDetails); // Assuming you have set up this method in IncidentCase
		} else {
			throw new RuntimeException("Patient not found with ID: " + incidentCaseDTO.getPatientId());
		}
		List<StaffDetails> staffDetailsList = new ArrayList<>();
		for (String staffId : incidentCaseDTO.getStaffIds()) {
			StaffDetails staffDetails = incidentCaseDAO.findById(staffId);
			if (staffDetails != null) {
				staffDetailsList.add(staffDetails);
			} else {
				throw new RuntimeException("Staff not found with ID: " + staffId);
			}
		}
		incidentCase.setStaffDetails(staffDetailsList);
		// Handling evidence attachments
		List<Incident_Attachment> incident_Attachments = new ArrayList<>();
		for (String base64Attachment : incidentCaseDTO.getAttachments()) {
			Incident_Attachment attachment = new Incident_Attachment();
			byte[] decodedBytes = java.util.Base64.getDecoder().decode(base64Attachment);

			attachment.setAttachmentData(decodedBytes);
			attachment.setAttachmentType("report incident"); // Replace with actual type if available
			attachment.setIncidentCase(incidentCase); // Link the attachment to the incident case

			incident_Attachments.add(attachment); // Add the attachment to the list
		}

		incidentCase.setAttachments(incident_Attachments); // Update the incident case with the list of attachments

		// Save the incident case and its attachments
		// Assuming you have a DAO or repository to save the incident case
		incidentCaseDAO.save(incidentCase);
	}

// Method to retrieve an incident case by case ID
	public IncidentCaseDTO getIncidentCaseByCaseId(String caseId) {
		IncidentCaseDTO incidentCase = incidentCaseDAO.findIncidentCaseById(caseId);
		// Check if the incidentCase is null to avoid NullPointerException
		return incidentCase;

	}

	public void updateIncidentCaseActions(IncidentInvestigationDTO investigationDTO) {
	    // Retrieve the IncidentCase by caseId
	    IncidentCase incidentCase = incidentCaseDAO.findIncidentById(investigationDTO.getCaseId());

	    if (incidentCase == null) {
	        throw new RuntimeException("Incident Case not found for caseId: " + investigationDTO.getCaseId());
	    }

	    // Update correctiveActions if provided and non-empty
	    if (investigationDTO.getCorrectiveActions() != null && !investigationDTO.getCorrectiveActions().isEmpty()) {
	        incidentCase.setCorrectiveActions(investigationDTO.getCorrectiveActions());
	    }

	    // Update preventiveActions if provided and non-empty
	    if (investigationDTO.getPreventiveActions() != null && !investigationDTO.getPreventiveActions().isEmpty()) {
	        incidentCase.setPreventiveActions(investigationDTO.getPreventiveActions());
	        
	        // Set incidentManager if preventiveActions has a value
	        incidentCase.setIncidentManager("patientsafetymanager@aaseya.com");
	    }

	    // Determine status based on provided actions
	    if (investigationDTO.getCorrectiveActions() != null && !investigationDTO.getCorrectiveActions().isEmpty()) {
	        // Prioritize correctiveActions if provided and non-empty
	        incidentCase.setStatus("under_investigation");
	    } else if (investigationDTO.getPreventiveActions() != null && !investigationDTO.getPreventiveActions().isEmpty()) {
	        // Set status to pending_review only if preventiveActions is provided and correctiveActions is empty or null
	        incidentCase.setStatus("pending_review");
	    }

	    // Set actionRequired if provided
	    if (investigationDTO.getActionRequired() != null) {
	        incidentCase.setActionRequired(investigationDTO.getActionRequired());
	    }

	    // Save or update the incident case
	    incidentCaseDAO.saveOrUpdate(incidentCase);
	}
	



	
	public List<IncidentCaseDTODashboard> getIncidentCases(String role, String userId) {
	    List<IncidentCase> incidentCases;
	    switch (role.toLowerCase()) {
	        case "investigationteam": // Updated from "investigator" to "investigationteam"
	            incidentCases = incidentCaseDAO.findByInvestigationTeamAndUnderOrPendingInvestigationStatus(userId);
	            break;
	        case "doctor":
	        case "nurse":
	            incidentCases = incidentCaseDAO.findByReportedByAndNotCompletedOrClosedStatus(userId);
	            break;
	        case "patientsafetymanager":
	            incidentCases = incidentCaseDAO.findByIncidentManagerAndNotCompletedStatus(userId);
	            break;
	        default:
	            throw new IllegalArgumentException("Invalid role: " + role);
	    }
	    return incidentCases.stream().map(this::convertToDashboardDTO).collect(Collectors.toList());
	}

	 private IncidentCaseDTODashboard convertToDashboardDTO(IncidentCase incidentCase) {
		    IncidentCaseDTODashboard dto = new IncidentCaseDTODashboard();
		    dto.setCaseId(incidentCase.getCaseId());
		    dto.setIncidentType(incidentCase.getIncidentType());
		    dto.setIncidentCategory(incidentCase.getIncidentCategory());
		    dto.setIncidentSubCategory(incidentCase.getIncidentSubCategory());
		    dto.setIncidentPriority(incidentCase.getIncidentPriority());
		    dto.setCreationDate(incidentCase.getCreationDate());
		    dto.setCreationTime(incidentCase.getCreationTime());
		    dto.setReportedBy(incidentCase.getReportedBy());
		    dto.setStatus(incidentCase.getStatus());
		    dto.setIncidentManager(incidentCase.getIncidentManager());
		    dto.setCorrectiveActions(incidentCase.getCorrectiveActions());
		    dto.setPreventiveActions(incidentCase.getPreventiveActions());

		    // Additional logic for assessmentType
		    if (incidentCase.getCorrectiveActions() != null && !incidentCase.getCorrectiveActions().isEmpty()) {
		        dto.setAssessmentType("follow up");
		    } else if (incidentCase.getPreventiveActions() != null && !incidentCase.getPreventiveActions().isEmpty()) {
		        dto.setAssessmentType("");
		    } else {
		        dto.setAssessmentType("initial");
		    }

		    return dto;
		}

	    
	    public String closeIncidentByBusinessKey(String businessKey) {
		       IncidentCase incidentCase = incidentCaseDAO.findIncidentByIncidentBusinessKey(businessKey);
		 
		       if (incidentCase != null) {
		           incidentCase.setStatus("Closed");
		           incidentCaseDAO.updateIncidentCase(incidentCase);
		           return "Incident closed successfully for Business Key: " + businessKey;
		       } else {
		           return "Incident not found with Business Key: " + businessKey;
		       }
		   }
	    
	    public void save(IncidentCase incidentCase) {
	        incidentCaseDAO.save(incidentCase);
	    }
	    
	    
	    public String processInvestigationFindings(String parentProcessInstanceKey, Map<String, String> reviewData) {
	        // Step 1: Retrieve the subprocess instance key
	        String subprocessInstanceKey = operateService.searchProcessInstances(parentProcessInstanceKey, "");
	        if (subprocessInstanceKey == null) {
	            throw new IllegalArgumentException("Subprocess instance key not found for parent key: " + parentProcessInstanceKey);
	        }

	        // Step 2: Get active task ID for the subprocess
	        String activeTaskId = taskListService.getActiveTaskID(subprocessInstanceKey);
	        if (activeTaskId == null) {
	            throw new IllegalArgumentException("No active task found for subprocess: " + subprocessInstanceKey);
	        }

	        // Step 3: Convert incoming review data to Camunda-supported variables
	        Map<String, Object> camundaVariables = convertToCamundaVariables(reviewData);

	        // Step 4: Complete the task in Camunda
	        String response = taskListService.CompleteTaskByID(activeTaskId, camundaVariables);
	        if (response.contains("Error")) {
	            throw new RuntimeException("Failed to complete task: " + response);
	        }

	        // Step 5: Save final reviewer comment and incident manager in the database
	        String finalReviewerComment = reviewData.get("finalReviewerComment");
	        IncidentCase incidentCase = incidentCaseDAO.findIncidentByIncidentBusinessKey(parentProcessInstanceKey);
	        if (incidentCase == null) {
	            throw new IllegalArgumentException("Incident case not found for process instance key: " + parentProcessInstanceKey);
	        }

	        // Update incident case status, final comment, and incident manager
	        incidentCase.setStatus("Reviewed");
	        incidentCase.setFinalReviewerComment(finalReviewerComment);
	        incidentCase.setIncidentManager("patientsafetymanager@aaseya.com"); // Set incident manager

	        // Save updates to the incident case
	        incidentCaseDAO.saveOrUpdate(incidentCase);

	        return "Review findings successfully submitted and saved.";
	    }


	    // Helper method to convert request data to Camunda variables format
	    private Map<String, Object> convertToCamundaVariables(Map<String, String> reviewData) {
	        Map<String, Object> variables = new HashMap<>();
	        
	        reviewData.forEach((key, value) -> {
	            if (value != null) {
	                variables.put(key, "\"" + value + "\""); // Wrap value in quotes for Camunda format
	            }
	        });

	        return variables;
	    }

	}


