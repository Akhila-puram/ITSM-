package com.aaseya.ITSM.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.hc.core5.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import com.aaseya.ITSM.dto.IncidentCaseDTO;
import com.aaseya.ITSM.dto.IncidentCaseDTODashboard;
import com.aaseya.ITSM.dto.IncidentCaseResponseDTO;
import com.aaseya.ITSM.dto.IncidentCommentAttachmentDTO;
import com.aaseya.ITSM.dto.IncidentInvestigationDTO;
import com.aaseya.ITSM.dto.IncidentTypeDTO;
import com.aaseya.ITSM.dto.ItsmResponseDTO;
import com.aaseya.ITSM.dto.MedicationClassDTO;
import com.aaseya.ITSM.dto.RequestFormsDTO;
import com.aaseya.ITSM.dto.StaffDetailsDTO;
import com.aaseya.ITSM.dto.TaskVariables;
import com.aaseya.ITSM.model.AssessmentDetails;
import com.aaseya.ITSM.model.IncidentCase;
import com.aaseya.ITSM.model.PatientDetails;
import com.aaseya.ITSM.service.AssessmentDetailsService;
import com.aaseya.ITSM.service.IncidentCaseService;
import com.aaseya.ITSM.service.IncidentCommentAttachmentService;
import com.aaseya.ITSM.service.IncidentReportPDFGenerator;
import com.aaseya.ITSM.service.IncidentTypeService;
import com.aaseya.ITSM.service.MedicationClassService;
import com.aaseya.ITSM.service.OperateService;
import com.aaseya.ITSM.service.PatientDetailsService;
import com.aaseya.ITSM.service.StaffDetailsService;
import com.aaseya.ITSM.service.StartIncidentCaseService;
import com.aaseya.ITSM.service.SubCategoryService;
import com.aaseya.ITSM.service.TaskListService;
import com.aaseya.ITSM.zeebeworker.SendEmailWorker;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.camunda.zeebe.client.ZeebeClient;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class ITSMController {
	private static final Logger logger = LoggerFactory.getLogger(ITSMController.class);
	 

	@Autowired
	private IncidentCaseService incidentCaseService;

//    @Autowired
//    private LocationService locationService;
	@Autowired
	private SendEmailWorker sendEmailWorker;

	@Autowired
	private PatientDetailsService patientDetailsService;

	@Autowired
	private IncidentTypeService incidentTypeService;

	@Autowired
	private SubCategoryService subCategoryService;
	
	@Autowired
	private IncidentReportPDFGenerator incidentReportPDFGenerator;

	@Autowired
	private StartIncidentCaseService startincidentCaseService;

	@Autowired
	private ZeebeClient zeebeClient;

	@Autowired
	private IncidentCommentAttachmentService incidentCommentAttachmentService;

	@Autowired
	private StaffDetailsService staffDetailsService;

	@Autowired
	private MedicationClassService medicationClassService;

	@Autowired
	private AssessmentDetailsService assessmentDetailsService;

	@Autowired
	private TaskListService taskListService;
	
	@Autowired
	private OperateService operateService;

	// Get the incident details by created by user(nurse/doctor)//
//    @GetMapping("/getincidents")
//    public List<IncidentCaseDTO> getIncidentsByCreatedBy(@RequestParam String createdBy) {
//        return incidentCaseService.getIncidentCasesByCreatedBy(createdBy);
//    }
//    //Get the incident details by created by user(nurse/doctor)//
//    
//    //Get the locations for incidents//
//    @GetMapping("/locations")
//    public ResponseEntity<List<Location>> getAllLocations() {
//        List<Location> locations = locationService.getAllLocations();
//        return ResponseEntity.ok(locations);
//    }
	// Get the locations for incidents//

	//// Get the categories of incidents based on incident type//
	@GetMapping("/incidentType")
	public IncidentTypeDTO getIncidentType(@RequestParam String incidentType) {
		return incidentTypeService.getIncidentTypeWithCategories(incidentType);
	}
	// Get the categories of incidents based on incident type//

	// Get the sub categories of incident based on incident category//
	@GetMapping("/getIncidentSubCategories")
	public List<String> getIncidentSubCategoriesByIncidentCategory(@RequestParam String incidentCategory) {

		return subCategoryService.getIncidentSubCategoriesByIncidentCategory(incidentCategory);
	}
	// Get the sub categories of incident based on incident category//

	// Add the attachement in incident case//
//    @PostMapping("/processIncidentCase")
//	public IncidentCaseResponseDTO processIncidentCase(@RequestBody StartIncidentCaseDTO startIncidentCaseDTO) {
//	    IncidentCaseResponseDTO responseDTO = new IncidentCaseResponseDTO();
//	    try {
//	        // Start the incident case process and get the business key
//	        String businessKey = startincidentCaseService.startIncidentCaseProcess(startIncidentCaseDTO);
// 
//	        // Save the incident case details in the database
//	        boolean result = incidentCaseService.saveIncidentCase(startIncidentCaseDTO, businessKey);
// 
//	        if (result) {
//	            // Set response details for successful case
//	            responseDTO.setStatus("Success");
//	            responseDTO.setMessage("Incident case process started and data saved successfully.");
//	            responseDTO.setBusinessKey(businessKey);
//	        } else {
//	            // Set response details for failed case
//	            responseDTO.setStatus("Failure");
//	            responseDTO.setMessage("Failed to save incident case details.");
//	        }
// 
//	    } catch (Exception e) {
//	        // Handle any exceptions and set failure response
//	        responseDTO.setStatus("Failure");
//	        responseDTO.setMessage("Failed to process incident case: " + e.getMessage());
//	    }
//	    return responseDTO;
//	}
//  //Add the attachement in incident case//
//    //Submit the recommendation for reviewer//  
//    @PostMapping("/submitFinalReview")
//    public ResponseEntity<IncidentCaseResponseDTO> completeCase(@RequestBody CompleteCaseRequestDTO requestDTO) {
//        IncidentCaseResponseDTO responseDTO = new IncidentCaseResponseDTO();
//        try {
//            // Call the service to complete the case
//            incidentCaseService.completeCase(requestDTO);
//
//            // Create a response with success message
//            responseDTO.setBusinessKey(requestDTO.getCaseId());
//            responseDTO.setStatus("Success");
//            responseDTO.setMessage("Case completed successfully");
//
//            return ResponseEntity.ok(responseDTO);
//        } catch (Exception e) {
//            // In case of an error, create an error response
//            responseDTO.setBusinessKey(requestDTO.getCaseId());
//            responseDTO.setStatus("Failure");
//            responseDTO.setMessage("Error completing case: " + e.getMessage());
//            return ResponseEntity.status(500).body(responseDTO);
//        }
//    }
////    @PostMapping("/send-email")
////    public String sendEmail(@RequestBody Emailrequest emailRequest) {
////        // Prepare the variables to send to the job worker
////        HashMap<String, Object> variables = new HashMap<>();
////        variables.put("receiver", emailRequest.getReceiver());
////        variables.put("subject", emailRequest.getSubject());
////        variables.put("body", emailRequest.getBody());
////
////        // Publish a message to trigger the SendEmail job
////        PublishMessageResponse response = zeebeClient.newPublishMessageCommand()
////                .messageName("SendEmail") // This should match the job type
////                .correlationKey(emailRequest.getReceiver()) // Use receiver as correlation key
////                .variables(variables)
////                .send()
////                .join(); // Wait for the response
////
////        return "Email send request sent: " + response.getMessageKey();
////    }
//    @PostMapping("/sendEmail")
//    public String sendEmail(@RequestBody Emailrequest emailRequest) throws MessagingException {
//        // Prepare the variables to send to the job worker
//        Map<String, Object> variables = new HashMap<>();
//        variables.put("receiver", emailRequest.getReceiver());
//        variables.put("subject", emailRequest.getSubject());
//        variables.put("body", emailRequest.getBody());
//
//       sendEmailWorker.sendMail("aaseyaitsm@gmail.com", "ravichandra.misala@aaseya.com", "IncidentRquest", "ITSM");
//
//        return "Email send request sent successfully!";
//    }
//  //Submit the recommendation for reviewer//  

	@PostMapping("saveIncidentCaseDetails")
	public ResponseEntity<String> addIncidentDetails(@RequestBody IncidentCaseDTO incidentCaseDTO) {
		try {
			incidentCaseService.addIncidentDetails(incidentCaseDTO);
			return ResponseEntity.ok("Incident details added successfully.");
		} catch (Exception e) {
			return ResponseEntity.status(500).body("Error adding incident details: " + e.getMessage());
		}
	}

	//// Start Incident Process////
	@PostMapping("/processIncidentCase")
	public IncidentCaseResponseDTO processIncidentCase() {
		return startincidentCaseService.startIncidentCaseProcess();
	}
	//// Start Incident Process////

	// Get formDetails by formID//
	@GetMapping("/getForms")
	public Map<String, Object> getFormDetails(@RequestParam("formId") String formId,
			@RequestParam("processDefinitionKey") String processDefinitionKey) {
		return startincidentCaseService.getFormDetails(formId, processDefinitionKey);
	}
	// Get formDetails by formID//

	// Add Incident Details

	// API to add attachments and final reviewer comment to the incident case

	@PostMapping("/addCommentAndAttachments")
	public String addCommentAndAttachments(@RequestBody IncidentCommentAttachmentDTO commentAttachmentDTO) {
		boolean isUpdated = incidentCommentAttachmentService.addIncidentCommentAndAttachments(commentAttachmentDTO);

		if (isUpdated) {
			return "Incident case d successfully.";
		} else {
			return "Failed to update incident case.";
		}
	}

	// API to get StaffDetails based on Name//
	@GetMapping("/GetStaffDetailsByName/{staffName}")
	public ResponseEntity<List<StaffDetailsDTO>> getStaffByName(@PathVariable String staffName) {
		List<StaffDetailsDTO> staffDetailsList = staffDetailsService.getStaffDetailsByName(staffName);

		if (!staffDetailsList.isEmpty()) {
			return ResponseEntity.ok(staffDetailsList);
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	// Create the api to get medication class

	@GetMapping("/getMedicationClass")
	public ResponseEntity<List<MedicationClassDTO>> getAllMedicationClasses() {
		List<MedicationClassDTO> medicationClasses = medicationClassService.getAllMedicationClasses();
		return ResponseEntity.ok(medicationClasses);
	}

	// Get the incident case details by incidentId
	@GetMapping("/getIncidentCaseById")
	public ResponseEntity<IncidentCaseDTO> getIncidentCaseByCaseId(@RequestParam String caseId) {
		IncidentCaseDTO incidentCaseDTO = incidentCaseService.getIncidentCaseByCaseId(caseId);

		if (incidentCaseDTO != null) {
			return ResponseEntity.ok(incidentCaseDTO);
		} else {
			return ResponseEntity.status(HttpStatus.SC_NOT_FOUND).body(null);
		}
	}

	// api to submit the assessment for patient//
	@PostMapping("/savePatientAssessmentDetails/{incidentCaseId}")
	public ResponseEntity<IncidentCaseResponseDTO> saveAssessmentDetails(
	        @RequestBody AssessmentDetails assessmentDetails, 
	        @PathVariable String incidentCaseId) {

	    // Call the service method and get the response DTO
	    IncidentCaseResponseDTO responseDTO = assessmentDetailsService.saveAssessmentDetails(assessmentDetails, incidentCaseId);

	    return ResponseEntity.ok(responseDTO);
	}
	
	
	@PostMapping("/updateActions/{parentProcessInstanceKey}")
	public ResponseEntity<String> updateIncidentCaseActions(
	        @PathVariable("parentProcessInstanceKey") String parentProcessInstanceKey,
	        @RequestBody IncidentInvestigationDTO investigationDTO) {

	    // Step 1: Get subprocess instance key using parent process instance key
	    String subprocessInstanceKey = operateService.searchProcessInstances(parentProcessInstanceKey, "Subprocess");
	    if (subprocessInstanceKey == null) {
	        return ResponseEntity.status(HttpStatus.SC_NOT_FOUND).body("Subprocess instance not found for parentProcessInstanceKey: " + parentProcessInstanceKey);
	    }

	    // Step 2: Get active task ID from the subprocess instance key
	    String taskId = taskListService.getActiveTaskID(subprocessInstanceKey);
	    if (taskId == null || taskId.isEmpty()) {
	        return ResponseEntity.status(HttpStatus.SC_NOT_FOUND).body("Active task not found for subprocessInstanceKey: " + subprocessInstanceKey);
	    }

	    // Step 3: Convert incoming JSON to Camunda format
	    Map<String, Object> camundaVariables = convertToCamundaVariables(investigationDTO);

	    // Step 4: Complete the task in Camunda using the task ID and variables
	    String taskCompletionResponse = taskListService.CompleteTaskByID(taskId, camundaVariables);
	   

	    // Step 5: Save or update the incident case in the database
	    investigationDTO.setCaseId(parentProcessInstanceKey); // Assuming parentProcessInstanceKey is the caseId
	    incidentCaseService.updateIncidentCaseActions(investigationDTO);

	    return ResponseEntity.ok("Incident case updated and task completed successfully");
	}

	private Map<String, Object> convertToCamundaVariables(IncidentInvestigationDTO investigationDTO) {
	    Map<String, Object> variables = new HashMap<>();

	    if (investigationDTO.getCorrectiveActions() != null) {
	        variables.put("correctiveActions", "\"" + investigationDTO.getCorrectiveActions() + "\"");
	    }
	    if (investigationDTO.getPreventiveActions() != null) {
	        variables.put("preventiveActions", "\"" + investigationDTO.getPreventiveActions() + "\"");
	    }
	    if (investigationDTO.getActionRequired() != null) {
	        variables.put("actionRequired", "\"" + investigationDTO.getActionRequired() + "\"");
	    }

	    return variables;
	}


	

	@GetMapping("/getmyincidentcases")
	public ResponseEntity<List<IncidentCaseDTODashboard>> getIncidentCasesByRoleAndUser(@RequestParam String role,
			@RequestParam String userId) {
		try {
			List<IncidentCaseDTODashboard> incidentCases = incidentCaseService.getIncidentCases(role, userId);
			return ResponseEntity.ok(incidentCases);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(null);
		}
	}

	@PostMapping("/Testingcompleteids")
	public ItsmResponseDTO completeTasks(@RequestParam String processInstanceKey,
	                                     @RequestBody RequestFormsDTO requestFormsDTO) {
	    ItsmResponseDTO ItsmResponseDTO = new ItsmResponseDTO();
	    
	    // Log the incoming request
	    System.out.println("Received request to complete tasks for processInstanceKey: " + processInstanceKey);
	    System.out.println("RequestFormsDTO: " + requestFormsDTO);

	    Map<String, Object> processVariables = new HashMap<>();
	    ObjectMapper mapper = new ObjectMapper();

	    // Convert and add all relevant DTOs into processVariables map
	    processVariables.putAll(mapper.convertValue(requestFormsDTO.getIncidentCreationDTO(),
	            new TypeReference<Map<String, Object>>() {}));
	    processVariables.putAll(mapper.convertValue(requestFormsDTO.getPatientDetailsDTO(),
	            new TypeReference<Map<String, Object>>() {}));
	    processVariables.putAll(mapper.convertValue(requestFormsDTO.getLocationDetailsDTO(),
	            new TypeReference<Map<String, Object>>() {}));
	    
	    // Add incidentId variable to processVariables with the value of processInstanceKey
	    processVariables.put("incidentId", processInstanceKey);

	    // Set variables for the process instance in Camunda
	    zeebeClient.newSetVariablesCommand(Long.parseLong(processInstanceKey))
	               .variables(processVariables)
	               .send()
	               .join();

	    // Fetch active task IDs for the given processInstanceKey
	    List<TaskVariables> tasks = taskListService.getActiveTaskIDs(processInstanceKey);
	    System.out.println("Active task IDs retrieved: " + tasks);

	    if (tasks.isEmpty()) {
	        System.out.println("No active tasks found for processInstanceKey: " + processInstanceKey);
	        ItsmResponseDTO.setStatus("failure");
	        ItsmResponseDTO.setMessage("Task incomplete");
	        return ItsmResponseDTO;
	    }

	    // Complete each task with the same processVariables including incidentId
	    List<String> responses = new ArrayList<>();
	    for (TaskVariables task : tasks) {
	        // Save details based on task type (if needed)
	        if (task.getName().equalsIgnoreCase("Add Patient details")) {
	            PatientDetails patientDetails = new PatientDetails();
	            patientDetails.setPatientId(requestFormsDTO.getPatientDetailsDTO().getPatientId());
	            patientDetails.setName(requestFormsDTO.getPatientDetailsDTO().getName());
	            patientDetails.setAge(requestFormsDTO.getPatientDetailsDTO().getAge());
	            patientDetails.setGender(requestFormsDTO.getPatientDetailsDTO().getGender());
	            patientDetails.setContactNo(requestFormsDTO.getPatientDetailsDTO().getContactNo());
	            patientDetails.setFamilyContactNo(requestFormsDTO.getPatientDetailsDTO().getFamilyContactNo());
	            patientDetailsService.save(patientDetails);
	            System.out.println("Patient details saved successfully: " + patientDetails);
	        }

	        // Complete the task with processVariables including incidentId
	        String completionResponse = taskListService.CompleteTaskByID(task, processVariables);
	        responses.add(completionResponse);
	    }

	    // Save the incident case with relevant information
	    IncidentCase incidentCase = new IncidentCase();
	    incidentCase.setCaseId(processInstanceKey);
	    incidentCase.setIncidentType(requestFormsDTO.getIncidentCreationDTO().getIncidentType());
	    incidentCase.setIncidentCategory(requestFormsDTO.getIncidentCreationDTO().getIncidentCategory());
	    incidentCase.setIncidentSubCategory(requestFormsDTO.getIncidentCreationDTO().getIncidentSubCategory());
	    incidentCase.setMedicationClass(requestFormsDTO.getIncidentCreationDTO().getMedicationClass());
	    incidentCase.setReportedBy(requestFormsDTO.getIncidentCreationDTO().getReportedBy());
	    incidentCase.setIncidentDescription(requestFormsDTO.getIncidentCreationDTO().getIncidentDescription());
	    incidentCase.setInitialActionTaken(requestFormsDTO.getIncidentCreationDTO().getInitialActionTaken());
	    incidentCase.setCreationDate(requestFormsDTO.getIncidentCreationDTO().getCreationDate());
	    incidentCase.setCreationTime(requestFormsDTO.getIncidentCreationDTO().getCreationTime());
	    incidentCase.setIncidentDate(requestFormsDTO.getIncidentCreationDTO().getIncidentDate());
	    incidentCase.setIncidentTime(requestFormsDTO.getIncidentCreationDTO().getIncidentTime());
	    incidentCase.setStatus("open");
	    
	    // Set location details
	    incidentCase.setState(requestFormsDTO.getLocationDetailsDTO().getState());
	    incidentCase.setCity(requestFormsDTO.getLocationDetailsDTO().getCity());
	    incidentCase.setBranch(requestFormsDTO.getLocationDetailsDTO().getBranch());
	    incidentCase.setDepartment(requestFormsDTO.getLocationDetailsDTO().getDepartment());
	    incidentCase.setBlockRoomNo(requestFormsDTO.getLocationDetailsDTO().getBlockRoomNo());
	    incidentCase.setFloor(requestFormsDTO.getLocationDetailsDTO().getFloor());

	    // Link patient details to the incident case
	    PatientDetails linkedPatientDetails = patientDetailsService.findById(requestFormsDTO.getPatientDetailsDTO().getPatientId());
	    if (linkedPatientDetails != null) {
	        incidentCase.setPatientDetails(linkedPatientDetails);
	    }

	    // Save the incident case
	    incidentCaseService.save(incidentCase);

	    System.out.println("Task completion responses: " + responses);
	    ItsmResponseDTO.setStatus("success");
	    ItsmResponseDTO.setMessage("Tasks completed successfully");
	    return ItsmResponseDTO;
	}



	
	 // Endpoint to search for subprocess instance by parent process ID
    @PostMapping("/searchProcessInstance")
    public ResponseEntity<String> searchProcessInstance(@RequestParam String parentId) {
        String subprocessInstanceKey = operateService.searchProcessInstances(parentId, "searchSubProcess");

        if (subprocessInstanceKey != null) {
            return ResponseEntity.ok(subprocessInstanceKey);
        } else {
            return ResponseEntity.status(HttpStatus.SC_NOT_FOUND)
                    .body("No subprocess instance found for the given parent ID.");
        }
    }
    
    
    @PostMapping("/investigationFindings/{parentProcessInstanceKey}")
    public ResponseEntity<String> submitInvestigationFindings(
            @PathVariable String parentProcessInstanceKey,
            @RequestBody Map<String, String> reviewData) {

        try {
            String result = incidentCaseService.processInvestigationFindings(parentProcessInstanceKey, reviewData);
            return ResponseEntity.ok(result);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred: " + e.getMessage());
        }
    }
    
    
    @GetMapping("/generate-report/{incidentId}")
	public ResponseEntity<String> generateReport(@PathVariable String incidentId) {
	    try {
	        IncidentCase incidentCase = incidentReportPDFGenerator.generateIncidentCaseById(incidentId);
	        if (incidentCase == null) {
	            return ResponseEntity.status(HttpStatus.SC_NOT_FOUND)
	                                 .body("Incident case not found for ID: " + incidentId);
	        }
	        incidentReportPDFGenerator.generateIncidentReport(incidentId);
	        return ResponseEntity.ok("PDF report generated successfully.");
	    } catch (Exception e) {
	        logger.error("Error generating report for incident ID {}: {}", incidentId, e.getMessage(), e);
	        return ResponseEntity.status(HttpStatus.SC_INTERNAL_SERVER_ERROR)
	                             .body("Error generating report. Please try again later.");
	    }
	}
}
