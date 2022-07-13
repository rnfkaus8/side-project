package com.shoppingmall.request;

import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ItemSave {

    @NotBlank(message = "상품명을 입력하세요.")
    private String name;

    @NotNull(message = "상품 가격을 입력하세요.")
    @Min(value = 1000, message = "상품 가격은 1,000 원 이상이어야 합니다.")
    private Integer price;

    private String description;

    @Builder
    public ItemSave(String name, Integer price, String description) {
        this.name = name;
        this.price = price;
        this.description = description;
    }
}
