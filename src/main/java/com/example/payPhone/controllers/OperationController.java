package com.example.payPhone.controllers;

import com.example.payPhone.dao.PaymentHistoryDAO;
import com.example.payPhone.enttities.PaymentHistory;
import com.example.payPhone.processors.OperationProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OperationController {
    private final PaymentHistoryDAO paymentHistoryDAO;
    private final OperationProcessor operationProcessor;

    @Autowired
    public OperationController(PaymentHistoryDAO paymentHistoryDAO, OperationProcessor operationProcessor) {
        this.paymentHistoryDAO = paymentHistoryDAO;
        this.operationProcessor = operationProcessor;
    }

    @GetMapping(value = "/history")
    public ResponseEntity<Page<PaymentHistory>> getPaymentHistory(@RequestParam String username,
                                                                  @PageableDefault(sort = {"username"}, direction = Sort.Direction.DESC)
                                                                  Pageable pageable) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (username.equals(auth.getName())) {
            return ResponseEntity.ok().body(this.paymentHistoryDAO.findByUsername(username, pageable));
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .header("Access", "Access to someone else's account information is prohibited")
                .build();
    }

    @GetMapping(value = "/short-history")
    public ResponseEntity<List<String>> getShortPaymentHistory(@RequestParam String username) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (username.equals(auth.getName())) {
            List<PaymentHistory> paymentHistoryList = paymentHistoryDAO.findByUsername(username);
            return ResponseEntity.ok().body(this.operationProcessor.convertToList(paymentHistoryList));
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .header("Access", "Access to someone else's account information is prohibited")
                .build();
    }
}
