package com.example.ExcelSheetValidation.Repository;

import com.example.ExcelSheetValidation.model.ExcelFileValidationConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ExcelFileValidationConfigRepo extends JpaRepository<ExcelFileValidationConfig, Long> {
    List<ExcelFileValidationConfig> findByFileType(String fileType);

}
