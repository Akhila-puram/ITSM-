 
package com.aaseya.ITSM.zeebeworker;
 
import java.util.Map;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
 
import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
 
import com.aaseya.ITSM.service.IncidentCaseService;
 
@Component
public class CloseIncidentWorker {
 
    @Autowired
    private ZeebeClient zeebeClient;
 
    @Autowired
    private IncidentCaseService incidentCaseService;
 
    @JobWorker(type = "close-incident")
    @Transactional
    public void closeIncident(final ActivatedJob job) {
        System.out.println("Job received: Closing incident...");
 
        try {
            Map<String, Object> variables = job.getVariablesAsMap();
            System.out.println("Job Variables: " + variables);
 
            if (variables.containsKey("IncidentBusinessKey")) {
                String incidentBusinessKey = variables.get("IncidentBusinessKey").toString();
                System.out.println("IncidentBusinessKey found: " + incidentBusinessKey);
 
                // Use the new method to close the incident
                String resultMessage = incidentCaseService.closeIncidentByBusinessKey(incidentBusinessKey);
                System.out.println(resultMessage);
            } else {
                System.out.println("IncidentBusinessKey is missing from job variables.");
            }
 
            // Complete the job
            zeebeClient.newCompleteCommand(job.getKey()).send().join();
            System.out.println("Job completed for key: " + job.getKey());
 
        } catch (Exception e) {
            System.err.println("Failed to process job with key: " + job.getKey());
            e.printStackTrace();
            zeebeClient.newFailCommand(job.getKey()).retries(job.getRetries() - 1).send().join();
        }
    }
}
