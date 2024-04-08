package com.example.payPhone.processors;

import com.example.payPhone.dao.PaymentHistoryDAO;
import com.example.payPhone.enttities.PaymentHistory;
import com.example.payPhone.enttities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
public class RegisterProcessor {

    private final PasswordEncoder passwordEncoder;
    private final PaymentHistoryDAO paymentHistoryDAO;

    @Autowired
    public RegisterProcessor(PasswordEncoder passwordEncoder, PaymentHistoryDAO paymentHistoryDAO) {
        this.passwordEncoder = passwordEncoder;
        this.paymentHistoryDAO = paymentHistoryDAO;
    }

    public boolean setDefaultSettings(User user){
        if (user == null){
            return false;
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles("ROLE_USER");
        user.setBalance(new BigDecimal(1000));
        paymentHistoryDAO.save(new PaymentHistory(LocalDate.now(), user.getPhone(), user.getBalance()));
        return true;
    }
}
