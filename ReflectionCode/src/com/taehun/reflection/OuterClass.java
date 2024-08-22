package com.taehun.reflection;

import java.lang.reflect.Field;

public class OuterClass {

    // Non-static nested class (Inner class)
    class InnerClass {
        private int innerValue = 10;
        private String innerString = "Hello, World!";
    }

    public static void main(String[] args) {
        try {
            // OuterClass의 인스턴스를 생성
            OuterClass outer = new OuterClass();
            // InnerClass의 인스턴스를 생성
            InnerClass inner = outer.new InnerClass();

            // InnerClass의 Class 객체를 얻음
            Class<?> innerClass = inner.getClass();

            // InnerClass의 필드 정보를 얻음
            Field[] fields = innerClass.getDeclaredFields();

            // 각 필드의 이름과 타입을 출력
            for (Field field : fields) {
                field.setAccessible(true); // private 필드 접근 허용
                System.out.println("Field name: " + field.getName());
                System.out.println("Field type: " + field.getType());
                System.out.println("Field value: " + field.get(inner)); // 필드 값 출력
                System.out.println("Field isSynthetic:" + field.isSynthetic());
                System.out.println("-----------------------");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
