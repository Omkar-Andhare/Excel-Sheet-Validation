package com.example.StudentFeeTransactionWithExcelSheets.service;

import com.example.StudentFeeTransactionWithExcelSheets.enums.FileType;
import com.example.StudentFeeTransactionWithExcelSheets.model.ExcelFileSheet;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IStudentService {
    void createSheet(String sheetName);

    ExcelFileSheet setMetaDataOfFile(MultipartFile file, FileType fileType) throws IOException;

}
