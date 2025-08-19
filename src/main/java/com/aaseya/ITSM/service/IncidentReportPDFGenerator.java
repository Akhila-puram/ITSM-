package com.aaseya.ITSM.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aaseya.ITSM.dao.IncidentCaseDAO;
import com.aaseya.ITSM.dao.IncidentReportDAO;
import com.aaseya.ITSM.model.AssessmentDetails;
import com.aaseya.ITSM.model.IncidentCase;
import com.aaseya.ITSM.model.IncidentReport;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import com.itextpdf.kernel.colors.ColorConstants;

@Service
public class IncidentReportPDFGenerator {

	@Autowired
	private IncidentCaseDAO incidentCaseDAO;

	@Autowired
	private IncidentReportDAO incidentReportDAO;

	// Method to generate IncidentCase by ID
	public IncidentCase generateIncidentCaseById(String id) throws Exception {
		IncidentCase incidentCase = incidentCaseDAO.generateIncidentCaseById(id);
		if (incidentCase == null) {
			throw new Exception("Incident case not found for ID: " + id);
		}
		return incidentCase;
	}

	// Method to generate the PDF report and save it in IncidentReport
	public void generateIncidentReport(String id) throws Exception {
		IncidentCase incidentCase = generateIncidentCaseById(id);

		String sourcePath = "D:\\";
		String fileName = "inspection_" + incidentCase.getCaseId() + ".pdf";
		String filePath = sourcePath + fileName;

		// Generate the PDF file
		PdfWriter writer = new PdfWriter(filePath);
		PdfDocument pdf = new PdfDocument(writer);
		Document document = new Document(pdf);

		// Title with spacing
		document.add(new Paragraph("Incident Report").setFontSize(24).setTextAlignment(TextAlignment.CENTER).setBold()
				.setMarginBottom(20));

		// Incident Report Header
		document.add(new Paragraph("Report Details").setBold().setFontSize(14).setMarginBottom(10));
		addTableHeader(document, incidentCase);

		document.add(new Paragraph("\n"));

		// Incident Summary
		document.add(new Paragraph("Incident Summary").setBold().setFontSize(14).setMarginBottom(10));
		addIncidentSummaryTable(document, incidentCase);

		document.add(new Paragraph("\n"));

		// Patient Information
		document.add(new Paragraph("Patient Information").setBold().setFontSize(14).setMarginBottom(10));
		addPatientInfoTable(document, incidentCase);

		document.add(new Paragraph("\n"));

		// Actions Taken
		document.add(new Paragraph("Actions Taken").setBold().setFontSize(14).setMarginBottom(10));
		addActionsTakenTable(document, incidentCase);

		// Assessment Details
		List<AssessmentDetails> assessmentDetailsList = incidentCase.getAssessmentDetails();

		// Check if any assessments are available
		if (assessmentDetailsList != null && !assessmentDetailsList.isEmpty()) {
			boolean hasInitial = false;
			boolean hasFollowUp = false;

			// Identify which assessments exist
			for (AssessmentDetails details : assessmentDetailsList) {
				String type = details.getAssessmentType();
				if ("Initial".equalsIgnoreCase(type))
					hasInitial = true;
				if ("Follow-Up".equalsIgnoreCase(type))
					hasFollowUp = true;
			}

			// Dynamically add Initial or Follow-Up assessment details
			for (AssessmentDetails details : assessmentDetailsList) {
				String type = details.getAssessmentType();
				if ("Initial".equalsIgnoreCase(type) && hasInitial) {
					document.add(
							new Paragraph("Initial Assessment Details").setBold().setFontSize(14).setMarginBottom(10));
					addInitialAssessmentDetailsTable(document, incidentCase);
					hasInitial = false;
				} else if ("Follow-Up".equalsIgnoreCase(type) && hasFollowUp) {
					document.add(new Paragraph("Follow-Up Assessment Details").setBold().setFontSize(14)
							.setMarginBottom(10));
					addFollowUpAssessmentDetailsTable(document, incidentCase);
					hasFollowUp = false;
				}
			}
		} else {
			// // No assessment details available
			document.add(new Paragraph("No assessment details available."));
		}

		// Close the document
		document.close();

		savePDFToIncidentReport(incidentCase, filePath);
	}

