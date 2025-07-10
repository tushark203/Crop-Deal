package com.payment.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer paymetId;
    private Integer dealerId;
    private Integer farmerId;
    private Integer cropId;
    
    @Positive(message = "Amount must be greater than zero")
    private Double amount;
    private LocalDateTime paidAt = LocalDateTime.now();
}
