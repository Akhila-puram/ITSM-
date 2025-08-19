package com.aaseya.ITSM;

import java.util.Properties;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import io.camunda.zeebe.client.ZeebeClient;

@SpringBootApplication
public class ItsmApplication {

	public static void main(String[] args) {

		SpringApplication.run(ItsmApplication.class, args);
//		  ZeebeClient zeebeClient = ZeebeClient.newClientBuilder()
//	              
//	                .usePlaintext()
//	                .build();
//
//	        
//	        String pdfFilePath ="C:\\Users\\ravichandra.misala\\Downloads\\Follow Up.pdf"; 
//
//	       
//	        startPdfProcessing(zeebeClient, pdfFilePath);
//	        
//	        
//	        zeebeClient.close();
//	    }
//
//	    private static void startPdfProcessing(ZeebeClient zeebeClient, String filePath) {
//	        zeebeClient.newCreateInstanceCommand()
//	            .bpmnProcessId("ITSM") 
//	            .latestVersion()
//	            .variables("{\"pdfFilePath\": \"" + filePath + "\"}") 
//	            .send()
//	            .join(); 
//
//	        System.out.println("PDF processing started for: " + filePath);
//	    }
//	
//	
	}

	@Bean
	public JavaMailSender javaMailSender() {
	    JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
	    mailSender.setHost("smtp.gmail.com");
	    mailSender.setPort(587);
	    mailSender.setUsername("aaseyaitsm@gmail.com");
	    mailSender.setPassword("oqjz fepj nndt piwo"); // Make sure this is correct

	    Properties props = mailSender.getJavaMailProperties();
	    props.put("mail.transport.protocol", "smtp");
	    props.put("mail.smtp.auth", "true");
	    props.put("mail.smtp.starttls.enable", "true");
	    props.put("mail.debug", "true");

	    return mailSender; // Return the configured instance
	}

}
