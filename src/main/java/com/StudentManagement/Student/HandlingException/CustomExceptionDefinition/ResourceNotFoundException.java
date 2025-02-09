package com.StudentManagement.Student.HandlingException.CustomExceptionDefinition;

public class ResourceNotFoundException  extends RuntimeException{

    private static final String ERROR_CODE = "RESOURCE_NOT_FOUND_EXCEPTION";

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public String getErrorCode() {
        return ERROR_CODE;
    }
}
