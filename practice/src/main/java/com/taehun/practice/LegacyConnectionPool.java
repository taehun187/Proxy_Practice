package com.taehun.practice;

//JavaBean 사양을 따르지 않는 클래스
public class LegacyConnectionPool {
 private String connectionString;

 // 디폴트 생성자가 없음
 public LegacyConnectionPool(String connectionString) {
     this.connectionString = connectionString;
 }

 // JavaBean 스타일의 접근자와 설정자가 없음
 public void connect() {
     System.out.println("Connecting to: " + connectionString);
 }
}