package com.shoppingmall.controller;

import com.shoppingmall.request.ItemEdit;
import com.shoppingmall.request.ItemSave;
import com.shoppingmall.request.ItemSearch;
import com.shoppingmall.response.ItemResponse;
import com.shoppingmall.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/items")
    public List<ItemResponse> getList(@ModelAttribute ItemSearch itemSearch) {
        return itemService.getItems(itemSearch);
    }

    @GetMapping("/items/{itemId}")
    public ItemResponse get(@PathVariable Long itemId) {
        return itemService.getItem(itemId);
    }

    @PostMapping("/items")
    public void save(@RequestBody @Valid ItemSave request) {
        itemService.save(request);
    }

    @PatchMapping("/items/{itemId}")
    public ItemResponse edit(@PathVariable Long itemId, @RequestBody @Valid ItemEdit request) {
        return itemService.edit(itemId, request);
    }

}
