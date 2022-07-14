package com.shoppingmall.controller;

import com.shoppingmall.domain.Item;
import com.shoppingmall.request.ItemSave;
import com.shoppingmall.response.ItemResponse;
import com.shoppingmall.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/items/{itemId}")
    public ItemResponse get(@PathVariable Long itemId) {
        return itemService.getItem(itemId);
    }

    @PostMapping("/items")
    public void save(@RequestBody @Valid ItemSave request) {
        itemService.save(request);
    }

}
