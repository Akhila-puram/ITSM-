package com.aaseya.ITSM.zeebeworker;
 
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
@Component
public class WhatsappandEmail {
	@Autowired
	private ZeebeClient zeebeClient;
	@JobWorker(type = "notify-applicant")
	public void startReview(final ActivatedJob job) {
		System.out.println("Notification sent successfully");
	}
}
