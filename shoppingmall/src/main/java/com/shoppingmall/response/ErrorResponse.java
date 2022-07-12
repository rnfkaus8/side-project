package com.shoppingmall.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/**
 * {
 *     "code" : "400",
 *     "message": "잘못된 요청입니다.",
 *     "validation":{
 *         "errorField1" : ".....",
 *         "errorField2" " ".....",
 *         ...
 *     }
 * }
 */
@Getter
@RequiredArgsConstructor
public class ErrorResponse {

    private final String code;
    private final String message;

    private Map<String, String> validation = new HashMap<>();

    public void addValidation(String field, String errorMessage) {
        this.validation.put(field, errorMessage);
    }
}
