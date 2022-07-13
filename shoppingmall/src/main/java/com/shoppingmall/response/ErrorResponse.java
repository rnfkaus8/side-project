package com.shoppingmall.response;

import lombok.*;

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
public class ErrorResponse {

    private final String code;
    private final String message;

    private final Map<String, String> validation = new HashMap<>();

    @Builder
    public ErrorResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public void addValidation(String field, String errorMessage) {
        this.validation.put(field, errorMessage);
    }
}
