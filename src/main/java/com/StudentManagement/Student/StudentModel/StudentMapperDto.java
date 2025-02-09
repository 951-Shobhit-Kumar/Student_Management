package com.StudentManagement.Student.StudentModel;

import java.util.*;
import java.util.stream.*;

public class StudentMapperDto {
    private StudentMapperDto() {
        // Private constructor to prevent instantiation
    }

    public static Student mapToEntity(StudentDto studentDto) {
        if (studentDto == null) {
            return null;
        }
        return new Student(
                studentDto.getRollNumber(),
                studentDto.getStudentName(),
                studentDto.getStudentAge(),
                studentDto.getStudentClass(),
                studentDto.getPhoneNumber()
        );
    }

    public static StudentDto mapToDto(Student student) {
        if (student == null) {
            return null;
        }
        return new StudentDto(
                student.getRollNumber(),
                student.getStudentName(),
                student.getStudentAge(),
                student.getStudentClass(),
                student.getPhoneNumber()
        );
    }

    public static List<StudentDto> mapToDtoList(List<Student> students) {
        if (students == null || students.isEmpty()) {
            return List.of();
        }
        return students.stream()
                .map(StudentMapperDto::mapToDto)
                .collect(Collectors.toList());
    }
}
