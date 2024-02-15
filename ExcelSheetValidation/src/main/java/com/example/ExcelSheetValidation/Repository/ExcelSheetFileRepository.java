package com.example.ExcelSheetValidation.Repository;

import com.example.ExcelSheetValidation.enums.FileStatus;
import com.example.ExcelSheetValidation.model.ExcelSheetFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExcelSheetFileRepository extends JpaRepository<ExcelSheetFile, Long> {
//    List<ExcelSheetFile> findTopNByStatusOrderByDateTimeAsc(FileStatus fileStatus, int batchSize);
    List<ExcelSheetFile> findByStatus(FileStatus fileStatus);


}
