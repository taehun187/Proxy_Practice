package com.taehun.practice;

public class MyService {

    private String message;

    // private 생성자
    private MyService(String message) {
        this.message = message;
    }

    // static 팩토리 메서드
    public static MyService createInstance() {
        return new MyService("Hello from MyService!");
    }

    public String getMessage() {
        return message;
    }
}