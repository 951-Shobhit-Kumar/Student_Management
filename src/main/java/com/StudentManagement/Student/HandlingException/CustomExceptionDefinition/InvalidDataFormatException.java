package com.StudentManagement.Student.HandlingException.CustomExceptionDefinition;

public class InvalidDataFormatException extends RuntimeException{

    private static final String errorCode="INVALID_DATA_FORMAT";
    public InvalidDataFormatException(String message) {
        super(message);
    }

    public String getErrorCode() {
        return errorCode;
    }
}
