package com.example.StudentFeeTransactionWithExcelSheets.model;

import com.example.StudentFeeTransactionWithExcelSheets.enums.FileStatus;
import com.example.StudentFeeTransactionWithExcelSheets.enums.FileType;

import java.time.LocalDateTime;

public class ExcelFileSheet {

    private String fileName;

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

    @Override
    public String toString() {
        return "ExcelFileSheet{" +
                "fileName='" + fileName + '\'' +
                ", fileType=" + fileType +
                ", status=" + status +
                ", dateTime=" + dateTime +
                ", filePath='" + filePath + '\'' +
                '}';
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
