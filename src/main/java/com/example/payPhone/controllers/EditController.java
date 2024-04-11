package com.example.payPhone.controllers;

import com.example.payPhone.dao.UserDAO;
import com.example.payPhone.enttities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;

@RestController
public class EditController {
    private final UserDAO userDAO;

    @Autowired
    public EditController(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @PutMapping(value = "/edit")
    public ResponseEntity<String> editData(@RequestParam(required = false) String fullName,
                                           @RequestParam(required = false) String email,
                                           @RequestParam(required = false) String gender,
                                           @RequestParam(required = false) String birthDay) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userDAO.findByUsername(auth.getName());
        if (user != null) {
            user.setFullName(fullName);
            user.setEmail(email);
            user.setGender(gender);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            if (birthDay != null){
                try {
                    user.setBirthDay(dateFormat.parse(birthDay));
                } catch (ParseException e) {
                    return ResponseEntity.status(HttpStatus.NOT_MODIFIED)
                            .header("error", "Incorrect Input Date!").build();
                }
            }
            else {
                user.setBirthDay(null);
            }
            userDAO.save(user);
            return new ResponseEntity<>("Data changed successfully!", HttpStatus.OK);
        }
        return new ResponseEntity<>("Oops! Something went wrong!", HttpStatus.NOT_FOUND);
    }
}
