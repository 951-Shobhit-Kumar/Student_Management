package com.StudentManagement.Student.StudentController;
import com.StudentManagement.Student.StudentModel.Student;
import com.StudentManagement.Student.StudentModel.StudentDto;
import com.StudentManagement.Student.StudentModel.StudentMapperDto;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.*;
import io.swagger.v3.oas.annotations.tags.*;
import com.StudentManagement.Student.StudentServices.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/students")
@Tag(name = "students", description = "API for managing student data")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    // Description for Student Addition
    @Operation(
            summary = "Create a new student record",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Student created successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid data provided"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            }
    )
    @PostMapping
    public ResponseEntity<StudentDto> createStudent(@Valid @RequestBody StudentDto studentDto) {
        Student student = StudentMapperDto.mapToEntity(studentDto);
        Student savedStudent = studentService.addStudent(student);
        StudentDto responseDto = StudentMapperDto.mapToDto(savedStudent);

        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }




    // Endpoint to retrieve all students
    @Operation(
            summary = "Fetch all student records",
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of students retrieved"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            }
    )
    @GetMapping
    public ResponseEntity<List<StudentDto>> getAllStudents() {
        List<StudentDto> studentDtos = StudentDto.convertToDtoList(studentService.getAllStudents());
        return new ResponseEntity<>(studentDtos, HttpStatus.OK);
    }

    // Endpoint to retrieve a student by roll number
    @Operation(
            summary = "Get student by roll number",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Student found"),
                    @ApiResponse(responseCode = "404", description = "Student not found"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            }
    )
    @GetMapping("/{rollNumber}")
    public ResponseEntity<StudentDto> getStudentByRollNumber(
            @Parameter(description = "The roll number of the student to fetch") @PathVariable Integer rollNumber
    ) {
        Student student = studentService.getStudentByRollNumber(rollNumber);

            return new ResponseEntity<>(StudentMapperDto.mapToDto(student), HttpStatus.OK);
    }



    // Endpoint to search for students by name
    @Operation(
            summary = "Search for students by their name",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Students found matching the name"),
                    @ApiResponse(responseCode = "404", description = "Invalid search criteria"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            }
    )
    @GetMapping("/search")
    public ResponseEntity<List<StudentDto>> searchStudentsByName(
            @Parameter(description = "Name of the student to search for") @RequestParam String name
    ) {
        List<StudentDto> studentDtos = StudentDto.convertToDtoList(studentService.searchStudentsByName(name));
        return new ResponseEntity<>(studentDtos, HttpStatus.OK);
    }

    // Endpoint to update an existing student's information
    @Operation(
            summary = "Update an existing student record",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Student record updated successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid student data"),
                    @ApiResponse(responseCode = "404", description = "Student not found"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            }
    )
    @PutMapping("/{rollNumber}")
    public ResponseEntity<StudentDto> updateStudent(
            @Parameter(description = "Roll number of the student to update") @PathVariable Integer rollNumber,
            @Valid @RequestBody StudentDto studentDto
    ) {
        Student updatedStudent = StudentMapperDto.mapToEntity(studentDto);
        Student student = studentService.updateStudent(rollNumber, updatedStudent);
        return new ResponseEntity<>(StudentMapperDto.mapToDto(student), HttpStatus.OK);

    }



    // Endpoint to delete a student by roll number
    @Operation(
            summary = "Delete a student record",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Student record deleted"),
                    @ApiResponse(responseCode = "404", description = "Student not found"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            }
    )
    @DeleteMapping("/{rollNumber}")
    public ResponseEntity<Void> deleteStudent(
            @Parameter(description = "Roll number of the student to delete") @PathVariable Integer rollNumber
    ) {
        boolean isDeleted = studentService.deleteStudent(rollNumber);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT) ;
    }
}
