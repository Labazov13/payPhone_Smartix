package com.example.payPhone.controllers;

import com.example.payPhone.dao.UserDAO;
import com.example.payPhone.enttities.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
    @DisplayName("This test simulates the process of successfully changing user data")
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
    @DisplayName("This test simulates the process where the findByUsername method does not find the user")
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
    @DisplayName("This test simulates the process in which the date of birth is entered incorrectly")
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
