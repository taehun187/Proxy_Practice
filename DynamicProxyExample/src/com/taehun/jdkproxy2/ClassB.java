package com.taehun.jdkproxy2;

class ClassB implements InterfaceB {
    @Override
    public void duplicateMethod() {
        System.out.println("ClassB: duplicateMethod");
    }
}
