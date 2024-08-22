package com.taehun.jdkproxy4;

public class BazException extends Exception {
    // 기본 생성자
    public BazException() {
        
    }

    // 메시지를 포함한 생성자
    public BazException(String message) {
        super(message);
    }

    // 메시지와 원인(cause)을 포함한 생성자
    public BazException(String message, Throwable cause) {
        super(message, cause);
    }

    // 원인을 포함한 생성자
    public BazException(Throwable cause) {
        super(cause);
    }
}
