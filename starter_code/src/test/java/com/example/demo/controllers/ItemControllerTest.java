package com.example.demo.controllers;

import com.example.demo.model.persistence.repositories.ItemRepository;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
class ItemControllerTest {

    @InjectMocks
    ItemController itemController;

    @Mock
    ItemRepository itemRepository;


}