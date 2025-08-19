package com.aaseya.ITSM.dto;

import java.util.List;
import java.util.Map;

public class CompleteTasksRequest {
    private List<String> taskIds;
    private Map<String, Object> variables;

    // Getters and Setters

    public List<String> getTaskIds() {
        return taskIds;
    }

    public void setTaskIds(List<String> taskIds) {
        this.taskIds = taskIds;
    }

    public Map<String, Object> getVariables() {
        return variables;
    }

    public void setVariables(Map<String, Object> variables) {
        this.variables = variables;
    }
}

