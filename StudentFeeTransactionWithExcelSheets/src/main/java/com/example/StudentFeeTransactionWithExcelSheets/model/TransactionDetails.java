package com.example.StudentFeeTransactionWithExcelSheets.model;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document
public class TransactionDetails {

    @Indexed(unique = true)
    private String Trans_Id;
    private double amount;
    private LocalDateTime dateTime;
    private String description;

    @Override
    public String toString() {
        return "TransactionDetails{" +
                "Trans_Id='" + Trans_Id + '\'' +
                ", amount=" + amount +
                ", dateTime=" + dateTime +
                ", description='" + description + '\'' +
                '}';
    }

    public String getTrans_Id() {
        return Trans_Id;
    }

    public void setTrans_Id(String trans_Id) {
        Trans_Id = trans_Id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
