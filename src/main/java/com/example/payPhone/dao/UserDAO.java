package com.example.payPhone.dao;


import com.example.payPhone.enttities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface UserDAO extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
