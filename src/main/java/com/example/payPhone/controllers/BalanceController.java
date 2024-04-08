package com.example.payPhone.controllers;

import com.example.payPhone.dao.UserDAO;
import com.example.payPhone.enttities.User;
import com.example.payPhone.processors.BalanceProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
public class BalanceController {
    private final UserDAO userDAO;
    private final BalanceProcessor balanceProcessor;
    @Autowired
    public BalanceController(UserDAO userDAO, BalanceProcessor balanceProcessor) {
        this.userDAO = userDAO;
        this.balanceProcessor = balanceProcessor;
    }
    @GetMapping(value = "/users/{username}")
    public ResponseEntity<BigDecimal> getBalance(@PathVariable(name = "username") String username){
        //Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userDAO.findByUsername(username);
        if (user != null){
            return new ResponseEntity<>(user.getBalance(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @PutMapping(value = "/payment")
    public ResponseEntity<?> payment(@RequestParam(value = "username") String username,
                                     @RequestParam(value = "amount") BigDecimal amount){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User userTo = userDAO.findByUsername(auth.getName());
        User userFrom = userDAO.findByUsername(username);
        boolean isChecked = balanceProcessor.checkBalance(userTo, amount, userFrom);
        if (isChecked){
            userDAO.save(userTo);
            userDAO.save(userFrom);
        }

    }
}
