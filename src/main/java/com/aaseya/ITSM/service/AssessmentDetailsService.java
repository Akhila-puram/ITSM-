package com.aaseya.ITSM.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aaseya.ITSM.dao.AssessmentDetailsDAO;
import com.aaseya.ITSM.dao.IncidentCaseDAO;
import com.aaseya.ITSM.dto.IncidentCaseResponseDTO;
import com.aaseya.ITSM.model.AssessmentDetails;
import com.aaseya.ITSM.model.IncidentCase;

@Service
public class AssessmentDetailsService {

    @Autowired
    private OperateService operateService;

    @Autowired
    private TaskListService taskListService;

    @Autowired
    private AssessmentDetailsDAO assessmentDetailsDAO;

    @Autowired
    private IncidentCaseDAO incidentCaseDAO;

    public IncidentCaseResponseDTO saveAssessmentDetails(AssessmentDetails assessmentDetails, String incidentCaseId) {
        IncidentCaseResponseDTO responseDTO = new IncidentCaseResponseDTO();

        try {
            IncidentCase incidentCase = incidentCaseDAO.getCaseById(incidentCaseId);

            if (incidentCase == null) {
                responseDTO.setStatus("FAILURE");
                responseDTO.setMessage("Incident case not found");
                return responseDTO;
            }

            // Set investigator email to the incident case
            incidentCase.setInvestigator("investigationteam@aaseya.com");

            String subprocessInstanceKey = operateService.searchProcessInstances(incidentCaseId, "");

            if (subprocessInstanceKey == null) {
                responseDTO.setStatus("FAILURE");
                responseDTO.setMessage("Subprocess instance not found");
                return responseDTO;
            }

            String activeTaskId = taskListService.getActiveTaskID(subprocessInstanceKey);

            if (activeTaskId == null || activeTaskId.isEmpty()) {
                responseDTO.setStatus("FAILURE");
                responseDTO.setMessage("Active task not found");
                return responseDTO;
            }

            setTaskVariables(assessmentDetails, activeTaskId);

            assessmentDetails.setIncidentCase(incidentCase);
            assessmentDetailsDAO.saveAssessmentDetails(assessmentDetails);

            // Save updated incident case with investigator information
            incidentCaseDAO.saveOrUpdate(incidentCase);

            try {
                // Check if the assessment type is "initial" before updating the status
                if (assessmentDetails.getAssessmentType().equalsIgnoreCase("initial")) {
                    boolean statusUpdated = incidentCaseDAO.updateStatus(incidentCaseId, "pending_investigation");
                    
                    if (statusUpdated) {
                        responseDTO.setStatus("SUCCESS");
                        responseDTO.setMessage("Assessment details saved, and incident status updated to pending_investigation.");
                    } else {
                        responseDTO.setStatus("PARTIAL_SUCCESS");
                        responseDTO.setMessage("Assessment details saved, but failed to update incident status.");
                    }
                } else {
                    // If assessment type is not "initial," proceed without updating the status
                    responseDTO.setStatus("SUCCESS");
                    responseDTO.setMessage("Assessment details saved.");
                }
            } catch (Exception e) {
                responseDTO.setStatus("FAILURE");
                responseDTO.setMessage("Error saving assessment details.");
                e.printStackTrace();
            }

        } catch (Exception e) {
            responseDTO.setStatus("FAILURE");
            responseDTO.setMessage("Error saving assessment details.");
            e.printStackTrace();
        }

        return responseDTO;
    }

    // New method to convert AssessmentDetails fields to Camunda-compatible variables
    private Map<String, Object> convertToCamundaVariables(AssessmentDetails assessmentDetails) {
        Map<String, Object> variables = new HashMap<>();

        if (assessmentDetails.getAssessmentType() != null) {
            variables.put("assessmentType", "\"" + assessmentDetails.getAssessmentType() + "\"");
        }
        if (assessmentDetails.getAssessmentDate() != null) {
            variables.put("assessmentDate", "\"" + assessmentDetails.getAssessmentDate().toString() + "\"");
        }
        if (assessmentDetails.getBloodPressure() != null) {
            variables.put("bloodPressure", "\"" + assessmentDetails.getBloodPressure() + "\"");
        }
        if (assessmentDetails.getHeartRate() != null) {
            variables.put("heartRate", "\"" + assessmentDetails.getHeartRate() + "\"");
        }
        if (assessmentDetails.getTemperature() != null) {
            variables.put("temperature", "\"" + assessmentDetails.getTemperature() + "\"");
        }
        if (assessmentDetails.getRespiratoryRate() != null) {
            variables.put("respiratoryRate", "\"" + assessmentDetails.getRespiratoryRate() + "\"");
        }
        if (assessmentDetails.getOxygenSaturation() != null) {
            variables.put("oxygenSaturation", "\"" + assessmentDetails.getOxygenSaturation() + "\"");
        }
        if (assessmentDetails.getLevelOfConsciousnes() != null) {
            variables.put("levelOfConsciousnes", "\"" + assessmentDetails.getLevelOfConsciousnes() + "\"");
        }
        if (assessmentDetails.getPainLevel() != null) {
            variables.put("painLevel", "\"" + assessmentDetails.getPainLevel() + "\"");
        }
        if (assessmentDetails.getMentalStatus() != null) {
            variables.put("mentalStatus", "\"" + assessmentDetails.getMentalStatus() + "\"");
        }
        if (assessmentDetails.getMobilityStatus() != null) {
            variables.put("mobilityStatus", "\"" + assessmentDetails.getMobilityStatus() + "\"");
        }
        if (assessmentDetails.getInjuriesSustained() != null) {
            variables.put("injuriesSustained", "\"" + assessmentDetails.getInjuriesSustained() + "\"");
        }
        if (assessmentDetails.getMedications() != null) {
            variables.put("medications", "\"" + assessmentDetails.getMedications().toString() + "\"");
        }
        if (assessmentDetails.getDescription() != null) {
            variables.put("description", "\"" + assessmentDetails.getDescription() + "\"");
        }
        if (assessmentDetails.getPatientStatus() != null) {
            variables.put("patientStatus", "\"" + assessmentDetails.getPatientStatus() + "\"");
        }
        if (assessmentDetails.getPatientCondition() != null) {
            variables.put("patientCondition", "\"" + assessmentDetails.getPatientCondition() + "\"");
        }
        if (assessmentDetails.getIncidentDescription() != null) {
            variables.put("incidentDescription", "\"" + assessmentDetails.getIncidentDescription() + "\"");
        }

        return variables;
    }

    // Modified setTaskVariables method to include check for assessmentType and patientCondition
    private void setTaskVariables(AssessmentDetails assessmentDetails, String activeTaskId) {
        // Convert AssessmentDetails to Camunda-compatible variables
        Map<String, Object> camundaVariables = convertToCamundaVariables(assessmentDetails);

        // Check assessmentType and patientCondition to set patientFollowupCondition
        if ("follow up".equalsIgnoreCase(assessmentDetails.getAssessmentType())) {
            String patientFollowupCondition = "Unstable";
            // If patientCondition is stable, set follow-up condition to stable
            if ("Stable".equalsIgnoreCase(assessmentDetails.getPatientCondition())) {
                patientFollowupCondition = "Stable";
            }

            // Add patientFollowupCondition to Camunda variables
            camundaVariables.put("patientFollowupCondition", "\"" + patientFollowupCondition + "\"");
        }

        // Call the TaskListService to complete the task with these variables
        taskListService.CompleteTaskByID(activeTaskId, camundaVariables);
    }
}
