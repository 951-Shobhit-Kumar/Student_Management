package com.StudentManagement.Student.HandlingException.CustomExceptionDefinition;
// PhoneNumberAlreadyExistsException.java
// Custom exception for scenarios where a phone number already exists in the system, typically used during user registration.

public class PhoneNumberAlreadyExistsException extends RuntimeException {

        private static final String ERROR_CODE = "PHONE_NUMBER_ALREADY_EXISTS";

        public PhoneNumberAlreadyExistsException(String message) {
            super(message);
        }

        public String getErrorCode() {
            return ERROR_CODE;
        }
    }

