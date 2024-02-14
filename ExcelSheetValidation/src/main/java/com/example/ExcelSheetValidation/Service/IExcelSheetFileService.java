package com.example.ExcelSheetValidation.Service;

import com.example.ExcelSheetValidation.enums.FileType;
import com.example.ExcelSheetValidation.exceptions.InvalidExcelFileException;
import com.example.ExcelSheetValidation.model.ExcelSheetFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

public interface IExcelSheetFileService {

    void processExcelFile(MultipartFile file, FileType fileType) throws IOException, InvalidExcelFileException;

    boolean validateExcelFile(InputStream fileInputStream, FileType fileType) throws IOException;

    void processExcelFileByMetaDataOfFile(ExcelSheetFile excelSheetFile) throws IOException, InvalidExcelFileException;

}
