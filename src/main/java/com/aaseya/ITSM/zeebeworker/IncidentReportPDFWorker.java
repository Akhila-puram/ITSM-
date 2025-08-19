package com.aaseya.ITSM.zeebeworker;
 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
 
import com.aaseya.ITSM.service.IncidentReportPDFGenerator;
 
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
 
@Component
public class IncidentReportPDFWorker {
    private static final Logger logger = LoggerFactory.getLogger(IncidentReportPDFWorker.class);
 
    @Autowired
    private IncidentReportPDFGenerator incidentReportPDFGenerator;
 
    @JobWorker(type = "generate-incident-report")
    public void handleGeneratePdfReport(final JobClient client, final ActivatedJob job) {
        // Extracting ITSM Business Key from the job variables
        String itsmBusinessKey = (String) job.getVariablesAsMap().get("ITSMBusinessKey");
        String caseId = itsmBusinessKey != null ? itsmBusinessKey : "UNKNOWN";
 
        try {
            // Call the service to generate and store the PDF report with String caseId
            incidentReportPDFGenerator.generateIncidentReport(caseId);
 
            // Complete the job in Zeebe
            client.newCompleteCommand(job.getKey()).send().join();
            logger.info("Incident report generated successfully for Case ID: {}", caseId);
        } catch (Exception e) {
            logger.error("Error generating PDF report for Case ID {}: {}", caseId, e.getMessage());
            client.newFailCommand(job.getKey())
                  .retries(0)
                  .errorMessage("Error generating PDF report: " + e.getMessage())
                  .send()
                  .join();
        }
    }
}