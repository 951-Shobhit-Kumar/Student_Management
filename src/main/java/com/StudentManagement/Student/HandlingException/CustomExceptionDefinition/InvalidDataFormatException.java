package com.StudentManagement.Student.HandlingException.CustomExceptionDefinition;
// InvalidDataFormatException.java
// Custom exception class to handle cases where input data format is invalid or cannot be processed.

public class InvalidDataFormatException extends RuntimeException{

    private static final String errorCode="INVALID_DATA_FORMAT";
    public InvalidDataFormatException(String message) {
        super(message);
    }

    public String getErrorCode() {
        return errorCode;
    }
}
