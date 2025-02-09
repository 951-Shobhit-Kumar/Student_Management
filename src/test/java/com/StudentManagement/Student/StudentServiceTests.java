package com.StudentManagement.Student.StudentServices;

import com.StudentManagement.Student.HandlingException.CustomExceptionDefinition.InvalidDataFormatException;
import com.StudentManagement.Student.HandlingException.CustomExceptionDefinition.PhoneNumberAlreadyExistsException;
import com.StudentManagement.Student.HandlingException.CustomExceptionDefinition.ResourceNotFoundException;
import com.StudentManagement.Student.StudentModel.Student;
import com.StudentManagement.Student.StudentRepository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class StudentServiceTests {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentService studentService;

    private Student student;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        student = new Student(1, "John Doe", "21", "10", "9876543210");
    }

    @Test
    void addStudent_ShouldReturnSavedStudent() {
        when(studentRepository.save(any(Student.class))).thenReturn(student);
        Student result = studentService.addStudent(student);
        assertNotNull(result);
        assertEquals(student.getStudentName(), result.getStudentName());
        assertEquals(student.getPhoneNumber(), result.getPhoneNumber());
    }

    @Test
    void addStudent_ShouldThrowInvalidDataFormatException_WhenInvalidData() {
        Student invalidStudent = new Student(1, "", "21", "10", "9876543210");
        InvalidDataFormatException exception = assertThrows(InvalidDataFormatException.class, () -> studentService.addStudent(invalidStudent));
        assertEquals("Please enter valid data for studentName, phoneNumber, Age, and class.", exception.getMessage());
    }

    @Test
    void addStudent_ShouldThrowPhoneNumberAlreadyExistsException_WhenPhoneNumberExists() {
        when(studentRepository.existsByPhoneNumber(student.getPhoneNumber())).thenReturn(true);
        PhoneNumberAlreadyExistsException exception = assertThrows(PhoneNumberAlreadyExistsException.class, () -> studentService.addStudent(student));
        assertEquals("The phone number is already in use.", exception.getMessage());
    }

    @Test
    void getAllStudents_ShouldReturnListOfStudents() {
        List<Student> students = Arrays.asList(student);
        when(studentRepository.findAll()).thenReturn(students);
        List<Student> result = studentService.getAllStudents();
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
    }

    @Test
    void getStudentByRollNumber_ShouldReturnStudent() {
        when(studentRepository.findById(anyInt())).thenReturn(Optional.of(student));
        Student result = studentService.getStudentByRollNumber(1);
        assertNotNull(result);
        assertEquals(student.getStudentName(), result.getStudentName());
    }

    @Test
    void getStudentByRollNumber_ShouldThrowResourceNotFoundException_WhenStudentNotFound() {
        when(studentRepository.findById(anyInt())).thenReturn(Optional.empty());
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> studentService.getStudentByRollNumber(1));
        assertEquals("Student not found with rollNumber: 1", exception.getMessage());
    }

    @Test
    void updateStudent_ShouldReturnUpdatedStudent() {
        Student updatedStudent = new Student(1, "Jane Doe", "22", "10", "9876543210");
        when(studentRepository.findById(anyInt())).thenReturn(Optional.of(student));
        when(studentRepository.save(any(Student.class))).thenReturn(updatedStudent);
        Student result = studentService.updateStudent(1, updatedStudent);
        assertNotNull(result);
        assertEquals(updatedStudent.getStudentName(), result.getStudentName());
        assertEquals(updatedStudent.getStudentAge(), result.getStudentAge());
    }

    @Test
    void updateStudent_ShouldThrowResourceNotFoundException_WhenStudentNotFound() {
        when(studentRepository.findById(anyInt())).thenReturn(Optional.empty());
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> studentService.updateStudent(1, student));
        assertEquals("Student not found with rollNumber: 1", exception.getMessage());
    }

    @Test
    void deleteStudent_ShouldReturnTrue_WhenStudentDeleted() {
        when(studentRepository.findById(anyInt())).thenReturn(Optional.of(student));
        boolean result = studentService.deleteStudent(1);
        assertTrue(result);
        verify(studentRepository, times(1)).delete(student);
    }

    @Test
    void deleteStudent_ShouldThrowResourceNotFoundException_WhenStudentNotFound() {
        when(studentRepository.findById(anyInt())).thenReturn(Optional.empty());
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> studentService.deleteStudent(1));
        assertEquals("Student not found with rollNumber: 1", exception.getMessage());
    }

    @Test
    void searchStudentsByName_ShouldReturnListOfStudents() {
        List<Student> students = Arrays.asList(student);
        when(studentRepository.findByStudentNameIgnoreCase(anyString())).thenReturn(students);
        List<Student> result = studentService.searchStudentsByName("John Doe");
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
    }

    @Test
    void searchStudentsByName_ShouldThrowResourceNotFoundException_WhenNoStudentFound() {
        when(studentRepository.findByStudentNameIgnoreCase(anyString())).thenReturn(Arrays.asList());
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> studentService.searchStudentsByName("John Doe"));
        assertEquals("No student found with name: John Doe", exception.getMessage());
    }
}
