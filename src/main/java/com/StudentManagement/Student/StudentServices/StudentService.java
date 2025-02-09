package com.StudentManagement.Student.StudentServices;

import com.StudentManagement.Student.HandlingException.CustomExceptionDefinition.DatabaseException;
import com.StudentManagement.Student.HandlingException.CustomExceptionDefinition.InvalidDataFormatException;
import com.StudentManagement.Student.HandlingException.CustomExceptionDefinition.PhoneNumberAlreadyExistsException;
import com.StudentManagement.Student.HandlingException.CustomExceptionDefinition.ResourceNotFoundException;
import com.StudentManagement.Student.StudentModel.Student;
import com.StudentManagement.Student.StudentRepository.StudentRepository;
import com.StudentManagement.Student.Utility.StudentValidator;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
//@RequiredArgsConstructor
public class StudentService {
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    // Add a new student

    public Student addStudent(Student student) {
        if (!StudentValidator.isValid(student)) {
            throw new InvalidDataFormatException("Please enter valid data for studentName, phoneNumber, Age, and class.");
        }

        if (studentRepository.existsByPhoneNumber(student.getPhoneNumber())) {
            throw new PhoneNumberAlreadyExistsException("The phone number is already in use.");
        }

        return studentRepository.save(student);
    }

    // Get all students
    public List<Student> getAllStudents() {
        try {
            return studentRepository.findAll();
        } catch (DatabaseException e) {
            throw new DatabaseException("Error occurred while fetching student records from Database");
        }

    }

    // Get student by rollNumber
    public Student getStudentByRollNumber(Integer rollNumber) {
        try {
            return studentRepository.findById(Math.toIntExact(rollNumber))
                    .orElseThrow(() -> new ResourceNotFoundException("Student not found with rollNumber: " + rollNumber));
        }
        catch (DatabaseException e) {
            throw new DatabaseException("Error occurred while fetching student records from Database");
        }
    }

    // Update student
    public Student updateStudent(Integer rollNumber, Student updatedStudent) {
        try {
            Student existingStudent = getStudentByRollNumber(rollNumber);
            if (existingStudent==null) {
                throw new ResourceNotFoundException("No student found with given rollNumber: " + rollNumber);
            }
            if (!StudentValidator.isValid(existingStudent)) {
                throw new InvalidDataFormatException("Please enter valid data for studentName, phoneNumber, Age, and class.");
            }
            existingStudent.setStudentName(updatedStudent.getStudentName());
            existingStudent.setStudentAge(updatedStudent.getStudentAge());
            existingStudent.setStudentClass(updatedStudent.getStudentClass());
            existingStudent.setPhoneNumber(updatedStudent.getPhoneNumber());
            return studentRepository.save(existingStudent);
        }
        catch (DatabaseException e) {
            throw new DatabaseException("Error occurred while fetching student records from Database");
        }
    }


    // Delete student
    public boolean deleteStudent(Integer rollNumber) {
        try{
            Student student = getStudentByRollNumber(rollNumber);
            if (student==null) {
                throw new ResourceNotFoundException("No student found with given rollNumber: " + rollNumber);
            }

                studentRepository.delete(student);
                return true;


        }
        catch (DatabaseException e) {
            throw new DatabaseException("Error occurred while fetching student records from Database");
        }

    }

    // Search students by name
    public List<Student> searchStudentsByName(String name) {

        try {

            List<Student> students = studentRepository.findByStudentNameIgnoreCase(name);

            if (students.isEmpty()) {
                throw new ResourceNotFoundException("No student found with name: " + name);
            }

            return students;
        }
        catch (DatabaseException e) {
            throw new DatabaseException("Error occurred while fetching student records from Database");
        }
    }
}

