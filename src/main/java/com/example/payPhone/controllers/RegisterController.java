package com.example.payPhone.controllers;

import com.example.payPhone.dao.PaymentHistoryDAO;
import com.example.payPhone.dao.UserDAO;
import com.example.payPhone.enttities.PaymentHistory;
import com.example.payPhone.enttities.User;
import com.example.payPhone.processors.RegisterProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class RegisterController {

    private final UserDAO userDAO;
    private final RegisterProcessor registerProcessor;
    @Autowired
    public RegisterController(UserDAO userDAO, RegisterProcessor registerProcessor) {
        this.userDAO = userDAO;
        this.registerProcessor = registerProcessor;
    }
    @PostMapping(value = "/register")
    public ResponseEntity<?> getRegister(@RequestBody User user){
        boolean isRegister = registerProcessor.setDefaultSettings(user);
        if (isRegister){
            userDAO.save(user);
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
