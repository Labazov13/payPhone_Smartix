package com.example.payPhone.processors;

import com.example.payPhone.enttities.User;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class BalanceProcessor {

    public boolean checkBalance(User userTo, BigDecimal amount, User userFrom) {
        if (userFrom == null) {
            return false;
        }
        if (userTo.getBalance().longValue() >= amount.longValue()) {
            userTo.setBalance(userTo.getBalance().subtract(amount));
            userFrom.setBalance(userFrom.getBalance().add(amount));
            return true;
        }
        return false;
    }
}
