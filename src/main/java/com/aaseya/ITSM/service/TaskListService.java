package com.aaseya.ITSM.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.aaseya.ITSM.OMSConstant;
import com.aaseya.ITSM.dto.AccessToken;
import com.aaseya.ITSM.dto.FormVariables;
import com.aaseya.ITSM.dto.IncidentCreationDTO;
import com.aaseya.ITSM.dto.IncidentInvestigationDTO;
import com.aaseya.ITSM.dto.RequestFormsDTO;
import com.aaseya.ITSM.dto.TaskVariables;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.camunda.zeebe.client.ZeebeClient;

@Service
public class TaskListService {

	Logger logger = LoggerFactory.getLogger(this.getClass());

//  @Value("camunda.client.id")
//  private String clientId;
//  
//  @Value("camunda.client.id")
//  private String clientSecret;
//  
//  @Value("camunda.grant.type")
//  private String grantType;
//  
//  @Value("camunda.keycloak.id")
//  private String keycloakId;
//  
//  @Value("camunda.keycloak.url")
//  private String keycloakUrl;

	@Autowired
	private ZeebeClient zeebeClient;

	public String getTasklistToken() {
		String token = "";

		RestTemplate restTemplate = new RestTemplate();
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

			// Creating the form data to send in the POST request
			MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
			map.add("client_secret", OMSConstant.CLIENT_SECRET);
			map.add("client_id", OMSConstant.CLIENT_ID);
			map.add("grant_type", OMSConstant.GRANT_TYPE);
			map.add("audience", OMSConstant.AUDIENCE); // Added audience here

			// Creating the HTTP entity with headers and form data
			HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

			// Sending the request to get the access token
			ResponseEntity<AccessToken> response = restTemplate.exchange(OMSConstant.TOKEN_URL, HttpMethod.POST,
					request, AccessToken.class);

			// Extract the access token from the response
			token = response.getBody().getAccess_token();
			System.out.println("token: " + token);
		} catch (Exception eek) {
			logger.info("** Exception: " + eek.getMessage());
		}

