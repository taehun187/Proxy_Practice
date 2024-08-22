package com.taehun.practice;

public class MyBean {
    private String name;
    private int age;

    // 디폴트 생성자
    public MyBean() {
    }

    // 접근자와 설정자 (Getter와 Setter)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
