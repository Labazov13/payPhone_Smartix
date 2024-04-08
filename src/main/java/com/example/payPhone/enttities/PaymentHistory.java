package com.example.payPhone.enttities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "payment_history")
public class PaymentHistory {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "paid_at")
    private LocalDate paidAt;
    @Column(name = "phone")
    private String phone;
    @Column(name = "amount")
    private BigDecimal amount;

    public PaymentHistory(LocalDate paidAt, String phone, BigDecimal amount) {
        this.paidAt = paidAt;
        this.phone = phone;
        this.amount = amount;
    }
}