		return token;
	}

	public String getActiveTaskID(String processID) {
		String taskID = "";
		RestTemplate restTemplate = new RestTemplate();

		try {
			// Set request factory with a custom timeout
			HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
			requestFactory.setConnectTimeout(0); // Set your preferred timeout
			restTemplate.setRequestFactory(requestFactory);

			// Set up headers including authorization
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			String authToken = "Bearer " + getTasklistToken(); // Retrieve task list token
			headers.add("Authorization", authToken);

			// Prepare JSON request body
			String requestJson = "{\"taskState\":\"CREATED\",\"processInstanceKey\":\"" + processID + "\"}";
			HttpEntity<String> entity = new HttpEntity<>(requestJson, headers);

			// Send the POST request
			ResponseEntity<TaskVariables[]> resEntity = restTemplate.exchange(OMSConstant.TASKS_SEARCH_URL,
					HttpMethod.POST, entity, TaskVariables[].class);

			// Extract response body
			TaskVariables[] tasks = resEntity.getBody();

			// Return the taskID of the first matching task
			if (tasks != null && tasks.length > 0) {
				taskID = tasks[0].getId(); // Get taskID of the first task
			}

		} catch (Exception e) {
			logger.info("Exception: " + e.getMessage());
		}

		return taskID;
	}

	public String CompleteTaskByID(TaskVariables taskvariable, Map<String, Object> map) {
		String uri = OMSConstant.BASE_TASK_URL + taskvariable.getId() + OMSConstant.COMPLETE_URL;
		System.out.println("taskid" + taskvariable.getId());

		ResponseEntity<String> response = null;
//    logger.info("Complete request" + variables.toString().contains("Approve"));

		RestTemplate restTemplate = new RestTemplate();
		try {
			// zeebeClient.newUserTaskCompleteCommand(Long.parseLong(taskvariable.getId())).variables(map).send().join();
			HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
			requestFactory.setConnectTimeout(0);
			// requestFactory.setReadTimeout(0);
			restTemplate.setRequestFactory(requestFactory);
			logger.info("complete task URI::: " + uri);
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			String AuthToken = "Bearer " + getTasklistToken();
			headers.add("Authorization", AuthToken);

			List<Map<String, Object>> result = new ArrayList<>();
			ObjectMapper objectMapper = new ObjectMapper();
//			for (Map.Entry<String, Object> entry : variables.entrySet()) {
//				Map<String, Object> keyValuePair = new HashMap<String, Object>();
//				if(entry.getValue() instanceof String)
//					keyValuePair = Map.of("name", entry.getKey(), "value", "\"" + entry.getValue() + "\"");
//				else
//					keyValuePair = Map.of("name", entry.getKey(), "value", entry.getValue());
//				System.out.println("key Value : " + keyValuePair);
//				result.add(keyValuePair);
//			}
//			String formattedInput = objectMapper.writeValueAsString(result);
//			System.out.println("Fecthing the form variables" + formattedInput);
//			// {"blockRoomNo":"B12","floor":"2","branch":"Main
//			// Hospital","department":"Emergency","city":"New York","state":"NY"}
//			// requestJson = objectMapper.writeValueAsString(variables);
//			String requestJson = formattedInput.replace("\"", "\\\"").replace("\\\\","\\\\\\");
//			requestJson = "\"{\\\"variables\\\": " + requestJson + "}\"";
			String requestJson = "";
//			if (taskvariable.getName().equalsIgnoreCase("Create Incident")) {
//				IncidentCreationDTO incidentCreationDTO = requestFormsDTO.getIncidentCreationDTO();
//				//requestJson = "{\"variables\": [{\"name\": \"creationDate\",\"value\": \"\\\"hello\\\"\"}]}";
//				requestJson = incidentCreationDTO.toFormattedJson();
//				requestJson = "\"{\\\"variables\\\": " + requestJson + "}\"";

//			} else if (variables.toString().contains("approved")) {
//				requestJson = "{\"variables\": [{\"name\": \"approved\",\"value\": true}]}";
//			} else {
//				requestJson = "{\"variables\": [{\"name\": \"ProcessID\",\"value\": \"\\\"hello\\\"\"}]}";
//			}

			System.out.println("Fecthing the variables" + requestJson);
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<String> entity = new HttpEntity<String>(requestJson, headers);

			logger.info("BBB:" + entity.getBody());
			response = restTemplate.exchange(uri, HttpMethod.PATCH, entity, String.class);

			logger.info("hello");
		} catch (Exception eek) {
			logger.info("** Exception: " + eek.getMessage());
		}
		return response.getBody();
	}

	public ArrayList<TaskVariables> GetOpenOrders(String process) {

		if (process.equals("")) {
			process = "Order";
		}

		ResponseEntity<TaskVariables[]> Resentity = null;
		ArrayList<TaskVariables> Task1 = new ArrayList<TaskVariables>();

		RestTemplate restTemplate = new RestTemplate();
		try {

			HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
			requestFactory.setConnectTimeout(0);
			// requestFactory.setReadTimeout(0);
			restTemplate.setRequestFactory(requestFactory);

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			String AuthToken = "Bearer " + getTasklistToken();
			headers.add("Authorization", AuthToken);

			String requestJson = "{\"taskState\":\"CREATED\",\"includeVariables\": [\r\n" + "    {\r\n"
					+ "      \"name\": \"firstName\"\r\n" + "      },\r\n" + "       {\r\n"
					+ "      \"name\": \"orderID\"\r\n" + "      },\r\n" + "      {\r\n"
					+ "        \"name\":\"status\"\r\n" + "      }]}";

			HttpEntity<String> entity = new HttpEntity<String>(requestJson, headers);
			Resentity = restTemplate.exchange(OMSConstant.TASKS_SEARCH_URL, HttpMethod.POST, entity,
					TaskVariables[].class);
			TaskVariables[] task = Resentity.getBody();
			logger.info("Fetch all orders" + Resentity.getBody());
			for (int i = 0; i < task.length; i++) {

				// logger.info("result " + i + " " + task[i]); String taskid =
				task[i].getId();
				String processID = task[i].getProcessInstanceKey();

				if (task[i].getProcessName().contains(process)
						&& (task[i].getTaskState() == null || task[i].getTaskState().equalsIgnoreCase("CREATED"))) {

					Task1.add(task[i]);
					logger.info("Process ID" + processID + "State " + task[i].getState() + "  Task State"
							+ task[i].getTaskState());
				}
			}

		} catch (Exception e) {
			logger.info("exception in get tasks " + e.getMessage());
		}
		return Task1;
	}

	public String getKeyCloakToken() {
		String token = "";
		String uri = "http://localhost:18080/auth/realms/camunda-platform/protocol/openid-connect/token";
		logger.info(" inside Patch ::" + uri);
		RestTemplate restTemplate = new RestTemplate();
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
			MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();

			map.add("client_secret", OMSConstant.KEYCLOAK_CLIENT_SECRET);
			map.add("client_id", OMSConstant.KEYCLOAK_CLIENT_ID);
			map.add("grant_type", OMSConstant.GRANT_TYPE);

			HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map,
					headers);

			ResponseEntity<AccessToken> response = restTemplate.exchange(uri, HttpMethod.POST, request,
					AccessToken.class);
			token = response.getBody().getAccess_token();

			logger.info(" accss toekn ::: " + response.getBody());
			logger.info("Result - status (" + response.getStatusCode() + ") has body: " + response.hasBody());
		} catch (Exception eek) {
			logger.info("** Exception: " + eek.getMessage());
		}

		return token;
	}

	public TaskVariables[] GetOpenOrders(String process, String userGroup) {

		if (process.equals("")) {
			process = "Order";
		}
		TaskVariables[] task = null;
		ResponseEntity<TaskVariables[]> Resentity = null;
		ArrayList<TaskVariables> Task1 = new ArrayList<TaskVariables>();

		RestTemplate restTemplate = new RestTemplate();

		try {

			HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();

			requestFactory.setConnectTimeout(0);
			// requestFactory.setReadTimeout(0);
			restTemplate.setRequestFactory(requestFactory);

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);

			String AuthToken = "Bearer " + getTasklistToken();
			headers.add("Authorization", AuthToken);

			String requestJson = "{\"state\":\"CREATED\",\"candidateGroup\":\"" + userGroup + "\"}";

			logger.info("----requestJson-->" + requestJson);

			HttpEntity<String> entity = new HttpEntity<String>(requestJson, headers);

			Resentity = restTemplate.exchange(OMSConstant.TASKS_SEARCH_URL, HttpMethod.POST, entity,
					TaskVariables[].class);

			task = Resentity.getBody();

		} catch (Exception e) {
			logger.info("exception in get tasks " + e.getMessage());
		}
		return task;
	}

	public void assignTask(String ProcessID, String taskID, String userID) {

		RestTemplate restTemplate = new RestTemplate();
		try {

			HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
			requestFactory.setConnectTimeout(0);
			// requestFactory.setReadTimeout(0);
			restTemplate.setRequestFactory(requestFactory);
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			String AuthToken = "Bearer " + getTasklistToken();
			headers.add("Authorization", AuthToken);

			String requestJson = "{\"assignee\":\"" + userID + "\" , \"allowOverrideAssignment\":true}";
			HttpEntity<String> entity = new HttpEntity<String>(requestJson, headers);

			// ResponseEntity<TaskVariables> resEntity =
			restTemplate.exchange(OMSConstant.TASKS_ASSIGN_URL + taskID + "/assign", HttpMethod.PATCH, entity,
					TaskVariables.class);
			// System.out.println("assign task response: "+ resEntity.getBody());
//	      TaskVariables[] task = resEntity.getBody();
//	      for (int i = 0; i < task.length; i++) {
//	        if (task[i].getProcessInstanceKey().equals(ProcessID)
//	            && task[i].getTaskState().equals("CREATED")) {
//	          logger.info(" Match found at " + i + task[i].getName());
//	          TaskID += task[i].getId();
////	        		  + ",";
//	        }
//	      }

		} catch (Exception eek) {
			logger.info("** Exception: " + eek.getMessage());
		}

	}

	public TaskVariables getTaskById(String taskId) {
		RestTemplate restTemplate = new RestTemplate();
		TaskVariables taskVariables = null;

		try {
			// Set request factory with a custom timeout
			HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
			requestFactory.setConnectTimeout(0); // Set your preferred timeout
			restTemplate.setRequestFactory(requestFactory);

			// Set up headers including authorization
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			String authToken = "Bearer " + getTasklistToken(); // Retrieve task list token
			headers.add("Authorization", authToken);

			// Send the GET request
			HttpEntity<String> entity = new HttpEntity<>(headers);
			ResponseEntity<TaskVariables> resEntity = restTemplate.exchange(OMSConstant.GET_TASK_URL + taskId,
					HttpMethod.GET, entity, TaskVariables.class);

			// Extract response body
			taskVariables = resEntity.getBody();

		} catch (Exception e) {
			logger.info("Exception: " + e.getMessage());
		}

		return taskVariables;
	}

	public FormVariables getFormById(String formId, String processDefinitionKey, Integer version) {
		RestTemplate restTemplate = new RestTemplate();
		FormVariables formVariables = null;

		try {
			// Set request factory with a custom timeout
			HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
			requestFactory.setConnectTimeout(0); // Set your preferred timeout
			restTemplate.setRequestFactory(requestFactory);

			// Set up headers including authorization
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			String authToken = "Bearer " + getTasklistToken(); // Retrieve task list token
			headers.add("Authorization", authToken);

			// Send the GET request
			HttpEntity<String> entity = new HttpEntity<>(headers);
			String url = OMSConstant.GET_FORM_URL + formId + "?processDefinitionKey=" + processDefinitionKey;
			if (version != null) {
				url += "&version=" + version;
			}
			ResponseEntity<FormVariables> resEntity = restTemplate.exchange(url, HttpMethod.GET, entity,
					FormVariables.class);

			// Extract response body
			formVariables = resEntity.getBody();

		} catch (Exception e) {
			logger.info("Exception: " + e.getMessage());
		}

		return formVariables;
	}

	public List<String> CompleteTasksByIDs(List<List<TaskVariables>> list, Map<String, Object> variables) {
		List<String> responses = new ArrayList<>();

		for (List<TaskVariables> id : list) {
			String uri = OMSConstant.BASE_TASK_URL + id + OMSConstant.COMPLETE_URL;
			ResponseEntity<String> response = null;
			logger.info("Complete Variables: " + variables);
			RestTemplate restTemplate = new RestTemplate();
			try {
				HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
				requestFactory.setConnectTimeout(0);
				restTemplate.setRequestFactory(requestFactory);
				logger.info("Complete task URI: " + uri);

				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.APPLICATION_JSON);
				String authToken = "Bearer " + getTasklistToken();
				headers.add("Authorization", authToken);

				// Create the variable structure as expected by Camunda
				List<Map<String, Object>> camundaVariables = new ArrayList<>();
				for (Map.Entry<String, Object> entry : variables.entrySet()) {
					Map<String, Object> variable = new HashMap<>();
					variable.put("name", entry.getKey());
					variable.put("value", entry.getValue());
					camundaVariables.add(variable);
				}

				// Prepare the request body
				Map<String, Object> requestBody = new HashMap<>();
				requestBody.put("variables", camundaVariables);

				// Convert the request body to JSON
				ObjectMapper mapper = new ObjectMapper();
				String requestJson = mapper.writeValueAsString(requestBody);
				logger.info("JSON request: " + requestJson);

				HttpEntity<String> entity = new HttpEntity<>(requestJson, headers);
				logger.info("Request Body: " + entity.getBody());

				response = restTemplate.exchange(uri, HttpMethod.PATCH, entity, String.class);
				logger.info("Task ID: " + id + " completed successfully.");

				responses.add(response.getBody());

			} catch (Exception e) {
				logger.info("** Exception while completing task ID " + id + ": " + e.getMessage());
				responses.add("Error: " + e.getMessage());
			}
		}

		return responses;
	}

	// Method to get active task IDs based on processInstanceKey
	public List<TaskVariables> getActiveTaskIDs(String processInstanceKey) {
		List<TaskVariables> tasks = new ArrayList<>();
		RestTemplate restTemplate = new RestTemplate();

		try {
			// Set request factory with a custom timeout
			HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
			requestFactory.setConnectTimeout(0); // Set your preferred timeout
			restTemplate.setRequestFactory(requestFactory);

			// Set up headers including authorization
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			String authToken = "Bearer " + getTasklistToken(); // Retrieve task list token
			headers.add("Authorization", authToken);

			// Prepare JSON request body
			String requestJson = "{\"taskState\":\"CREATED\",\"processInstanceKey\":\"" + processInstanceKey + "\"}";
			HttpEntity<String> entity = new HttpEntity<>(requestJson, headers);

			// Log the request being made
			logger.info("Request to fetch active tasks for processInstanceKey: " + processInstanceKey);

			// Send the POST request
			ResponseEntity<TaskVariables[]> resEntity = restTemplate.exchange(OMSConstant.TASKS_SEARCH_URL,
					HttpMethod.POST, entity, TaskVariables[].class);

			// Extract response body
			TaskVariables[] tasksArray = resEntity.getBody();
			tasks = Arrays.asList(tasksArray);
			// Log the response from the API
			logger.info("Tasks fetched from API: " + tasks);

		} catch (Exception e) {
			logger.info("Exception occurred while fetching active tasks: " + e.getMessage());
		}

		return tasks;
	}

	// original complete task by id single//
		public String CompleteTaskByID(String id, Map<String, Object> variables) {
		    String uri = OMSConstant.BASE_TASK_URL + id + OMSConstant.COMPLETE_URL;

		    ResponseEntity<String> response = null;
		    logger.info("Complete Variables: " + variables);

		    RestTemplate restTemplate = new RestTemplate();
		    try {
		        // Set up request factory with no timeout
		        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
		        requestFactory.setConnectTimeout(0);
		        restTemplate.setRequestFactory(requestFactory);

		        // Set up headers
		        HttpHeaders headers = new HttpHeaders();
		        headers.setContentType(MediaType.APPLICATION_JSON);
		        String authToken = "Bearer " + getTasklistToken();  // Assumes getTasklistToken provides a valid token
		        headers.add("Authorization", authToken);

		        // Prepare request body
		        ObjectMapper mapper = new ObjectMapper();
		        List<Map<String, Object>> variableList = new ArrayList<>();
		        
		        // Convert the map of variables to the format Camunda expects
		        variables.forEach((key, value) -> {
		            Map<String, Object> varMap = new HashMap<>();
		            varMap.put("name", key);
		            varMap.put("value", value);
		            variableList.add(varMap);
		        });

		        // Wrap the variables array inside an object with a key "variables"
		        Map<String, Object> requestBody = new HashMap<>();
		        requestBody.put("variables", variableList);

		        // Convert to JSON
		        String requestJson = mapper.writeValueAsString(requestBody);
		        logger.info("JSON request: " + requestJson);

		        // Create HTTP entity with headers and body
		        HttpEntity<String> entity = new HttpEntity<>(requestJson, headers);

		        // Send PATCH request
		        response = restTemplate.exchange(uri, HttpMethod.PATCH, entity, String.class);
		        logger.info("Response: " + response.getBody());

		    } catch (Exception e) {
		        logger.error("Exception: " + e.getMessage(), e);
		        return "Error completing task: " + e.getMessage();
		    }

		    return response.getBody();
		}

		// original complete task by id single//
	
	 public Map<String, Object> convertToCamundaVariables(IncidentInvestigationDTO investigationDTO) {
	        // Create the variable list that Camunda expects
	        List<Map<String, Object>> variableList = new ArrayList<>();

	        // Add correctiveActions
	        if (investigationDTO.getCorrectiveActions() != null) {
	            Map<String, Object> correctiveActionsMap = new HashMap<>();
	            correctiveActionsMap.put("name", "correctiveActions");
	            correctiveActionsMap.put("value", "\"" + investigationDTO.getCorrectiveActions() + "\"");
	            variableList.add(correctiveActionsMap);
	        }

	        // Add preventiveActions
	        if (investigationDTO.getPreventiveActions() != null) {
	            Map<String, Object> preventiveActionsMap = new HashMap<>();
	            preventiveActionsMap.put("name", "preventiveActions");
	            preventiveActionsMap.put("value", "\"" + investigationDTO.getPreventiveActions() + "\"");
	            variableList.add(preventiveActionsMap);
	        }

	        // Add actionRequired
	        if (investigationDTO.getActionRequired() != null) {
	            Map<String, Object> actionRequiredMap = new HashMap<>();
	            actionRequiredMap.put("name", "actionRequired");
	            actionRequiredMap.put("value", "\"" + investigationDTO.getActionRequired() + "\"");
	            variableList.add(actionRequiredMap);
	        }

	        // Wrap the list inside a map with the "variables" key
	        Map<String, Object> requestBody = new HashMap<>();
	        requestBody.put("variables", variableList);

	        return requestBody;
	    }
}
