package com.example.StudentFeeTransactionWithExcelSheets.handler;

import com.example.StudentFeeTransactionWithExcelSheets.model.Student;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public class StudentHandler {


    private static final Logger logger = LogManager.getLogger(Student.class);

    /**
     * Reads data from an Excel sheet and converts it into a list of Student objects.
     *
     * @param inputStream The input stream of the Excel file.
     * @return A list of Student objects.
     * @throws IOException If an I/O error occurs.
     */
    public List<Student> readDataFromSheet(InputStream inputStream) throws IOException {

        List<Student> students = new ArrayList<>();

        Workbook workbook = new XSSFWorkbook(inputStream);
        Sheet sheet = workbook.getSheetAt(0);
        Iterator<Row> rowIterator = sheet.iterator();

        // Skip the header row assuming it contains column names
        if (rowIterator.hasNext()) {
            rowIterator.next();
        }

        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            Iterator<Cell> cellIterator = row.cellIterator();

            // Initialize variables to store cell values
            String name = "";
            String phone_no = "";
            String email = "";

            // Read cell values and populate variables


            if (cellIterator.hasNext()) {
                Cell firstCell = cellIterator.next();
                if (firstCell.getCellType() == CellType.STRING) {
                    name = firstCell.getStringCellValue();
                }
            }

            if (cellIterator.hasNext()) {
                Cell secondCell = cellIterator.next();
                if (secondCell.getCellType() == CellType.STRING) {
                    phone_no = secondCell.getStringCellValue();
                }
            }

            if (cellIterator.hasNext()) {
                Cell thirdCell = cellIterator.next();
                if (thirdCell.getCellType() == CellType.STRING) {
                    email = thirdCell.getStringCellValue();
                }
            }

            // Create Student object and add to the list
            students.add(new Student(name, phone_no, email));
        }

        // Close the workbook and return the list of students
        workbook.close();
        logger.info("Data read from Excel sheet successfully.");
        return students;
    }


}
