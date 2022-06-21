package com.hu.expense.UserTest;

import com.hu.expense.entity.User;
import com.hu.expense.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class LoginTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testLogin(){
        User user = new User();
        user.setUsername("Test");
        user.setFirstName("Te");
        user.setLastName("St");
        user.setPassword("test");
        user.setEmail("test@test.com");
        userRepository.save(user);
        assertEquals(user, userRepository.findByUsername("Test"));
        userRepository.deleteById("Test");
    }
}
