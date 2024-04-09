package com.example.payPhone.enttities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
@Data
@Entity
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "payment_history")
public class PaymentHistory implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "paid_at", nullable = false)
    private LocalDate paidAt;
    @Column(name = "phone", nullable = false)
    private String phone;
    @Column(name = "amount", nullable = false)
    private BigDecimal amount;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public PaymentHistory(LocalDate paidAt, String phone, BigDecimal amount) {
        this.paidAt = paidAt;
        this.phone = phone;
        this.amount = amount;
    }
}
