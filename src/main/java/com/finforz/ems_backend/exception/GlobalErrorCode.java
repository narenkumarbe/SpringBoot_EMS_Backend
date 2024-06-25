package com.finforz.ems_backend.exception;

public class GlobalErrorCode {
    public static final String ERROR_EMPLOYEE_ID_NOT_FOUND = "1000";

        public static final String BAD_REQUEST = "400 Bad Request";
        public static final String UNAUTHORIZED = "401 Unauthorized";
        public static final String FORBIDDEN = "403 Forbidden";
        public static final String NOT_FOUND = "404 Not Found";
        public static final String METHOD_NOT_ALLOWED = "405 Method Not Allowed";
        public static final String REQUEST_TIMEOUT = "408 Request Timeout";
        public static final String TOO_MANY_REQUESTS = "429 Too Many Requests";
        public static final String INTERNAL_SERVER_ERROR = "500 Internal Server Error";
        public static final String NOT_IMPLEMENTED = "501 Not Implemented";
        public static final String BAD_GATEWAY = "502 Bad Gateway";
        public static final String SERVICE_UNAVAILABLE = "503 Service Unavailable";
        public static final String GATEWAY_TIMEOUT = "504 Gateway Timeout";
        public static final String INSUFFICIENT_STORAGE = "507 Insufficient Storage";

  // Database Errors
    public static final String DB_CONNECTION_FAILED = "6000 DB Connection Failed";
    public static final String DB_RESOURCE_NOT_FOUND = "6001 DB Resource Not Found";
    public static final String DB_QUERY_TIMEOUT = "6002 DB Query Timeout";
    public static final String _6003 = "Duplicate email Id";

    // File Writing Errors
    public static final String FILE_WRITE_FAILED = "6010 File Write Failed";
    public static final String FILE_NOT_FOUND = "6011 File Not Found";
    public static final String FILE_READ_ERROR = "6012 File Read Error";
    public static final String FILE_PERMISSION_DENIED = "6013 File Permission Denied";

    // URL Errors
    public static final String INVALID_URL = "6020 Invalid URL";
    public static final String URL_NOT_REACHABLE = "6021 URL Not Reachable";
    public static final String URL_TIMEOUT = "6022 URL Timeout";

    // Resource Errors
    public static final String _6040 = "Resource Not Found";
    public static final String RESOURCE_LOCKED = "6041 Resource Locked";
    public static final String RESOURCE_CONFLICT = "6042 Resource Conflict";
    public static final String RESOURCE_EXHAUSTED = "6043 Resource Exhausted";
    public static final String RESOURCE_UNAVAILABLE = "6044 Resource Unavailable";
}
