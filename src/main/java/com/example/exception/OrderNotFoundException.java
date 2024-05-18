package com.example.exception;

public class OrderNotFoundException extends Throwable {
    private static final String NOT_FOUND = "Order with id: %s, not found";

    public OrderNotFoundException(long id){
        super(String.format(NOT_FOUND, id));
    }
}
