package com.example.payPhone.controllers;

import com.example.payPhone.dao.UserDAO;
import com.example.payPhone.enttities.User;
import com.example.payPhone.processors.RegisterProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<?> getRegister(@RequestBody User user) {
        User fullUser = registerProcessor.setDefaultSettings(user);
        userDAO.save(fullUser);
        registerProcessor.savePayment(user);
        return new ResponseEntity<>(fullUser, HttpStatus.OK);
    }
}
