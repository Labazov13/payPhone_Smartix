package com.example.payPhone.controllers;

import com.example.payPhone.enttities.User;
import com.example.payPhone.processors.RegisterProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegisterController {

    private final RegisterProcessor registerProcessor;

    @Autowired
    public RegisterController(RegisterProcessor registerProcessor) {
        this.registerProcessor = registerProcessor;
    }

    @PostMapping(value = "/register")
    public ResponseEntity<User> getRegister(@RequestBody User user) {
        return ResponseEntity.ok().body(this.registerProcessor.setDefaultSettings(user));
    }
}
