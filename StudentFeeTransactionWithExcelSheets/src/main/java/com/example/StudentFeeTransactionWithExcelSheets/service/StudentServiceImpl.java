package com.example.StudentFeeTransactionWithExcelSheets.service;

import com.example.StudentFeeTransactionWithExcelSheets.model.Student;
import com.example.StudentFeeTransactionWithExcelSheets.repository.StudentRepository;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class StudentServiceImpl implements IStudentService {

    private static final Logger logger = LogManager.getLogger(Student.class);


    @Autowired
    private StudentRepository studentRepository;

    @Override
    public void createSheet(String sheetName) {
        try {
            // Fetch students from the database
            List<Student> students = studentRepository.findAll();

            // Create a new workbook
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet(sheetName);

            // Create header row
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("Name");
            headerRow.createCell(1).setCellValue("Phone_No");
            headerRow.createCell(2).setCellValue("Email");

            // Populate data rows
            int rowNum = 1;
            for (Student student : students) {
                Row dataRow = sheet.createRow(rowNum++);
                dataRow.createCell(0).setCellValue(student.getName());
                dataRow.createCell(1).setCellValue(student.getPhone_no());
                dataRow.createCell(2).setCellValue(student.getEmail());
            }

            try (FileOutputStream fileOut = new FileOutputStream("/home/perennial/ExcelSheets/" + sheetName + ".xlsx")) {
                workbook.write(fileOut);
                logger.info("Sheet created successfully");
            } catch (IOException e) {
                logger.error("Error creating sheet: " + e.getMessage());
                throw new RuntimeException(e);
            }
        } catch (Exception e) {
            logger.error("Error creating sheet: " + e.getMessage());
            throw new RuntimeException(e);
        }


    }
}
