package com.example.payPhone.controllers;

import com.example.payPhone.dao.UserDAO;
import com.example.payPhone.processors.RegisterProcessor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class RegisterControllerTest {

    @Mock
    UserDAO userDAO;
    @Mock
    RegisterProcessor registerProcessor;
    @InjectMocks
    RegisterController registerController;

    @Test
    void getRegister_ReturnResponseEntityWithHttpStatusOK(){}

}