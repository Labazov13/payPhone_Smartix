package com.example.payPhone.controllers;

import com.example.payPhone.dao.UserDAO;
import com.example.payPhone.enttities.User;
import com.example.payPhone.processors.BalanceProcessor;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
    void getBalance_ReturnedCorrectDataWithStatusOK() throws Exception {
        when(userDAO.findByUsername("testuser")).thenReturn(new User("testuser", "password"));
        this.mockMvc.perform(get("/users/testuser")
                .with(httpBasic("testuser", "password")))
                .andExpect(content().string("User: testuser, Balance: null"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "testuser")
    void getBalance_ReturnedDenyAccessDataWithStatusFORBIDDEN() throws Exception {
        when(userDAO.findByUsername("anotherTestuser")).thenReturn(new User("anotherTestuser", "password"));
        this.mockMvc.perform(get("/users/anotherTestuser")
                        .with(httpBasic("testuser", "password")))
                .andExpect(content().string("Access to someone else's account information is prohibited"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "testuser")
    void getBalance_ReturnedNullDataWithStatusNOT_FOUND() throws Exception {
        when(userDAO.findByUsername("nullUsername")).thenReturn(null);
        this.mockMvc.perform(get("/users/testuser")
                        .with(httpBasic("testuser", "password")))
                .andExpect(status().isNotFound());
    }

}