	// Method to save PDF file as byte array in IncidentReport
	private void savePDFToIncidentReport(IncidentCase incidentCase, String filePath) throws IOException {
		File pdfFile = new File(filePath);
		byte[] pdfBytes = Files.readAllBytes(pdfFile.toPath());

		// Create and save the IncidentReport
		IncidentReport incidentReport = new IncidentReport();

		// Dynamically generate reportId (can be customized)
		String reportId = generateReportId();
		incidentReport.setReportId(reportId); // Set the generated report ID
		incidentReport.setReportData(pdfBytes); // Assuming reportData is a byte[] field in IncidentReport
		incidentReport.setIncidentCase(incidentCase); // Link it to the incident case if needed

		// Save incidentReport to the database using the DAO
		incidentReportDAO.save(incidentReport); // Ensure you have the corresponding DAO/repository for IncidentReport
	}

	// Method to generate a unique report ID
	private String generateReportId() {
		return UUID.randomUUID().toString(); // Generates a unique ID
	}

	// Method to add table header
	private void addTableHeader(Document document, IncidentCase incidentCase) {
		Table table = new Table(UnitValue.createPercentArray(new float[] { 50, 50 }));
		table.setWidth(UnitValue.createPercentValue(100));
		table.setMarginBottom(10);

		table.addCell(createHeaderCell("Incident ID"));
		table.addCell(createCell(incidentCase.getCaseId() != null ? incidentCase.getCaseId() : "N/A"));

		table.addCell(createHeaderCell("Report Generated By"));
		table.addCell(createCell(incidentCase.getReportedBy() != null ? incidentCase.getReportedBy() : "N/A"));

		table.addCell(createHeaderCell("Report Date"));
		table.addCell(createCell((incidentCase.getCreationDate() != null ? incidentCase.getCreationDate().toString()
				: "N/A") + " "
				+ (incidentCase.getCreationTime() != null ? incidentCase.getCreationTime().toString() : "N/A")));

		table.addCell(createHeaderCell("Incident Status"));
		table.addCell(createCell(incidentCase.getStatus() != null ? incidentCase.getStatus() : "N/A"));

		document.add(table);
	}

	// Method to add incident summary table
	private void addIncidentSummaryTable(Document document, IncidentCase incidentCase) {
		Table table = new Table(UnitValue.createPercentArray(new float[] { 50, 50 }));
		table.setWidth(UnitValue.createPercentValue(100));
		table.setMarginBottom(10);

		table.addCell(createHeaderCell("Incident Type"));
		table.addCell(createCell(incidentCase.getIncidentType() != null ? incidentCase.getIncidentType() : "N/A"));

		table.addCell(createHeaderCell("Priority"));
		table.addCell(
				createCell(incidentCase.getIncidentPriority() != null ? incidentCase.getIncidentPriority() : "N/A"));

		document.add(table);
	}

	// Method to add patient info table
	private void addPatientInfoTable(Document document, IncidentCase incidentCase) {
		Table table = new Table(UnitValue.createPercentArray(new float[] { 50, 50 }));
		table.setWidth(UnitValue.createPercentValue(100));
		table.setMarginBottom(10);

		table.addCell(createHeaderCell("Patient ID"));
		table.addCell(createCell(
				incidentCase.getPatientDetails() != null && incidentCase.getPatientDetails().getPatientId() != null
						? incidentCase.getPatientDetails().getPatientId()
						: "N/A"));

		table.addCell(createHeaderCell("Gender"));
		table.addCell(createCell(
				incidentCase.getPatientDetails() != null && incidentCase.getPatientDetails().getGender() != null
						? incidentCase.getPatientDetails().getGender()
						: "N/A"));

		document.add(table);
	}

	// Method to add actions taken table
	private void addActionsTakenTable(Document document, IncidentCase incidentCase) {
        Table table = new Table(UnitValue.createPercentArray(new float[]{50, 50}));
        table.setWidth(UnitValue.createPercentValue(100));
        table.setMarginBottom(10);

        table.addCell(createHeaderCell("Initial Action Taken"));
        table.addCell(createCell(incidentCase.getInitialActionTaken() != null ? incidentCase.getInitialActionTaken() : "N/A"));

        table.addCell(createHeaderCell("Corrective Actions"));
        table.addCell(createCell(incidentCase.getCorrectiveActions() != null ? incidentCase.getCorrectiveActions() : "N/A"));

       
        table.addCell(createHeaderCell("Preventive Actions"));
        table.addCell(createCell(incidentCase.getPreventiveActions() != null ? incidentCase.getPreventiveActions() : "N/A"));

        table.addCell(createHeaderCell("Final Reviewer Comment"));
        table.addCell(createCell(incidentCase.getFinalReviewerComment() != null ? incidentCase.getFinalReviewerComment() : "N/A"));

        document.add(table);
    }

