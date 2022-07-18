package com.shoppingmall.service;

import com.shoppingmall.domain.Item;
import com.shoppingmall.repository.ItemRepository;
import com.shoppingmall.request.ItemEdit;
import com.shoppingmall.request.ItemSave;
import com.shoppingmall.request.ItemSearch;
import com.shoppingmall.response.ItemResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class ItemServiceTest {

    @Autowired
    ItemService itemService;

    @Autowired
    ItemRepository itemRepository;

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

    @Test
    @DisplayName("상품 리스트 페이지 조회")
    void getItemList() {
        //given
        List<Item> requestItems = IntStream.range(0, 30)
                .mapToObj(i -> Item.builder()
                        .name("상품명_" + i)
                        .price(i * 1000)
                        .description("설명_" + i)
                        .build())
                .collect(Collectors.toList());
        itemRepository.saveAll(requestItems);

        //when
        ItemSearch itemSearch = ItemSearch.builder()
                .page(1)
                .build();
        List<ItemResponse> findItems = itemService.getItems(itemSearch);

        //then
        assertThat(findItems.size()).isEqualTo(10);
        assertThat(findItems.get(0).getName()).isEqualTo("상품명_29");
    }

    @Test
    @DisplayName("상품 명 수정")
    void editItemName() {
        //given
        Item item = Item.builder()
                .name("상품명1")
                .price(1000)
                .description("상품설명1")
                .build();

        itemRepository.save(item);

        ItemEdit itemEdit = ItemEdit.builder()
                .name("메소")
                .price(item.getPrice())
                .description(item.getDescription())
                .build();
        //when
        itemService.edit(item.getId(), itemEdit);

        //then
        Item changedItem = itemRepository.findById(item.getId())
                .orElseThrow(() -> new RuntimeException("상품이 존재하지 않습니다. id=" + item.getId()));

        assertThat(changedItem.getName()).isEqualTo(itemEdit.getName());
    }

    @Test
    @DisplayName("상품 가격 수정")
    void editItemPrice() {
        //given
        Item item = Item.builder()
                .name("상품명1")
                .price(1000)
                .description("상품설명1")
                .build();

        itemRepository.save(item);

        ItemEdit itemEdit = ItemEdit.builder()
                .name(item.getName())
                .price(10000)
                .description(item.getDescription())
                .build();
        //when
        itemService.edit(item.getId(), itemEdit);

        //then
        Item changedItem = itemRepository.findById(item.getId())
                .orElseThrow(() -> new RuntimeException("상품이 존재하지 않습니다. id=" + item.getId()));

        assertThat(changedItem.getPrice()).isEqualTo(itemEdit.getPrice());
    }

    @Test
    @DisplayName("상품 삭제")
    void deleteItem() {
        //given
        Item item = Item.builder()
                .name("상품명1")
                .price(1000)
                .description("상품설명1")
                .build();

        itemRepository.save(item);

        //when
        itemService.delete(item.getId());
        //then
        assertThat(0).isEqualTo(itemRepository.count());

    }
}