package com.StudentManagement.Student.StudentModel;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentDto {
    private Integer rollNumber;
    private String studentName;
    private String studentAge;
    private String studentClass;
    private String phoneNumber;

    // Constructor to map Student entity to StudentDto
    public StudentDto(Student student) {
        this.rollNumber = student.getRollNumber();
        this.studentName = student.getStudentName();
        this.studentAge = student.getStudentAge();
        this.studentClass = student.getStudentClass();
        this.phoneNumber = student.getPhoneNumber();
    }

    public static List<StudentDto> convertToDtoList(List<Student> students) {
        List<StudentDto> studentDtos = new ArrayList<>();

        for (Student student : students) {
            StudentDto studentDto = new StudentDto(student);
            studentDtos.add(studentDto);
        }

        return studentDtos;
    }
}