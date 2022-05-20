package com.example.demo.mockConstants;

import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.requests.ModifyCartRequest;

import java.math.BigDecimal;

public class MockConstants {

    public MockConstants() {
    }

    public static ModifyCartRequest getRequest() {
        ModifyCartRequest modifyCartRequest = new ModifyCartRequest();
        modifyCartRequest.setUsername("east");
        modifyCartRequest.setItemId(1L);
        modifyCartRequest.setQuantity(1);
        return modifyCartRequest;

    }

    public static Item getItem() {
        Item item = new Item();
        item.setId(1L);
        item.setName("testName");
        item.setDescription("testDescription");
        item.setPrice(BigDecimal.valueOf(100));
        return item;
    }

    public static Cart getCart() {
        Cart cart = new Cart();
        cart.addItem(getItem());
        return cart;
    }

    public static User getUserWithCart() {
        User user = new User();

        user.setId(1L);
        user.setUsername("east");
        user.setPassword("password");
        user.setCart(getCart());

        return user;
    }


}
