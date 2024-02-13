package com.example.ExcelSheetValidation.Repository;

import com.example.ExcelSheetValidation.model.ExcelSheetFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExcelSheetFileRepository extends JpaRepository<ExcelSheetFile, Long> {
}
