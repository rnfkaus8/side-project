package com.shoppingmall.service;

import com.shoppingmall.domain.Item;
import com.shoppingmall.request.ItemSave;
import com.shoppingmall.response.ItemResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ItemServiceTest {

    @Autowired
    private ItemService itemService;

    @Test
    @DisplayName("상품 저장 : 성공")
    void save() {
        //given
        ItemSave itemSave = ItemSave.builder()
                .name("상품명1")
                .price(1000)
                .build();
        //when
        Item savedItem = itemService.save(itemSave);

        //then
        assertThat(savedItem.getName()).isEqualTo(itemSave.getName());
        assertThat(savedItem.getPrice()).isEqualTo(itemSave.getPrice());
        assertThat(savedItem.getDescription()).isNull();
    }

    @Test
    @DisplayName("상품 단건 조회 : 성공")
    void getItem() {
        //given
        ItemSave itemSave = ItemSave.builder()
                .name("상품명1")
                .price(1000)
                .build();
        Item savedItem = itemService.save(itemSave);

        //when
        ItemResponse findItem = itemService.getItem(savedItem.getId());

        //then
        assertThat(savedItem.getName()).isEqualTo(findItem.getName());
        assertThat(savedItem.getPrice()).isEqualTo(findItem.getPrice());
        assertThat(savedItem.getDescription()).isEqualTo(findItem.getDescription());
    }
}