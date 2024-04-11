package com.example.payPhone.controllers;

import com.example.payPhone.dao.PaymentHistoryDAO;
import com.example.payPhone.enttities.PaymentHistory;
import com.example.payPhone.enttities.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class OperationControllerTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    private PaymentHistoryDAO paymentHistoryDAO;

    @Test
    @WithMockUser(username = "testuser")
    @DisplayName("This test checks the status OK returned by the controller method OperationController")
    void getPaymentHistory_ReturnCorrectResponseEntityWithHttpStatusOK() throws Exception {
        PaymentHistory paymentHistory = new PaymentHistory(new Date(), "testuser", BigDecimal.TEN);
        List<PaymentHistory> paymentHistoryList = Collections.singletonList(paymentHistory);
        Page<PaymentHistory> page = new PageImpl<>(paymentHistoryList);

        given(paymentHistoryDAO.findByUsername("testuser", PageRequest.of(0, 10)))
                .willReturn(page);

        mockMvc.perform(get("/history")
                        .param("username", "testuser")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    @Test
    @WithMockUser(username = "testuser")
    @DisplayName("This test checks the status FORBIDDEN returned by the controller method OperationController")
    void getPaymentHistory_ReturnIncorrectResponseEntityWithHttpStatusFORBIDDEN() throws Exception {
        PaymentHistory paymentHistory = new PaymentHistory(new Date(), "testuser", BigDecimal.TEN);
        List<PaymentHistory> paymentHistoryList = Collections.singletonList(paymentHistory);
        Page<PaymentHistory> page = new PageImpl<>(paymentHistoryList);

        given(paymentHistoryDAO.findByUsername("testuser", PageRequest.of(0, 10)))
                .willReturn(page);

        mockMvc.perform(get("/history")
                        .param("username", "anotherTestUser")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "testuser")
    void getShortPaymentHistory_ReturnCorrectResponseEntityWithHttpStatusOK() throws Exception {
        PaymentHistory paymentHistory = new PaymentHistory(new Date(), "testuser",
                BigDecimal.TEN, new User("username", "password"));
        List<PaymentHistory> paymentHistoryList = Collections.singletonList(paymentHistory);

        given(this.paymentHistoryDAO.findByUsername("testuser"))
                .willReturn(paymentHistoryList);

        mockMvc.perform(get("/short-history")
                        .param("username", "testuser")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "testuser")
    void getShortPaymentHistory_ReturnIncorrectResponseEntityWithHttpStatusFORBIDDEN() throws Exception {
        PaymentHistory paymentHistory = new PaymentHistory(new Date(), "testuser",
                BigDecimal.TEN, new User("username", "password"));
        List<PaymentHistory> paymentHistoryList = Collections.singletonList(paymentHistory);

        given(this.paymentHistoryDAO.findByUsername("testuser"))
                .willReturn(paymentHistoryList);

        mockMvc.perform(get("/short-history")
                        .param("username", "anotherTestuser")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }
}
