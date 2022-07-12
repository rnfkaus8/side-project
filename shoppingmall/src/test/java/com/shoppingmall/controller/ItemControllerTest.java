package com.shoppingmall.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shoppingmall.request.ItemSave;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
class ItemControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @DisplayName("/items GET 요청 시 Hello World 출력")
    void get() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/items"))
                .andExpect(status().isOk())
                .andExpect(content().string("hello world"))
                .andDo(print());
    }

    @Test
    @DisplayName("/items POST 요청 테스트")
    void save() throws Exception {
        String content = objectMapper.writeValueAsString(new ItemSave("itemName", 10000));
        mockMvc.perform(post("/items")
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isOk())
//                .andExpect()
                .andDo(print());
    }

    @Test
    @DisplayName("/items POST 요청 시 name 필수, price 필수, price >= 1000 성공")
    void itemSaveValid() throws Exception {
        String content = objectMapper.writeValueAsString(new ItemSave("name", 1000));
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
        mockMvc.perform(post("/items")
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new ItemSave("", 1000))))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.message").value("잘못된 요청입니다."))
                .andExpect(jsonPath("$.validation.name").value("상품명을 입력하세요."))
                .andDo(print());

        mockMvc.perform(post("/items")
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new ItemSave("not null", 999))))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.message").value("잘못된 요청입니다."))
                .andExpect(jsonPath("$.validation.price").value("상품 가격은 1,000 원 이상이어야 합니다."))
                .andDo(print());

        mockMvc.perform(post("/items")
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new ItemSave(null, null))))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.message").value("잘못된 요청입니다."))
                .andExpect(jsonPath("$.validation.name").value("상품명을 입력하세요."))
                .andExpect(jsonPath("$.validation.price").value("상품 가격을 입력하세요."))
                .andDo(print());
    }
}