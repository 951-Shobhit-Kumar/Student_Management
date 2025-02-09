package com.StudentManagement.Student.HandlingException.CustomExceptionDefinition;



import lombok.AllArgsConstructor;
import lombok.*;
import org.springframework.http.HttpStatus;

// ErrorResponse.java
// Represents the structure of the error response sent to the client when an exception occurs, containing status and message details.

@Setter
@Getter
@AllArgsConstructor
public class ErrorResponse {
    private String message;
    private String errorCode;
    private HttpStatus status;
}
