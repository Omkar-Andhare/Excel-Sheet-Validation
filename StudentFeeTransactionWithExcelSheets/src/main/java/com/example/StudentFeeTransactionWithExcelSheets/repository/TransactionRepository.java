package com.example.StudentFeeTransactionWithExcelSheets.repository;

import com.example.StudentFeeTransactionWithExcelSheets.model.TransactionDetails;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TransactionRepository extends MongoRepository<TransactionDetails, String> {
}
