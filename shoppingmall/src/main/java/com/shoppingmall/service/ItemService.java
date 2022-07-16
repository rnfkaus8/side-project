package com.shoppingmall.service;

import com.shoppingmall.domain.Item;
import com.shoppingmall.repository.ItemRepository;
import com.shoppingmall.request.ItemSave;
import com.shoppingmall.request.ItemSearch;
import com.shoppingmall.response.ItemResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    public ItemResponse getItem(Long itemId) {

        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."));

        return ItemResponse.builder()
                .id(item.getId())
                .name(item.getName())
                .price(item.getPrice())
                .description(item.getDescription())
                .build();

    }

    public List<ItemResponse> getItems(ItemSearch itemSearch) {
        return itemRepository.getList(itemSearch)
                .stream().map(ItemResponse::new)
                .collect(Collectors.toList());
    }
}
