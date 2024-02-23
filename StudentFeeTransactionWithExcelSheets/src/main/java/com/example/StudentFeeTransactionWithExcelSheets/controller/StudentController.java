package com.example.StudentFeeTransactionWithExcelSheets.controller;

import com.example.StudentFeeTransactionWithExcelSheets.enums.FileType;
import com.example.StudentFeeTransactionWithExcelSheets.handler.StudentHandler;
import com.example.StudentFeeTransactionWithExcelSheets.model.ExcelFileSheet;
import com.example.StudentFeeTransactionWithExcelSheets.model.Student;
import com.example.StudentFeeTransactionWithExcelSheets.repository.ExcelFileSheetRepository;
import com.example.StudentFeeTransactionWithExcelSheets.repository.StudentRepository;
import com.example.StudentFeeTransactionWithExcelSheets.service.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    ExcelFileSheetRepository excelFileSheetRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private IStudentService studentService;
    @Autowired
    private StudentHandler studentHandler;
    @Autowired
    private RestTemplate restTemplate;

    // Add a new student
    @PostMapping("/add")
    public ResponseEntity<?> addStudent(@RequestBody Student student) {
        if (studentRepository.existsByEmail(student.getEmail())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Email already exists");
        } else {
            // Save the student if the email doesn't exist
            studentRepository.save(student);
            return ResponseEntity.ok("Student data added successfully");
        }
    }

    // Get all students
    @GetMapping("/get")
    public ResponseEntity<?> getAllStudent() {
        return ResponseEntity.ok(studentRepository.findAll());
    }

    //get student by mail
    @GetMapping("/getByMail")
    public ResponseEntity<?> getStudentByMail(@RequestHeader String email) {
        return ResponseEntity.ok(studentRepository.getByEmail(email));
    }

    // Delete a student by ID
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteStudent(@RequestHeader String email) {
        studentRepository.deleteByEmail(email);
        return ResponseEntity.ok("Student deleted");
    }

    // Create an Excel sheet with student data
    @GetMapping("/createSheet")
    public ResponseEntity<?> createSheet(@RequestHeader String sheetName) {
        studentService.createSheet(sheetName);
        return ResponseEntity.ok("Sheet created successfully");
    }

    // Upload student data from an Excel sheet
    @PostMapping("/uploadSheet")
    public ResponseEntity<?> uploadSheet(@RequestBody MultipartFile file) {
        try {
            List<Student> students = studentHandler.readDataFromSheet(file.getInputStream());
            studentRepository.saveAll(students);
            return ResponseEntity.ok("Sheet uploaded and data stored successfully");
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Error uploading sheet");
        }
    }







    @PostMapping("/sendExcelInfo")
    public void sendExcelInfo(@RequestBody MultipartFile excelFile, @RequestHeader FileType fileType) throws IOException {

        ExcelFileSheet excelFileSheetData = studentService.setMetaDataOfFile(excelFile, fileType);
        ExcelFileSheet excelFileSheet = restTemplate.postForEntity("http://localhost:8081/file/validateExcelByMetaData", excelFileSheetData, ExcelFileSheet.class).getBody();
        excelFileSheetRepository.save(excelFileSheet);

    }

    @PostMapping("/updateFilePath")
    public void updateExcelFilePath(@RequestBody ExcelFileSheet excelFileSheet) {

        excelFileSheetRepository.save(excelFileSheet);

    }


}