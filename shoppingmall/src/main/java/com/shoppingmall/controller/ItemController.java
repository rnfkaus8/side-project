package com.shoppingmall.controller;

import com.shoppingmall.request.ItemSave;
import com.shoppingmall.response.ItemResponse;
import com.shoppingmall.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/items")
    public List<ItemResponse> getList(Pageable pageable) {
        return itemService.getItems(pageable);
    }

    @GetMapping("/items/{itemId}")
    public ItemResponse get(@PathVariable Long itemId) {
        return itemService.getItem(itemId);
    }

    @PostMapping("/items")
    public void save(@RequestBody @Valid ItemSave request) {
        itemService.save(request);
    }

}
