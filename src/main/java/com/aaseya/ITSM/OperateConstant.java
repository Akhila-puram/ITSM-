package com.aaseya.ITSM;

public class OperateConstant {
	 
    // Base configuration (change this if the region or cluster ID changes)
    public static final String CAMUNDA_OPERATE_CLUSTER_BASE = "https://ont-1.operate.camunda.io/3b71debe-5479-49c9-b8a3-aaf54aab9bd4                ";
 
    // OAuth configuration
    public static final String CLIENT_ID = "1_p5roTUQ7r5Jl-Fy5DCbnxXmdn-Y8kI";
    public static final String CLIENT_SECRET = "Gi9hAu7V9~6m5UHfD25xEvQ2uj~tLce0Nj1TgZVoFySHWwi3z5fuTQBQlYw2cmLI";
    public static final String TOKEN_URL = "https://login.cloud.camunda.io/oauth/token";
    public static final String GRANT_TYPE = "client_credentials";
    public static final String AUDIENCE = "operate.camunda.io";
 
    // Keycloak config
    public static final String KEYCLOAK_CLIENT_ID = "aaseyainspectionsolution";
    public static final String KEYCLOAK_CLIENT_SECRET = "nTJJR3VDiLS0GQGySp2aBcNUUHRQIUVn";
 
    // Operate API URLs
    public static final String SEARCH_PROCESS_INSTANCES = CAMUNDA_OPERATE_CLUSTER_BASE + "/v1/process-instances/search";
    public static final String GET_PROCESS_INSTANCE_BY_KEY = CAMUNDA_OPERATE_CLUSTER_BASE + "/v1/process-instances";
    public static final String GET_VARIABLES_BY_INSTANCE_ID = CAMUNDA_OPERATE_CLUSTER_BASE + "/v1/variables/search";
}
 
	
	
//	public static String CLIENT_ID = "operate";
//    public static String TOKEN_URL = "http://10.13.1.180:18080/auth/realms/camunda-platform/protocol/openid-connect/token";
//    public static String CLIENT_SECRET = "XALaRPl5qwTEItdwCMiPS62nVpKs7dL7";
//    public static String GRANT_TYPE = "client_credentials";
//    public static String SEARCH_PROCESS_INSTANCES = "http://10.13.1.180:8081/v1/process-instances/search";
//    public static String Get_PROCESS_INSTANCE_BY_KEY = "http://10.13.1.180:8081/v1/process-instances";
//    public static String KEYCLOAK_CLIENT_ID = "aaseyainspectionsolution";
//    public static String KEYCLOAK_CLIENT_SECRET = "nTJJR3VDiLS0GQGySp2aBcNUUHRQIUVn";
	
//}