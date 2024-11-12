package com.service.beta.data.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.Date;

@Data
@Entity
public class DbTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String transactionId;

    private String transactionType;

    private String transactionStatus;

    private Date transactionDate;

    private Date transactionTime;
    private Integer transactionAmount;
    private Integer transactionFee;
    private String transactionDescription;
    private String transactionNotes;
}
