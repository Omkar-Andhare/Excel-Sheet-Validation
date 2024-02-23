package com.example.StudentFeeTransactionWithExcelSheets.repository;

import com.example.StudentFeeTransactionWithExcelSheets.model.ExcelFileSheet;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExcelFileSheetRepository extends MongoRepository<ExcelFileSheet, String> {

    ExcelFileSheet findByFileName(String fileName);
    ExcelFileSheet findById(Long id);



}
