package com.StudentManagement.Student.HandlingException.CustomExceptionDefinition;

public class InvalidDataFormatException extends RuntimeException{

    private static final String errorCode="INVALID_DATA_FORMAT";
    // Constructor that takes the error message and error code
    public InvalidDataFormatException(String message) {
        super(message);
    }

    // Getters for error code
    public String getErrorCode() {
        return errorCode;
    }
}
