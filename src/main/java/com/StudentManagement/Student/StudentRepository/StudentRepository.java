package com.StudentManagement.Student.StudentRepository;

import com.StudentManagement.Student.StudentModel.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {


    boolean existsByPhoneNumber(String phoneNumber);


    List<Student> findByStudentNameIgnoreCase(String studentName);//Finding student by Student Name


    void deleteByRollNumber(Long rollNumber);//Deleting student  by ID
}

