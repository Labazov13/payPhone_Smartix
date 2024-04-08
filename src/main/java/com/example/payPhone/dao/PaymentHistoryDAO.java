package com.example.payPhone.dao;

import com.example.payPhone.enttities.PaymentHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentHistoryDAO extends JpaRepository<PaymentHistory, Long> {
}
