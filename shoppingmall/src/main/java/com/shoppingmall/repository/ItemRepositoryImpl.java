package com.shoppingmall.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.shoppingmall.domain.Item;
import com.shoppingmall.request.ItemSearch;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.shoppingmall.domain.QItem.item;

@RequiredArgsConstructor
public class ItemRepositoryImpl implements ItemRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Item> getList(ItemSearch itemSearch) {
        return jpaQueryFactory.selectFrom(item)
                .limit(itemSearch.getSize())
                .offset(itemSearch.getOffset())
                .orderBy(item.id.desc())
                .fetch();
    }
}
