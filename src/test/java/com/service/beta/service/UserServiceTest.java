package com.service.beta.service;

import com.service.beta.data.dao.DaoUser;
import com.service.beta.data.response.Response;
import com.service.beta.services.business_logic.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

public class UserServiceTest {

    private UserService userService = new UserService();

    @Test
    void shouldCreateUser() {
        DaoUser user = new DaoUser();
        user.setUsername("admin");
        user.setEmail("admin@admin.com");
        user.setFirstName("admin");
        user.setLastName("admin");
        user.setPassword("admin");
       ResponseEntity<Response> user1 =userService.saveUser(user);
        System.out.println(user1.getBody());
        Assertions.assertNotNull(user1.getBody());
    }
}
