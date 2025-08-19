package com.aaseya.ITSM.service;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aaseya.ITSM.dto.FormVariables;
import com.aaseya.ITSM.dto.IncidentCaseResponseDTO;
import com.aaseya.ITSM.dto.StartIncidentCaseDTO;
import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.response.ProcessInstanceEvent;
@Service


public class StartIncidentCaseService {
	
    private static final Logger logger = LoggerFactory.getLogger(StartIncidentCaseService.class);
    
    @Autowired
    private ZeebeClient zeebeClient;
    
    @Autowired
    private TaskListService taskListService;
    
    public String startIncidentCaseProcess(StartIncidentCaseDTO startIncidentCaseDTO) {
        String businessKey = "";
        // Map the IncidentCaseDTO fields to process variables
        Map<String, Object> variables = new HashMap<>();
        variables.put("caseId", startIncidentCaseDTO.getCaseId());
        variables.put("location", startIncidentCaseDTO.getLocation());
        variables.put("incidentCategory", startIncidentCaseDTO.getIncidentCategory());
        variables.put("incidentSubCategory", startIncidentCaseDTO.getIncidentSubCategory());
        variables.put("incidentPriority", startIncidentCaseDTO.getIncidentPriority());
        variables.put("date", startIncidentCaseDTO.getDate().toString()); // Convert LocalDate to String
        variables.put("time", startIncidentCaseDTO.getTime().toString()); // Convert LocalTime to String
        variables.put("createdBy", startIncidentCaseDTO.getCreatedBy());
        variables.put("incidentManager", startIncidentCaseDTO.getIncidentManager());
        variables.put("channel", startIncidentCaseDTO.getChannel());
        variables.put("incidentType", startIncidentCaseDTO.getIncidentType());

        try {
            // Create and start the process instance
            ProcessInstanceEvent processInstanceEvent = zeebeClient.newCreateInstanceCommand()
                .bpmnProcessId("ITSM")
                .latestVersion()
                .variables(variables)
                .send()
                .join();
            logger.info("Process Instance Key: {}", processInstanceEvent.getProcessInstanceKey());
            // Generate the business key based on the process instance key
            businessKey = "INC" + processInstanceEvent.getProcessInstanceKey();
            variables.put("IncidentBusinessKey", businessKey);
            // Set the business key as a process variable
            zeebeClient.newSetVariablesCommand(processInstanceEvent.getProcessInstanceKey())
                .variables(variables)
                .send()
                .join();
            logger.info("Successfully started process with business key: {}", businessKey);
        } catch (Exception e) {
            logger.error("Error starting incident case process", e);
            throw new RuntimeException("Error starting incident case process", e);
        }
        return businessKey;
    }
    
    //Start incidentCase Process//
    public IncidentCaseResponseDTO startIncidentCaseProcess() {
        IncidentCaseResponseDTO responseDTO = new IncidentCaseResponseDTO();

        try {
            // Create and start the process instance
            ProcessInstanceEvent processInstanceEvent = zeebeClient.newCreateInstanceCommand()
                .bpmnProcessId("ITSM")
                .latestVersion()
                .send()
                .join();

            logger.info("Process Instance Key: {}", processInstanceEvent.getProcessInstanceKey());

            // Generate the business key based on the process instance key
            String businessKey = "INC" + processInstanceEvent.getProcessInstanceKey();

            logger.info("Successfully started process with business key: {}", businessKey);

            // Get process definition key by instance key
            long processDefinitionKey = processInstanceEvent.getProcessDefinitionKey();

            // Set response details for successful case
            responseDTO.setStatus("Success");
            responseDTO.setMessage("Incident case process started and data saved successfully.");
            responseDTO.setBusinessKey(businessKey);
            responseDTO.setProcessDefinitionKey(processDefinitionKey);

        } catch (Exception e) {
            logger.error("Error starting incident case process", e);
            // Set response details for failed case
            responseDTO.setStatus("Failure");
            responseDTO.setMessage("Failed to process incident case: " + e.getMessage());
        }

        return responseDTO;
    }
    // Start incidentCase Process//
    
    //Get formDetails by formID//
    public Map<String, Object> getFormDetails(String formId, String processDefinitionKey) {
	    FormVariables formVariables = taskListService.getFormById(formId, processDefinitionKey, null);
	    if (formVariables != null) {
	        Map<String, Object> result = new HashMap<>();
	        result.put("id", formVariables.getId());
	        result.put("schema", formVariables.getSchema());
	        return result;
	    } else {
	        return null;
	    }
	}
    
  //Get formDetails by formID//
}

