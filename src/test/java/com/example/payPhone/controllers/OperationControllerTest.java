package com.example.payPhone.controllers;

import com.example.payPhone.dao.PaymentHistoryDAO;
import com.example.payPhone.processors.OperationProcessor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class OperationControllerTest {

    @Mock
    PaymentHistoryDAO paymentHistoryDAO;
    @Mock
    OperationProcessor operationProcessor;
    @InjectMocks
    OperationController operationController;
    @Test
    void getPaymentHistory_ReturnCorrectResponseEntityWithHttpStatusOK(){

    }

}