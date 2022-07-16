package com.shoppingmall.repository;

import com.shoppingmall.domain.Item;
import com.shoppingmall.request.ItemSearch;

import java.util.List;

public interface ItemRepositoryCustom {
    List<Item> getList(ItemSearch itemSearch);
}
