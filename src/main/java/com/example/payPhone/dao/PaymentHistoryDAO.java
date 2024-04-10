package com.example.payPhone.dao;

import com.example.payPhone.enttities.PaymentHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentHistoryDAO extends JpaRepository<PaymentHistory, Long> {

    Page<PaymentHistory> findByUsername(String username, Pageable pageable);
    List<PaymentHistory> findByUsername(String username);
}
