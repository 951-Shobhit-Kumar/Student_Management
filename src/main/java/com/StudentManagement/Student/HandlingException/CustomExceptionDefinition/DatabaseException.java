package com.StudentManagement.Student.HandlingException.CustomExceptionDefinition;
// DatabaseException.java
// Handles exceptions related to database errors, such as connection failures or query execution issues.

public class DatabaseException  extends RuntimeException{
    private static final String errorCode="DATABASE_ACCESS_ERROR";
    public DatabaseException(String message) {
        super(message);
    }

    public String getErrorCode() {
        return errorCode;
    }
}
