package com.taehun.practice2;

import java.lang.reflect.Proxy;

import com.taehun.practice2.Calculator;
import com.taehun.practice2.CalculatorImpl;
import com.taehun.practice2.CalculatorInvocationHandler;

public class DynamicProxyExample {
    public static void main(String[] args) {
        // 실제 객체 생성
        Calculator realCalculator = new CalculatorImpl();

        // 다이나믹 프록시 생성
        Calculator proxyCalculator = (Calculator) Proxy.newProxyInstance (
                realCalculator.getClass().getClassLoader(),     // 1. 클래스 로더
                new Class<?>[]{Calculator.class},               // 2. 인터페이스 목록
                new CalculatorInvocationHandler(realCalculator) // 3. InvocationHandler 구현체
        );

        // 프록시 객체의 메서드 호출
        int sum = proxyCalculator.add(3, 7);
        System.out.println("Sum: " + sum);

        int difference = proxyCalculator.subtract(10, 4);
        System.out.println("Difference: " + difference);
    }
}
