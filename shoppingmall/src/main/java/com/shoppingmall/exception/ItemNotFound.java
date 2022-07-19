package com.shoppingmall.exception;

public class ItemNotFound extends ShoppingmallException {

    private static final String MESSAGE = "존재하지 않는 상품입니다.";

    public ItemNotFound() {
        super(MESSAGE);
    }

    public ItemNotFound(Throwable cause) {
        super(MESSAGE, cause);
    }

    @Override
    public int getStatusCode() {
        return 404;
    }
}
