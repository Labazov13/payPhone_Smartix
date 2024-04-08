package com.example.payPhone.dao;


import com.example.payPhone.enttities.User;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface UserDAO extends PagingAndSortingRepository<User, Long> {
    void addUser(User user);
    User getUserById(Long id);
    User getUserByUsername(String username);
    List<User> getAllUsers();

}
