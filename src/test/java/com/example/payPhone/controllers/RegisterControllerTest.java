package com.example.payPhone.controllers;

import com.example.payPhone.dao.UserDAO;
import com.example.payPhone.enttities.User;
import com.example.payPhone.processors.RegisterProcessor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@AutoConfigureMockMvc(printOnlyOnFailure = false)
class RegisterControllerTest {

    @MockBean
    UserDAO userDAO;
    @MockBean
    RegisterProcessor registerProcessor;



    @Test
    void getRegister_ReturnResponseEntityWithHttpStatusOK() throws Exception {
        RegisterController registerController = new RegisterController(registerProcessor);
        User user  = new User("username", "password");
        var responseEntity = registerController.getRegister(registerProcessor.setDefaultSettings(user));
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        if (responseEntity.getBody() instanceof User user1){
            assertEquals(user.getUsername(), user1.getUsername());
            verify(this.userDAO).save(user);
            assertNotNull(user.getId());
            assertEquals(responseEntity.getBody(), user);
        }
    }
}