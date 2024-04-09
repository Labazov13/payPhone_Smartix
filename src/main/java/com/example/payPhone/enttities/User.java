package com.example.payPhone.enttities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "username", nullable = false, unique = true)
    private String username;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "balance", nullable = false)
    private BigDecimal balance;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private Set<PaymentHistory> paymentHistory = new HashSet<>();
    @Column(name = "roles", nullable = false)
    private String roles;
    @Column(name = "full_name", nullable = false)
    private String fullName;
    @Column(name = "email", nullable = false)
    private String email;
    @Column(name = "gender", nullable = false)
    private String gender;
    @Column(name = "birth_day", nullable = false)
    private Date birthDay;

    public User(String username, String password, String fullName, String email, String gender, Date birthDay) {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.email = email;
        this.gender = gender;
        this.birthDay = birthDay;
    }
}
