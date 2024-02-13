package com.example.ExcelSheetValidation.Service;

import com.example.ExcelSheetValidation.Repository.ExcelFileValidationConfigRepo;
import com.example.ExcelSheetValidation.model.ExcelFileValidationConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class ExcelFileValidationConfigServiceImpl implements IExcelFileValidationConfigService {

    private static final Logger logger = LogManager.getLogger(ExcelFileValidationConfigServiceImpl.class);


    @Autowired
    private ExcelFileValidationConfigRepo excelFileValidationConfigRepo;

    /**
     * Saves configuration for validating Excel file headers and data.
     */
    @Override
    public void saveConfig() {

        // Configuration for Name
        ExcelFileValidationConfig configName = new ExcelFileValidationConfig();
        configName.setFileType("SCHOOL_STUDENT");
        configName.setHeader("NAME");
        configName.setRegex("^[A-Za-z ]+$");

        // Record for Phone No
        ExcelFileValidationConfig configPhone = new ExcelFileValidationConfig();
        configPhone.setFileType("SCHOOL_STUDENT");
        configPhone.setHeader("PHONE");
        configPhone.setRegex("^\\d{10}$");

        // Record for Email
        ExcelFileValidationConfig configEmail = new ExcelFileValidationConfig();
        configEmail.setFileType("SCHOOL_STUDENT");
        configEmail.setHeader("EMAIL");
        configEmail.setRegex("^[a-zA-Z0-9._%+-]+@gmail\\.com$");

        // Save all configurations
        excelFileValidationConfigRepo.saveAll(Arrays.asList(configName, configPhone, configEmail));

        logger.info("Excel file validation configuration saved successfully.");


    }
}
