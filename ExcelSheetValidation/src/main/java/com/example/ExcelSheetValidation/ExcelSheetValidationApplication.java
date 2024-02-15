package com.example.ExcelSheetValidation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(scanBasePackages = "com")
public class ExcelSheetValidationApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExcelSheetValidationApplication.class, args);
	}

}
