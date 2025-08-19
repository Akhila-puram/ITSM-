package com.aaseya.ITSM.dto;

import java.util.HashMap;
import java.util.Map;

public class IncidentInvestigationDTO {
	    private String caseId;            
	    private String correctiveActions;    
	    private String preventiveActions;    
	    private String actionRequired;
		public String getCaseId() {
			return caseId;
		}
		public void setCaseId(String caseId) {
			this.caseId = caseId;
		}
		public String getCorrectiveActions() {
			return correctiveActions;
		}
		public void setCorrectiveActions(String correctiveActions) {
			this.correctiveActions = correctiveActions;
		}
		public String getPreventiveActions() {
			return preventiveActions;
		}
		public void setPreventiveActions(String preventiveActions) {
			this.preventiveActions = preventiveActions;
		}
		public String getActionRequired() {
			return actionRequired;
		}
		public void setActionRequired(String actionRequired) {
			this.actionRequired = actionRequired;
		}
		@Override
		public String toString() {
			return "IncidentInvestigationDTO [caseId=" + caseId + ", correctiveActions=" + correctiveActions
					+ ", preventiveActions=" + preventiveActions + ", actionRequired=" + actionRequired + "]";
		}

		
		private Map<String, Object> convertToCamundaVariables(IncidentInvestigationDTO investigationDTO) {
		    Map<String, Object> variables = new HashMap<>();

		    // Convert each field to the format Camunda expects
		    if (investigationDTO.getCorrectiveActions() != null) {
		        variables.put("correctiveActions", "\""+investigationDTO.getCorrectiveActions()+"\"");
		    }
		    if (investigationDTO.getPreventiveActions() != null) {
		        variables.put("preventiveActions", "\""+investigationDTO.getPreventiveActions()+"\"");
		    }
		    if (investigationDTO.getActionRequired() != null) {
		        variables.put("actionRequired", "\""+investigationDTO.getActionRequired()+"\"");
		    }

		    return variables;
		}

}