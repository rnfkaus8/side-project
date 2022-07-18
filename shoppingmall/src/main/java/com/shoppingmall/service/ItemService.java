package com.shoppingmall.service;

import com.shoppingmall.domain.Item;
import com.shoppingmall.domain.ItemEditor;
import com.shoppingmall.exception.ItemNotFound;
import com.shoppingmall.repository.ItemRepository;
import com.shoppingmall.request.ItemEdit;
import com.shoppingmall.request.ItemSave;
import com.shoppingmall.request.ItemSearch;
import com.shoppingmall.response.ItemResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
                .orElseThrow(ItemNotFound::new);

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

    @Transactional
    public ItemResponse edit(Long id, ItemEdit itemEdit) {
        Item item = itemRepository.findById(id)
                .orElseThrow(ItemNotFound::new);

        ItemEditor.ItemEditorBuilder itemEditorBuilder = item.toEditor();

        ItemEditor itemEditor = itemEditorBuilder
                .name(itemEdit.getName())
                .price(itemEdit.getPrice())
                .description(itemEdit.getDescription())
                .build();

        item.edit(itemEditor);

        return new ItemResponse(item);
    }

    public void delete(Long itemId) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(ItemNotFound::new);

        itemRepository.delete(item);
    }
}
