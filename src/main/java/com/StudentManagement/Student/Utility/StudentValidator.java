package com.StudentManagement.Student.Utility;

import com.StudentManagement.Student.StudentModel.Student;
//this file validate the student based on parameters passed
public class StudentValidator {
    public static boolean isValid(Student student) {
        return !isInvalidPhoneNumber(student.getPhoneNumber()) &&
                !isInValidField(student.getStudentName()) &&
                isInValidAge(student.getStudentAge()) &&
                !isInValidField(student.getStudentClass());
    }

    private static boolean isInvalidPhoneNumber(String phoneNumber) {
        return phoneNumber == null || !phoneNumber.matches("\\d{10}");
    }

    private static boolean isInValidField(String field) {
        return field == null || field.trim().isEmpty();
    }

    private static boolean isInValidAge(String age) {
        try {
            return Integer.parseInt(age) > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
