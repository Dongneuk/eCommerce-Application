package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.mockConstants.MockConstants;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.CreateUserRequest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserControllerTest {

    private UserController userController;
    private UserRepository userRepo = mock(UserRepository.class);
    private CartRepository cartRepo = mock(CartRepository.class);
    private BCryptPasswordEncoder encoder = mock(BCryptPasswordEncoder.class);

    private MockConstants mockConstants;

    @Before
    public void setUp() {
        userController = new UserController();
        TestUtils.injectObjects(userController, "userRepository", userRepo);
        TestUtils.injectObjects(userController, "cartRepository", cartRepo);
        TestUtils.injectObjects(userController, "bCryptPasswordEncoder", encoder);

    }

    @Test
    public void findByIdTest() throws Exception {

        when(userRepo.findById(1L)).thenReturn(Optional.of(MockConstants.getUser()));
        ResponseEntity<User> response = userController.findById(1L);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void findByUserNameTest() throws Exception {
        //given

        when(userRepo.findByUsername("east")).thenReturn(MockConstants.getUser());
        //when
        ResponseEntity<User> response = userController.findByUserName("east");
        //then
        assertEquals(1, response.getBody().getId());
        assertEquals("east", response.getBody().getUsername());
        assertEquals(200, response.getStatusCodeValue());
    }


    @Test
    public void createUserTest() throws Exception {

        when(encoder.encode("password")).thenReturn("hashedPassword");
        CreateUserRequest userRequest = new CreateUserRequest();
        userRequest.setUsername("east");
        userRequest.setPassword("password");
        userRequest.setConfirmedPassword("password");


        ResponseEntity<User> response = userController.createUser(userRequest);
        User user1 = response.getBody();

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(user1);

        assertEquals(0, user1.getId());
        assertEquals("east", user1.getUsername());
        assertEquals("hashedPassword", user1.getPassword());


        Cart cart1 = new Cart();
        cartRepo.save(cart1);
        user1.setCart(cart1);

        // user 한명당 카트 한개.
        assertEquals(cart1, user1.getCart());


    }




}
