package com.example.exception;

public class OrderAlreadyExistException extends Throwable{

    public OrderAlreadyExistException(String str){
        super(str);
    }

    public OrderAlreadyExistException() {
        super();
    }
}
