package com.StudentManagement.Student.HandlingException.CustomExceptionDefinition;



import lombok.AllArgsConstructor;
import lombok.*;
import org.springframework.http.HttpStatus;

@Setter
@Getter
@AllArgsConstructor
public class ErrorResponse {
    private String message;
    private String errorCode;
    private HttpStatus status;
}
