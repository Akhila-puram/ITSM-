package com.aaseya.ITSM.zeebeworker;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.aaseya.ITSM.dto.IncidentCaseDTO;
import com.aaseya.ITSM.dto.PatientDetailsDTO;
import com.aaseya.ITSM.model.AssessmentDetails;
import com.aaseya.ITSM.service.IncidentCaseService;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.spring.client.annotation.JobWorker;

@Service
public class getIncidentCaseDetailsWorker {

    private static final Logger logger = LoggerFactory.getLogger(getIncidentCaseDetailsWorker.class);

    @Autowired
    private IncidentCaseService incidentCaseService;

    @JobWorker(type = "get-incident-case-details", autoComplete = true)
    public void getIncidentCaseDetails(final JobClient client, final ActivatedJob job) {

        // Extract process variables from the job
        Map<String, Object> variables = job.getVariablesAsMap();

        // Extract the caseId from the process variables
        String caseId = (String) variables.get("caseID");

        if (caseId == null || caseId.isEmpty()) {
            logger.error("caseID is missing in process variables");
            // Update the process with an error message
            variables.put("error", "caseID is missing");
            client.newFailCommand(job.getKey())
                  .retries(0)
                  .errorMessage("caseID is missing in the process variables.")
                  .send()
                  .join();
            
        }

        try {
        	
            // Fetch incident case details using the IncidentCaseService
            IncidentCaseDTO incidentDetails = incidentCaseService.getIncidentCaseByCaseId(caseId);
            
       

            // Log the incident details
            logger.info("Incident Case Details for caseId {}: {}", caseId, incidentDetails);
            incidentDetails.setAttachments(null);
            logger.info("",incidentDetails);
           String summerizationInput = "";
            summerizationInput=summerizationInput+"The incident case has been raised. The caseid is  " +incidentDetails.getCaseId()+ ". " +
                    "Incident Category: " + incidentDetails.getIncidentCategory() + ". " +
                    "Incident Sub-Category: " + incidentDetails.getIncidentSubCategory() + ". " +
                    "Priority: " + incidentDetails.getIncidentPriority() + ". " +
                    "Status: " + incidentDetails.getStatus() + ". " +
                    "Incident Manager: " + incidentDetails.getIncidentManager() + ". " +
                    "Investigator: " + incidentDetails.getInvestigator() + ". " +
                    "Incident Type: " + incidentDetails.getIncidentType() + ". " +
                    "Patient ID: " + incidentDetails.getPatientId() + ". " +
                    "Location: " + incidentDetails.getLocation() + ", " +
                    incidentDetails.getCity() + ", " + incidentDetails.getState() + ". " +
                    "Branch: " + incidentDetails.getBranch() + ". " +
                    "Department: " + incidentDetails.getDepartment() + ". " +
                    "Name: " + incidentDetails.getName() + ", Age: " + incidentDetails.getAge() + ". " +
                    "Reported By: " + incidentDetails.getReportedBy() + ". " +
                    "Gender: " + incidentDetails.getGender() + ". " +
                    "Contact No: " + incidentDetails.getContactNo() + ". " +
                    "Family Contact No: " + incidentDetails.getFamilyContactNo() + ". " +
                    "Medication Class: " + incidentDetails.getMedicationClass() + ". " +
                    "Description: " + incidentDetails.getIncidentDescription() + ". " +
                    "Corrective Actions: " + incidentDetails.getCorrectiveActions() + ". " +
                    "Creation Date: " + incidentDetails.getCreationDate() + ". " +
                    "Incident Date: " + incidentDetails.getIncidentDate() + ", Time: " + incidentDetails.getIncidentTime() + ". " +
                    "Initial Action Taken: " + incidentDetails.getInitialActionTaken() + ". " +
                    "Manager Comments: " + incidentDetails.getManagerComments() + ". " +
                    "Preventive Actions: " + incidentDetails.getPreventiveActions() + ".";
            // Set the incident details back in process variables
            		
            for (AssessmentDetails assessmentDetails : incidentDetails.getAssesmentDetails()) {
            	summerizationInput += "Assessment Type: " + assessmentDetails.getAssessmentType() + ". " +
                        "Assessment Date: " + assessmentDetails.getAssessmentDate() + ". " +
                        "Blood Pressure: " + assessmentDetails.getBloodPressure() + ". " +
                        "Heart Rate: " + assessmentDetails.getHeartRate() + ". " +
                        "Temperature: " + assessmentDetails.getTemperature() + ". " +
                        "Respiratory Rate: " + assessmentDetails.getRespiratoryRate() + ". " +
                        "Oxygen Saturation: " + assessmentDetails.getOxygenSaturation() + ". " +
                        "Level of Consciousness: " + assessmentDetails.getLevelOfConsciousnes() + ". " +
                        "Mental Status: " + assessmentDetails.getMentalStatus() + ". " +
                        "Mobility Status: " + assessmentDetails.getMobilityStatus() + ". " +
                        "Injuries Sustained: " + assessmentDetails.getInjuriesSustained() + ". " +
                        "Patient Status: " + assessmentDetails.getPatientStatus() + ". " +
                        "Patient Condition: " + assessmentDetails.getPatientCondition() + ". " +
                        "Description: " + assessmentDetails.getDescription() + ". " +
                        "Medications: " + String.join(", ", assessmentDetails.getMedications()) + ". "; // Joining list of medications
            }
          PatientDetailsDTO patientDetails = incidentDetails.getPatientDetails();
            	summerizationInput += "Patient ID: " + patientDetails.getPatientId() + ". " +
                        "Name: " + patientDetails.getName() + ". " +
                        "Age: " + patientDetails.getAge() + ". " + // Assuming age is never null
                        "Gender: " + patientDetails.getGender() + ". " +
                        "Contact No: " + patientDetails.getContactNo() + ". " +
              "Family Contact No: " + patientDetails.getFamilyContactNo() + ". ";

            variables.put("summerizationInput", summerizationInput);

            // Complete the job with updated variables
            client.newCompleteCommand(job.getKey())
                  .variables(variables)
                  .send()
                  .join();
            	}
        
         catch (Exception e) {
            logger.error("Failed to retrieve incident details for caseId {}: {}", caseId, e.getMessage());
            variables.put("error", "Failed to retrieve incident details: " + e.getMessage());

            // Mark job as failed
            client.newFailCommand(job.getKey())
                  .retries(0)
                  .errorMessage("Failed to retrieve incident details.")
                  .send()
                  .join();
        }
    }
}