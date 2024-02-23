package com.example.StudentFeeTransactionWithExcelSheets.model;

import com.example.StudentFeeTransactionWithExcelSheets.enums.FileStatus;
import com.example.StudentFeeTransactionWithExcelSheets.enums.FileType;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "excelfiles")
public class ExcelFileSheet {

    private String fileName;

    private Long id;

    public ExcelFileSheet(String fileName, Long id, FileType fileType, FileStatus status, LocalDateTime dateTime, String filePath) {
        this.fileName = fileName;
        this.id = id;
        this.fileType = fileType;
        this.status = status;
        this.dateTime = dateTime;
        this.filePath = filePath;
    }

    @Override
    public String toString() {
        return "ExcelFileSheet{" +
                "fileName='" + fileName + '\'' +
                ", id=" + id +
                ", fileType=" + fileType +
                ", status=" + status +
                ", dateTime=" + dateTime +
                ", filePath='" + filePath + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private FileType fileType;
    private FileStatus status;
    private LocalDateTime dateTime;
    private String filePath;

    public ExcelFileSheet(String fileName, FileType fileType, FileStatus status, LocalDateTime dateTime, String filePath) {
        this.fileName = fileName;
        this.fileType = fileType;
        this.status = status;
        this.dateTime = dateTime;
        this.filePath = filePath;
    }

    public ExcelFileSheet() {
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public FileType getFileType() {
        return fileType;
    }

    public void setFileType(FileType fileType) {
        this.fileType = fileType;
    }

    public FileStatus getStatus() {
        return status;
    }

    public void setStatus(FileStatus status) {
        this.status = status;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
