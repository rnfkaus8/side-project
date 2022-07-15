package com.shoppingmall.response;

import com.shoppingmall.domain.Item;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ItemResponse {

    private Long id;
    private String name;
    private int price;
    private String description;

    public ItemResponse(Item item) {
        this.id = item.getId();
        this.name = item.getName();
        this.price = item.getPrice();
        this.description = item.getDescription();
    }

    @Builder
    public ItemResponse(Long id, String name, int price, String description) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
    }
}
