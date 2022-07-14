package com.shoppingmall.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ItemResponse {

    private Long id;
    private String name;
    private int price;
    private String description;

    @Builder
    public ItemResponse(Long id, String name, int price, String description) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
    }
}
