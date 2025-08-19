package com.aaseya.ITSM.zeebeworker;
 
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
 
@Component
public class SendEmailWorker {
 
	@Autowired
	private JavaMailSender mailSender;
 
	@Autowired
	private ZeebeClient zeebeClient;
 
	@JobWorker(type = "SendEmail")
	public void CheckCelebAge(final JobClient client, final ActivatedJob job) {
 
		Map<String, Object> variablesAsMap = job.getVariablesAsMap();
 
		 String sender = "aaseyaitsm@gmail.com";
		String receiver = variablesAsMap.get("receiver").toString();
		String subject = variablesAsMap.get("subject").toString();
		String body = variablesAsMap.get("body").toString();
 
		try {
			System.out.println("Sending email to: " + receiver);
			System.out.println("Subject: " + subject);
			System.out.println("Body: " + body);
			sendMail(sender,receiver, subject, body);
			String resultMessage = "Mail Sent Successfully to " + receiver;
 
			HashMap<String, Object> variables = new HashMap<>();
			variables.put("result", resultMessage);
			client.newCompleteCommand(job.getKey()).variables(variables).send().exceptionally((throwable -> {
				throw new RuntimeException("Could not complete job", throwable);
			}));
		} catch (MessagingException e) {
		    System.out.println("Failed to send email: " + e.getMessage());
			e.printStackTrace();
			client.newFailCommand(job.getKey())
		      .retries(job.getRetries() - 1)  // Allow retries to happen
		      .send().exceptionally(throwable -> {
		          System.out.println("Could not fail the job: " + throwable.getMessage());
		          return null;
		      });
 
 
		}
	}
 
	 public void sendMail(String sender,String receiver, String subject, String body) throws MessagingException {
		MimeMessage message = mailSender.createMimeMessage();

		
		MimeMessageHelper helper = new MimeMessageHelper(message, true);
		 helper.setFrom(sender);
		helper.setTo(receiver);
		helper.setSubject(subject);
		helper.setText(body, false);
 
		mailSender.send(message);
	}
 
}
 