package com.example.payPhone.enttities;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
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
}
