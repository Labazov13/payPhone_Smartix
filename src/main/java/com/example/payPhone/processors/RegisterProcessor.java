package com.example.payPhone.processors;

import com.example.payPhone.dao.UserDAO;
import com.example.payPhone.enttities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;

@Component
public class RegisterProcessor {

    private final PasswordEncoder passwordEncoder;
    private final UserDAO userDAO;

    @Autowired
    public RegisterProcessor(PasswordEncoder passwordEncoder, UserDAO userDAO) {
        this.passwordEncoder = passwordEncoder;
        this.userDAO = userDAO;
    }

    public User setDefaultSettings(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles("ROLE_USER");
        user.setBalance(new BigDecimal(1000));
        userDAO.save(user);
        return user;
    }
}
