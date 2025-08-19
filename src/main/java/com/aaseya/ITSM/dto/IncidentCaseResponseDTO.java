package com.aaseya.ITSM.dto;

public class IncidentCaseResponseDTO {
    private String status;
    private String message;
    private String businessKey;
    private String taskId;
    private String taskName;
    private String formId;
    private String formKey;
    private String schema;
    private String processId;
    private String processName;
    private long processDefinitionKey;
    
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public long getProcessDefinitionKey() {
		return processDefinitionKey;
	}
	public void setProcessDefinitionKey(long processDefinitionKey) {
		this.processDefinitionKey = processDefinitionKey;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getBusinessKey() {
		return businessKey;
	}
	public void setBusinessKey(String businessKey) {
		this.businessKey = businessKey;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public String getFormId() {
		return formId;
	}
	public void setFormId(String formId) {
		this.formId = formId;
	}
	public String getFormKey() {
		return formKey;
	}
	public void setFormKey(String formKey) {
		this.formKey = formKey;
	}
	public String getSchema() {
		return schema;
	}
	public void setSchema(String schema) {
		this.schema = schema;
	}
	public String getProcessId() {
		return processId;
	}
	public void setProcessId(String processId) {
		this.processId = processId;
	}
	public String getProcessName() {
		return processName;
	}
	public void setProcessName(String processName) {
		this.processName = processName;
	}
    
    
   }