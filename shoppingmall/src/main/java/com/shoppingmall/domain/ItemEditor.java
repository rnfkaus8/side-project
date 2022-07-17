package com.shoppingmall.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ItemEditor {

    private final String name;
    private final int price;
    private final String description;

    @Builder
    public ItemEditor(String name, int price, String description) {
        this.name = name;
        this.price = price;
        this.description = description;
    }
}
