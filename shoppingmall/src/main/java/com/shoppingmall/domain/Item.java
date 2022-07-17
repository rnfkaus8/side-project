package com.shoppingmall.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"id", "name", "price", "description"})
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Lob
    private String description;

    private int price;

    @Builder
    public Item(String name, String description, int price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public ItemEditor.ItemEditorBuilder toEditor() {
        return ItemEditor.builder()
                .name(name)
                .price(price)
                .description(description);
    }

    public void edit(ItemEditor itemEditor) {
        this.name = itemEditor.getName();
        this.price = itemEditor.getPrice();
        this.description = itemEditor.getDescription();
    }
}
