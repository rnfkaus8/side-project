package com.shoppingmall.controller;

import com.shoppingmall.request.ItemSave;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
public class ItemController {

    @GetMapping("/items")
    public String get() {
        return "hello world";
    }

    @PostMapping("/items")
    public Map<String, String> save(@RequestBody @Valid ItemSave request) {
        log.info("request={}", request);
        log.info("name={}, price={}", request.getName(), request.getPrice());

        return Map.of();
    }
}
