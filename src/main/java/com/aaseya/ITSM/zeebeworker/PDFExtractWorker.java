package com.aaseya.ITSM.zeebeworker;

import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.spring.client.annotation.JobWorker;

@Component
public class PDFExtractWorker {
    private static final Logger logger = LoggerFactory.getLogger(PDFExtractWorker.class);

    @Autowired
    private ZeebeClient zeebeClient;

    @JobWorker(type = "extract-pdf")
    public void extractData(JobClient client, ActivatedJob job) {
        ObjectMapper objectMapper = new ObjectMapper();
        String pdfFilePath = null;

        try {
            // Parse the job variables to get the PDF file path
            JsonNode variables = objectMapper.readTree(job.getVariables());
            JsonNode filePathNode = variables.get("pdfFilePath");

            if (filePathNode != null && !filePathNode.isNull()) {
                pdfFilePath = filePathNode.asText();
                logger.info("PDF file path received: " + pdfFilePath);
            } else {
                logger.error("The 'pdfFilePath' variable is missing or null.");
                client.newFailCommand(job.getKey())
                      .retries(0)
                      .errorMessage("The 'pdfFilePath' variable is missing or null.")
                      .send()
                      .join();
                return;
            }

            PDDocument document = null;
            try {
                // Load the PDF document from the file system
                File pdfFile = new File(pdfFilePath);
                if (pdfFile.exists()) {
                    document = PDDocument.load(pdfFile);
                    PDFTextStripper pdfStripper = new PDFTextStripper();

                    // Extract text from the PDF
                    String text = pdfStripper.getText(document);
                    logger.info("Extracted Text: \n" + text);
                } else {
                    throw new IOException("File not found: " + pdfFilePath);
                }

            } catch (IOException e) {
                logger.error("Error reading PDF file: {}", e.getMessage());
                client.newFailCommand(job.getKey())
                      .retries(0)
                      .errorMessage("Error reading PDF file: " + e.getMessage())
                      .send()
                      .join();
            } finally {
                if (document != null) {
                    try {
                        document.close();
                    } catch (IOException e) {
                        logger.error("Error closing document: {}", e.getMessage());
                    }
                }
            }

        } catch (IOException e) {
            logger.error("Error parsing job variables: {}", e.getMessage());
            client.newFailCommand(job.getKey())
                  .retries(0)
                  .errorMessage("Error parsing job variables: " + e.getMessage())
                  .send()
                  .join();
        }
    }
}