	// Method to add initial assessment details table
	private void addInitialAssessmentDetailsTable(Document document, IncidentCase incidentCase) {
		createAssessmentDetailsTable(document, incidentCase.getAssessmentDetails(), "Initial");
	}

	// Method to add follow-up assessment details table
	private void addFollowUpAssessmentDetailsTable(Document document, IncidentCase incidentCase) {
		createAssessmentDetailsTable(document, incidentCase.getAssessmentDetails(), "Follow-Up");
	}

	// Method to create assessment details table
	private void createAssessmentDetailsTable(Document document, List<AssessmentDetails> detailsList, String type) {
		Table table = new Table(UnitValue.createPercentArray(new float[] { 50, 50 }));
		table.setWidth(UnitValue.createPercentValue(100));
		table.setMarginBottom(10);

		boolean hasDetails = false; // Flag to check if details exist

		for (AssessmentDetails details : detailsList) {
			if (details.getAssessmentType().equalsIgnoreCase(type)) {
				hasDetails = true; // Set flag if at least one detail matches the type

				table.addCell(createHeaderCell("Assessment Date"));
				table.addCell(createCell(
						details.getAssessmentDate() != null ? details.getAssessmentDate().toString() : "N/A"));

				table.addCell(createHeaderCell("Blood Pressure"));
				table.addCell(createCell(details.getBloodPressure() != null ? details.getBloodPressure() : "N/A"));

				table.addCell(createHeaderCell("Heart Rate"));
				table.addCell(createCell(details.getHeartRate() != null ? details.getHeartRate() : "N/A"));

				table.addCell(createHeaderCell("Description"));
				table.addCell(createCell(details.getDescription() != null ? details.getDescription() : "N/A"));

				table.addCell(createHeaderCell("Injuries Sustained"));
				table.addCell(
						createCell(details.getInjuriesSustained() != null ? details.getInjuriesSustained() : "N/A"));

				table.addCell(createHeaderCell("Level of Consciousness"));
				table.addCell(createCell(
						details.getLevelOfConsciousnes() != null ? details.getLevelOfConsciousnes() : "N/A"));

				table.addCell(createHeaderCell("Mental Status"));
				table.addCell(createCell(details.getMentalStatus() != null ? details.getMentalStatus() : "N/A"));

				table.addCell(createHeaderCell("Mobility Status"));
				table.addCell(createCell(details.getMobilityStatus() != null ? details.getMobilityStatus() : "N/A"));

				table.addCell(createHeaderCell("Oxygen Saturation"));
				table.addCell(
						createCell(details.getOxygenSaturation() != null ? details.getOxygenSaturation() : "N/A"));

				table.addCell(createHeaderCell("Respiratory Rate"));
				table.addCell(createCell(details.getRespiratoryRate() != null ? details.getRespiratoryRate() : "N/A"));

				table.addCell(createHeaderCell("Temperature"));
				table.addCell(createCell(details.getTemperature() != null ? details.getTemperature() : "N/A"));

				table.addCell(createHeaderCell("Patient Condition"));
				table.addCell(
						createCell(details.getPatientCondition() != null ? details.getPatientCondition() : "N/A"));

				table.addCell(createHeaderCell("Incident Description"));
				table.addCell(createCell(
						details.getIncidentDescription() != null ? details.getIncidentDescription() : "N/A"));
			}
		}

		if (hasDetails) {
			document.add(table);
		} else {
			document.add(new Paragraph("No " + type + " assessment details available."));
		}
	}

	// Utility methods to create header and data cells
	private Cell createHeaderCell(String content) {
		return new Cell().add(new Paragraph(content)).setBold().setTextAlignment(TextAlignment.CENTER)
				.setBackgroundColor(ColorConstants.LIGHT_GRAY);
	}

	private Cell createCell(String content) {
		return new Cell().add(new Paragraph(content)).setTextAlignment(TextAlignment.CENTER);
	}
}