package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.mockConstants.MockConstants;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.ItemRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.ModifyCartRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static com.example.demo.mockConstants.MockConstants.getItem;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CartControllerTest {

    @InjectMocks // Mock 이 붙은 객체들을 @InjectMocks가 붙은 객체에 주입시켜준다.
    private CartController cartController;

    @Mock
    private UserRepository userRepo;

    @Mock
    private CartRepository cartRepo;

    @Mock
    private ItemRepository itemRepo;

    @Mock
    private ModifyCartRequest modifyCartRequest;

    private MockConstants mockConstants;

    private ModifyCartRequest mcr;

    @Before
    public void before() {
        when(userRepo.findByUsername("east")).thenReturn(MockConstants.getUser());
        when(itemRepo.findById(1L)).thenReturn(Optional.of(getItem()));

        mcr = MockConstants.getRequest();


    }

    @Test
    public void addToCartTest() throws Exception {
        //given
        ResponseEntity<Cart> response = cartController.addToCart(mcr);

        //when
        Cart cart = response.getBody();
        List<Item> items = cart.getItems();

        //then
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(2, items.size());
        assertEquals(BigDecimal.valueOf(2000), cart.getTotal());
    }

    @Test(expected = RuntimeException.class)
    public void addToCart_failed() throws Exception {
        doThrow(new RuntimeException("Cart Repository Error")).when(cartRepo).save(any(Cart.class));
        cartController.addToCart(mcr);
    }
    
    @Test
    public void removeFromCartTest() throws Exception {
        //given
        ResponseEntity<Cart> response = cartController.removeFromCart(mcr);

        //when
        Cart cart = response.getBody();
        List<Item> items = cart.getItems();

        //then
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(0, items.size());
    }




}