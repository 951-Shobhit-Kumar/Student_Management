package com.StudentManagement.Student;

import com.StudentManagement.Student.HandlingException.CustomExceptionDefinition.ResourceNotFoundException;
import com.StudentManagement.Student.HandlingException.StudentApplicationExceptionHandler;
import com.StudentManagement.Student.StudentController.StudentController;
import com.StudentManagement.Student.StudentModel.Student;
import com.StudentManagement.Student.StudentModel.StudentDto;
import com.StudentManagement.Student.StudentServices.StudentService;
import com.StudentManagement.Student.StudentModel.StudentMapperDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class StudentControllerTests {

    @InjectMocks
    private StudentController studentController;

    @Mock
    private StudentService studentService;

    private MockMvc mockMvc;

//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//        mockMvc = MockMvcBuilders.standaloneSetup(studentController).build();
//    }
@BeforeEach
void setUp() {
    try (var mock = MockitoAnnotations.openMocks(this)) { // Ensure mock resources are closed
        mockMvc = MockMvcBuilders.standaloneSetup(studentController)
                .setControllerAdvice(new StudentApplicationExceptionHandler())  // Register exception handler
                .build();
    } catch (Exception e) {
        e.printStackTrace();
    }
}



    @Test
    void createStudent_ShouldReturnCreated() throws Exception {
        StudentDto studentDto = new StudentDto(1, "Soury", "20", "10", "9876543210");
        Student student = StudentMapperDto.mapToEntity(studentDto);
        when(studentService.addStudent(student)).thenReturn(student);

        mockMvc.perform(post("/api/v1/students")
                        .contentType("application/json")
                        .content("{\"studentName\":\"Soury\",\"studentAge\":\"20\",\"studentClass\":\"10\",\"phoneNumber\":\"9876543210\"}"))
                .andExpect(status().isCreated());
    }

    @Test
    void getAllStudents_ShouldReturnList() throws Exception {
        StudentDto studentDto = new StudentDto(1, "Soury", "20", "10", "9876543210");
        when(studentService.getAllStudents()).thenReturn(Collections.singletonList(StudentMapperDto.mapToEntity(studentDto)));

        mockMvc.perform(get("/api/v1/students"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].studentName").value("Soury"))
                .andExpect(jsonPath("$[0].studentAge").value("20"));
    }

    @Test
    void getStudentByRollNumber_ShouldReturnStudent() throws Exception {
        Integer rollNumber = 1;
        StudentDto studentDto = new StudentDto(1, "Soury", "20", "10", "9876543210");
        Student student = StudentMapperDto.mapToEntity(studentDto);
        when(studentService.getStudentByRollNumber(Integer.valueOf(rollNumber))).thenReturn(student);

        mockMvc.perform(get("/api/v1/students/{rollNumber}", rollNumber))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.studentName").value("Soury"))
                .andExpect(jsonPath("$.studentAge").value("20"));
    }

    @Test
    void getStudentByRollNumber_ShouldReturnNotFound() throws Exception {
        Integer rollNumber = 1000;
        when(studentService.getStudentByRollNumber(rollNumber)).thenThrow(new ResourceNotFoundException("Student not found"));

        mockMvc.perform(get("/api/v1/students/{rollNumber}", rollNumber))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateStudent_ShouldReturnUpdatedStudent() throws Exception {
        Integer rollNumber = 1;
        StudentDto studentDto = new StudentDto(1, "Soury", "21", "11", "9876543210");
        Student student = StudentMapperDto.mapToEntity(studentDto);

        // Mock the service method to return the student entity
        when(studentService.updateStudent(eq(rollNumber), any(Student.class)))
                .thenReturn(student);

        // Perform the PUT request
        mockMvc.perform(put("/api/v1/students/{rollNumber}", rollNumber)
                        .contentType("application/json")
                        .content("{\"studentName\":\"Soury\",\"studentAge\":\"21\",\"studentClass\":\"11\",\"phoneNumber\":\"9876543210\"}"))
                .andDo(print())  // Debug the response body
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.studentName").value("Soury"))
                .andExpect(jsonPath("$.studentAge").value("21"));
    }


    @Test
    void deleteStudent_ShouldReturnNoContent() throws Exception {
        Integer rollNumber = 1;
        when(studentService.deleteStudent(rollNumber)).thenReturn(true);

        mockMvc.perform(delete("/api/v1/students/{rollNumber}", rollNumber))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteStudent_ShouldReturnNotFound() throws Exception {
        Integer rollNumber = 1;
        when(studentService.deleteStudent(rollNumber)).thenThrow(new ResourceNotFoundException("No student found with given rollNumber: " + rollNumber));

        mockMvc.perform(delete("/api/v1/students/{rollNumber}", rollNumber))
                .andExpect(status().isNotFound())  // Expect 404 status
                .andExpect(jsonPath("$.message").value("No student found with given rollNumber: " + rollNumber))  // Check 'message' in JSON response
                .andExpect(jsonPath("$.errorCode").value("RESOURCE_NOT_FOUND"))  // Check 'errorCode' in JSON response
                .andExpect(jsonPath("$.status").value("NOT_FOUND"));  // Check 'status' in JSON response
    }



}
