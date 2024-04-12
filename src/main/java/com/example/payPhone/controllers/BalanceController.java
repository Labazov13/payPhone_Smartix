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

    /**
     * This method assumes equal authority for all users of the application,
     * so there is a restriction on viewing user information ONLY for the user who is currently authenticated
     * @param username
     * @return Class: ResponseEntity<?>
     */
    @GetMapping(value = "/users/{username}")
    public ResponseEntity<String> getBalance(@PathVariable(name = "username") String username){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (username.equals(auth.getName())){
            User user = userDAO.findByUsername(username);
            if (user != null){
                return new ResponseEntity<>("User: " + user.getUsername() + ", Balance: " + user.getBalance(), HttpStatus.OK);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).header("Error", "User is not found!").build();
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body("Access to someone else's account information is prohibited");

    }
    @PutMapping(value = "/payment")
    public ResponseEntity<String> payment(@RequestParam(value = "username") String username,
                                     @RequestParam(value = "amount") BigDecimal amount){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User userTo = userDAO.findByUsername(auth.getName());
        User userFrom = userDAO.findByUsername(username);
        boolean isChecked = balanceProcessor.checkBalance(userTo, amount, userFrom);
        if (isChecked){
            balanceProcessor.editBalance(userTo, amount, userFrom);
            return ResponseEntity.status(HttpStatus.OK).body("The payment was successful!");
        }
        return new ResponseEntity<>("Oops! Something went wrong! Your balance: " + userTo.getBalance(), HttpStatus.NOT_FOUND);
    }
}
