package com.example.payPhone.controllers;

import com.example.payPhone.dao.UserDAO;
import com.example.payPhone.enttities.User;
import com.example.payPhone.processors.BalanceProcessor;
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

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@AutoConfigureMockMvc(printOnlyOnFailure = false)
class BalanceControllerTest {

    @Autowired
    MockMvc mockMvc;
    @MockBean
    UserDAO userDAO;
    @MockBean
    BalanceProcessor balanceProcessor;
    @Test
    @WithMockUser(username = "testuser")
    @DisplayName("This test simulates the execution of a method without errors with the status OK")
    void getBalance_ReturnedCorrectDataWithStatusOK() throws Exception {
        when(userDAO.findByUsername("testuser")).thenReturn(new User("testuser", "password"));
        this.mockMvc.perform(get("/users/testuser")
                .with(httpBasic("testuser", "password")))
                .andExpect(content().string("User: testuser, Balance: null"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "testuser")
    @DisplayName("This test simulates viewing information about a user that he is not")
    void getBalance_ReturnedDenyAccessDataWithStatusFORBIDDEN() throws Exception {
        when(userDAO.findByUsername("anotherTestuser")).thenReturn(new User("anotherTestuser", "password"));
        this.mockMvc.perform(get("/users/anotherTestuser")
                        .with(httpBasic("testuser", "password")))
                .andExpect(content().string("Access to someone else's account information is prohibited"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "testuser")
    @DisplayName("This test simulates viewing user information with a user search error")
    void getBalance_ReturnedNullDataWithStatusNOT_FOUND() throws Exception {
        when(userDAO.findByUsername("nullUsername")).thenReturn(null);
        this.mockMvc.perform(get("/users/testuser")
                        .with(httpBasic("testuser", "password")))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "testuser")
    @DisplayName("This test simulates the successful completion of a funds transfer operation")
    void payment_ReturnedSuccessfulFundTransferWithStatus_OK() throws Exception {
        when(userDAO.findByUsername(anyString())).thenReturn(new User());
        when(balanceProcessor.checkBalance(any(User.class), any(BigDecimal.class), any(User.class))).thenReturn(true);
        mockMvc.perform(put("/payment")
                        .param("username", "anotherTestUser")
                        .param("amount", "100"))
                .andExpect(status().isOk())
                .andExpect(content().string("The payment was successful!"));
    }

    @Test
    @WithMockUser(username = "testuser")
    @DisplayName("This test simulates the process of an error in completing a funds transfer due to insufficient funds")
    void payment_ReturnsAnnsIufficientFundsErrorWithStatus_NOT_FOUND() throws Exception {
        User user = new User("testuser", "password", new BigDecimal(100L));
        when(userDAO.findByUsername(anyString())).thenReturn(user);
        when(balanceProcessor.checkBalance(any(User.class), any(BigDecimal.class), any(User.class))).thenReturn(false);
        mockMvc.perform(put("/payment")
                        .param("username", "anotherTestUser")
                        .param("amount", "150"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Oops! Something went wrong! Your balance: 100"));
    }
}