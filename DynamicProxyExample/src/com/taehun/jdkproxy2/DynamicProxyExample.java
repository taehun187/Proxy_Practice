package com.taehun.jdkproxy2;


import java.lang.reflect.Proxy;

public class DynamicProxyExample {
    public static void main(String[] args) {
        InterfaceA classA = new ClassA();
        InterfaceB classB = new ClassB();

        Object proxyInstance = Proxy.newProxyInstance(
                DynamicProxyExample.class.getClassLoader(),
                new Class<?>[]{InterfaceA.class, InterfaceB.class},
                new MyInvocationHandler(classA, classB)
        );

        ((InterfaceA) proxyInstance).duplicateMethod();  // ClassA: duplicateMethod 출력
        ((InterfaceB) proxyInstance).duplicateMethod();  // ClassB: duplicateMethod 출력
    }
}
