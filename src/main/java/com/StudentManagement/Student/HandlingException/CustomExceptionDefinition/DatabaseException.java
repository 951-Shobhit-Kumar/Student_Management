package com.StudentManagement.Student.HandlingException.CustomExceptionDefinition;

public class DatabaseException  extends RuntimeException{
    private static final String errorCode="DATABASE_ACCESS_ERROR";
    // Constructor that takes the error message and error code
    public DatabaseException(String message) {
        super(message);
    }

    // Getters for error code
    public String getErrorCode() {
        return errorCode;
    }
}
