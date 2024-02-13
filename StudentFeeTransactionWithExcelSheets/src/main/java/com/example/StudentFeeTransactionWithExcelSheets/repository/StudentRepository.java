package com.example.StudentFeeTransactionWithExcelSheets.repository;

import com.example.StudentFeeTransactionWithExcelSheets.model.Student;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends MongoRepository<Student, String> {
    Student getByEmail(String email);

    Student deleteByEmail(String email);

    boolean existsByEmail(String email);


}
