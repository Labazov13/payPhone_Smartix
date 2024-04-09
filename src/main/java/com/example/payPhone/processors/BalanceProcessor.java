package com.example.payPhone.processors;

import com.example.payPhone.dao.PaymentHistoryDAO;
import com.example.payPhone.dao.UserDAO;
import com.example.payPhone.enttities.PaymentHistory;
import com.example.payPhone.enttities.User;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
public class BalanceProcessor {
    private final UserDAO userDAO;
    private final PaymentHistoryDAO paymentHistoryDAO;

    public BalanceProcessor(UserDAO userDAO, PaymentHistoryDAO paymentHistoryDAO) {
        this.userDAO = userDAO;
        this.paymentHistoryDAO = paymentHistoryDAO;
    }


    public boolean checkBalance(User userTo, BigDecimal amount, User userFrom) {
        return userFrom != null && userTo.getBalance().longValue() >= amount.longValue();
    }

    public void editBalance(User userTo, BigDecimal amount, User userFrom) {
        BigDecimal balanceTo = userTo.getBalance().subtract(amount);
        BigDecimal balanceFrom = userFrom.getBalance().add(amount);
        userTo.setBalance(balanceTo);
        userFrom.setBalance(balanceFrom);
        PaymentHistory paymentHistoryUserTo = new PaymentHistory(LocalDate.now(), userTo.getUsername(), balanceTo);
        paymentHistoryUserTo.setUser(userTo);
        PaymentHistory paymentHistoryUserFrom = new PaymentHistory(LocalDate.now(), userFrom.getUsername(), balanceFrom);
        paymentHistoryUserFrom.setUser(userFrom);
        userDAO.save(userTo);
        paymentHistoryDAO.save(paymentHistoryUserTo);
        userDAO.save(userFrom);
        paymentHistoryDAO.save(paymentHistoryUserFrom);
    }
}
