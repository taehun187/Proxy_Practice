package com.taehun.jdkproxy2;

class ClassA implements InterfaceA {
    @Override
    public void duplicateMethod() {
        System.out.println("ClassA: duplicateMethod");
    }
}
