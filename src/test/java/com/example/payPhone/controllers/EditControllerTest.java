package com.example.payPhone.controllers;

import com.example.payPhone.dao.UserDAO;
import com.example.payPhone.enttities.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@AutoConfigureMockMvc(printOnlyOnFailure = false)
class EditControllerTest {

    @MockBean
    UserDAO userDAO;
    @Autowired
    MockMvc mockMvc;

    @Test
    @WithMockUser(username = "testuser")
    void editData_ReturnCorrectChangesInputedDataWithStatusOK() throws Exception {
        when(userDAO.findByUsername("testuser")).thenReturn(new User("testuser", "password"));
        this.mockMvc.perform(put("/edit")
                        .with(httpBasic("testuser", "password"))
                        .param("fullName", "petya")
                        .param("gender", "male"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "testuser")
    void editData_ReturnIncorrectChangesInputedDataWithNullUserWithStatusNOT_FOUND() throws Exception {
        when(userDAO.findByUsername("anotherTestuser")).thenReturn(new User("testuser", "password"));
        this.mockMvc.perform(put("/edit")
                        .with(httpBasic("testuser", "password"))
                        .param("fullName", "petya")
                        .param("birthDay", "1998-11-15")
                        .param("gender", "male"))
                .andExpect(status().isNotFound());
    }
    @Test
    @WithMockUser(username = "testuser")
    void editData_ReturnIncorrectChangesInputedDataWithInvalidBirthDayParametrWithStatusNOT_MODIFIED() throws Exception {
        when(userDAO.findByUsername("testuser")).thenReturn(new User("testuser", "password"));
        this.mockMvc.perform(put("/edit")
                        .with(httpBasic("testuser", "password"))
                        .param("fullName", "petya")
                        .param("birthDay", "wrongBirthday")
                        .param("gender", "male"))
                .andExpect(header().string("error", "Incorrect Input Date!"))
                .andExpect(status().isNotModified());
    }

}




/*EditController editController = new EditController(userDAO);
 *//*User user = new User("testuser", "password");
        userDAO.save(user);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User ourUser = this.userDAO.findByUsername(auth.getName());
        System.out.println(ourUser);
        assertEquals("testuser", auth.getName());*//*
        var responseEntity = editController.editData("petrov", "newEmail", "male", "1996-12-15");
        System.out.println(responseEntity.getBody());
        *//*User mockUser = new User();
        mockUser.setUsername("testUser");
        mockUser.setPassword("password");

        // Mock userDAO behavior
        when(userDAO.findByUsername("testUser")).thenReturn(mockUser);

        // Perform the PUT request
        mockMvc.perform(put("/edit")
                        .with(httpBasic("testuser", "password"))
                        .param("fullName", "John Doe")
                        .param("email", "john.doe@example.com")
                        .param("gender", "male")
                        .param("birthDay", "1990-01-01"))
                .andExpect(status().isOk());*/