package com.shoppingmall.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shoppingmall.domain.Item;
import com.shoppingmall.repository.ItemRepository;
import com.shoppingmall.request.ItemSave;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@AutoConfigureMockMvc
@SpringBootTest
@Transactional
class ItemControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    ItemRepository itemRepository;

    @Test
    @DisplayName("상품 단건 조회")
    void get() throws Exception {
        //given
        Item item = Item.builder()
                .name("상품명1")
                .price(10000)
                .build();
        itemRepository.save(item);
        //expected
        mockMvc.perform(MockMvcRequestBuilders.get("/items/{itemId}", item.getId())
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(item.getId()))
                .andExpect(jsonPath("$.name").value(item.getName()))
                .andExpect(jsonPath("$.price").value(item.getPrice()))
                .andExpect(jsonPath("$.description").value(item.getDescription()))
                .andDo(print());

    }

    @Test
    @DisplayName("상품 리스트 조회")
    void getList() throws Exception {
        //given
        Item item1 = Item.builder()
                .name("상품명1")
                .price(10000)
                .build();

        Item item2 = Item.builder()
                .name("상품명2")
                .price(20000)
                .build();
        itemRepository.saveAll(List.of(item1, item2));
        //expected
        mockMvc.perform(MockMvcRequestBuilders.get("/items")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(2)))
                .andExpect(jsonPath("$[1].id").value(item2.getId()))
                .andExpect(jsonPath("$[1].name").value(item2.getName()))
                .andExpect(jsonPath("$[1].price").value(item2.getPrice()))
                .andExpect(jsonPath("$[1].description").value(item2.getDescription()))
                .andDo(print());

    }

    @Test
    @DisplayName("/items POST 요청 테스트")
    void save() throws Exception {
        //given
//        ItemSave itemSave = new ItemSave("itemName", 10000, "");
        ItemSave itemSave = ItemSave.builder()
                .name("itemName")
                .price(10000)
                .build();
        String content = objectMapper.writeValueAsString(itemSave);
        mockMvc.perform(post("/items")
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isOk())
                .andExpect(content().string(""))
                .andDo(print());

        //when
        Item findItem = itemRepository.findAll().get(0);

        //then
        assertThat(itemRepository.count()).isEqualTo(1L);
        assertThat(findItem.getName()).isEqualTo(itemSave.getName());
        assertThat(findItem.getPrice()).isEqualTo(itemSave.getPrice());
        assertThat(findItem.getDescription()).isEqualTo(itemSave.getDescription());

    }

    @Test
    @DisplayName("/items POST 요청 시 name 필수, price 필수, price >= 1000 성공")
    void itemSaveValid() throws Exception {
        ItemSave itemSave = ItemSave.builder()
                .name("name")
                .price(1000)
                .build();
        String content = objectMapper.writeValueAsString(itemSave);
        mockMvc.perform(post("/items")
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("/items POST 요청 시 name 필수, price 필수, price >= 1000 실패")
    void itemSaveValidFailed() throws Exception {
        ItemSave emptyName = ItemSave.builder()
                .price(1000)
                .build();
        mockMvc.perform(post("/items")
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(emptyName)))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.message").value("잘못된 요청입니다."))
                .andExpect(jsonPath("$.validation.name").value("상품명을 입력하세요."))
                .andDo(print());

        ItemSave lessPrice = new ItemSave("not null", 999, "");
        mockMvc.perform(post("/items")
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(lessPrice)))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.message").value("잘못된 요청입니다."))
                .andExpect(jsonPath("$.validation.price").value("상품 가격은 1,000 원 이상이어야 합니다."))
                .andDo(print());

        ItemSave emptyNameAndPrice = ItemSave.builder()
                .build();
        mockMvc.perform(post("/items")
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(emptyNameAndPrice)))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.message").value("잘못된 요청입니다."))
                .andExpect(jsonPath("$.validation.name").value("상품명을 입력하세요."))
                .andExpect(jsonPath("$.validation.price").value("상품 가격을 입력하세요."))
                .andDo(print());
    }

}