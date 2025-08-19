package com.aaseya.ITSM;


public class OMSConstant {
	 
    // Base configuration (change only here when needed)
    public static final String CAMUNDA_CLUSTER_BASE = "https://ont-1.tasklist.camunda.io/3b71debe-5479-49c9-b8a3-aaf54aab9bd4";
 
    // OAuth configuration
    public static final String CLIENT_ID = "1_p5roTUQ7r5Jl-Fy5DCbnxXmdn-Y8kI";
    public static final String CLIENT_SECRET = "Gi9hAu7V9~6m5UHfD25xEvQ2uj~tLce0Nj1TgZVoFySHWwi3z5fuTQBQlYw2cmLI";
    public static final String TOKEN_URL = "https://login.cloud.camunda.io/oauth/token";
    public static final String GRANT_TYPE = "client_credentials";
    public static final String AUDIENCE = "tasklist.camunda.io";
 
    // Tasklist URLs
    public static final String TASKS_ASSIGN_URL = CAMUNDA_CLUSTER_BASE + "/v1/tasks/";
    public static final String TASKS_SEARCH_URL = CAMUNDA_CLUSTER_BASE + "/v1/tasks/search";
    public static final String BASE_TASK_URL = CAMUNDA_CLUSTER_BASE + "/v1/tasks/";
    public static final String GET_TASK_URL = CAMUNDA_CLUSTER_BASE + "/v1/tasks/";
    public static final String GET_FORM_URL = CAMUNDA_CLUSTER_BASE + "/v1/forms/";
    public static final String COMPLETE_URL = "/complete";
    public static final String SEARCH_TASK_VARIABLES = "/variables/search";
 
    // Keycloak config
    public static final String KEYCLOAK_CLIENT_ID = "aaseyainspectionsolution";
    public static final String KEYCLOAK_CLIENT_SECRET = "nTJJR3VDiLS0GQGySp2aBcNUUHRQIUVn";
}
 
//    
 
//	public static final String TASKS_ASSIGN_URL = "http://10.13.1.180:8082/v1/tasks/";
//	public static String CLIENT_ID = "tasklist";
//	public static String TOKEN_URL = "http://10.13.1.180:18080/auth/realms/camunda-platform/protocol/openid-connect/token";
//	public static String CLIENT_SECRET = "XALaRPl5qwTEItdwCMiPS62nVpKs7dL7";
//	public static String GRANT_TYPE = "client_credentials";
//	public static String TASKS_SEARCH_URL = "http://10.13.1.180:8082/v1/tasks/search";
//	public static String BASE_TASK_URL = "http://10.13.1.180:8082/v1/tasks/";
//	public static String COMPLETE_URL = "/complete";
//	public static String SEARCH_TASK_VARIABLES = "/variables/search";
//	public static String GET_TASK_URL = "http://10.13.1.180:8082/v1/tasks/";
//	public static String GET_FORM_URL = "http://10.13.1.180:8082/v1/forms/";
//	public static String KEYCLOAK_CLIENT_ID = "aaseyainspectionsolution";
//	public static String KEYCLOAK_CLIENT_SECRET = "nTJJR3VDiLS0GQGySp2aBcNUUHRQIUVn";
//}
