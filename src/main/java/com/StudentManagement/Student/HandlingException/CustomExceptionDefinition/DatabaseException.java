package com.StudentManagement.Student.HandlingException.CustomExceptionDefinition;

public class DatabaseException  extends RuntimeException{
    private static final String errorCode="DATABASE_ACCESS_ERROR";
    public DatabaseException(String message) {
        super(message);
    }

    public String getErrorCode() {
        return errorCode;
    }
}
