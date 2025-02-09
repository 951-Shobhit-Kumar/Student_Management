package com.StudentManagement.Student.HandlingException.CustomExceptionDefinition;
// ResourceNotFoundException.java
// Represents an exception thrown when a requested resource (e.g., a user or item) is not found in the database.

public class ResourceNotFoundException  extends RuntimeException{

    private static final String ERROR_CODE = "RESOURCE_NOT_FOUND_EXCEPTION";

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public String getErrorCode() {
        return ERROR_CODE;
    }
}
