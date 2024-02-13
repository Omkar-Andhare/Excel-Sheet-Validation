package com.example.ExcelSheetValidation.controller;

import com.example.ExcelSheetValidation.Service.IExcelSheetFileService;
import com.example.ExcelSheetValidation.enums.FileType;
import com.example.ExcelSheetValidation.exceptions.InvalidExcelFileException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/file")
public class ExcelSheetFileController {
    private static final Logger logger = LogManager.getLogger(ExcelSheetFileController.class);
    @Autowired
    private IExcelSheetFileService excelSheetFileService;


//  @Autowired
//  private IExcelFileValidationConfigService excelFileValidationConfigService;

    @PostMapping("/validateExcel")
    public ResponseEntity<String> uploadExcelFile(@RequestBody MultipartFile file, @RequestHeader FileType fileType) {


        //its for only setting the excel cells regex and only for one call
        //excelFileValidationConfigService.saveConfig();
        try {
            logger.info("Received request to upload Excel file with file type: {}", fileType);
            excelSheetFileService.processExcelFile(file, fileType);
            logger.info("File uploaded successfully.");
            return ResponseEntity.ok("File uploaded successfully.");
        } catch (IOException e) {
            logger.error("Failed to upload file: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to upload file: " + e.getMessage());
        } catch (InvalidExcelFileException e) {
            throw new RuntimeException(e);
        }
    }


}
