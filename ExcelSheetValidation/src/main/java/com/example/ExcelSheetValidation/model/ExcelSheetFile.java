package com.example.ExcelSheetValidation.model;

import com.example.ExcelSheetValidation.enums.FileStatus;
import com.example.ExcelSheetValidation.enums.FileType;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class ExcelSheetFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileName;
    @Enumerated(EnumType.STRING)
    private FileType fileType;
    @Enumerated(EnumType.STRING)
    private FileStatus status;
    private LocalDateTime dateTime;
    private String filePath;

    @Override
    public String toString() {
        return "ExcelSheetFile{" +
                "id=" + id +
                ", fileName='" + fileName + '\'' +
                ", fileType=" + fileType +
                ", status=" + status +
                ", dateTime=" + dateTime +
                ", filePath='" + filePath + '\'' +
                '}';
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
}
