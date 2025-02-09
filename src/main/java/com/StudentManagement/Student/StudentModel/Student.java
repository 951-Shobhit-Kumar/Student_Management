package com.StudentManagement.Student.StudentModel;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer rollNumber; // Auto-generated ID

    @Column(nullable = false)
    private String studentName;

    @Column(nullable = false)
    private String studentAge;

    @Column(nullable = false)
    private String studentClass;

    @Column(nullable = false, unique = true)
    private String phoneNumber;
}


