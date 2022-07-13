package com.shoppingmall.service;

import com.shoppingmall.domain.Item;
import com.shoppingmall.repository.ItemRepository;
import com.shoppingmall.request.ItemSave;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    public Item save(ItemSave itemSave){
        Item item = Item.builder()
                .name(itemSave.getName())
                .description(itemSave.getDescription())
                .price(itemSave.getPrice())
                .build();
        Item savedItem = itemRepository.save(item);
        return item;
    }
}