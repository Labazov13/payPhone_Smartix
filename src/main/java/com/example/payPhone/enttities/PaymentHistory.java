package com.example.payPhone.enttities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

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
    private Date paidAt;
    @Column(name = "phone", nullable = false)
    private String username;
    @Column(name = "amount", nullable = false)
    private BigDecimal amount;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public PaymentHistory(Date paidAt, String username, BigDecimal amount) {
        this.paidAt = paidAt;
        this.username = username;
        this.amount = amount;
    }
}
