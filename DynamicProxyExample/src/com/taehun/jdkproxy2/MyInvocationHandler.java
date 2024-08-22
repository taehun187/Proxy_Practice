package com.taehun.jdkproxy2;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

class MyInvocationHandler implements InvocationHandler {
    private final Object objA;
    private final Object objB;

    public MyInvocationHandler(Object objA, Object objB) {
        this.objA = objA;
        this.objB = objB;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("Invoked method: " + method.getName());

        if (method.getDeclaringClass().isAssignableFrom(InterfaceA.class)) {
            return method.invoke(objA, args);
        } else if (method.getDeclaringClass().isAssignableFrom(InterfaceB.class)) {
            return method.invoke(objB, args);
        }

        return null;
    }
}
