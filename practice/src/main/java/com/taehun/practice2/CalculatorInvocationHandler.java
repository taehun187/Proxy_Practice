package com.taehun.practice2;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

class CalculatorInvocationHandler implements InvocationHandler {
    private final Object targetObject;

    public CalculatorInvocationHandler(Object targetObject) {
        this.targetObject = targetObject;
    }

    @Override
    public Object invoke(Object proxyInstance, Method method, Object[] methodArgs) throws Throwable {
        System.out.println("Method " + method.getName() + 
        		" is called with arguments " + java.util.Arrays.toString(methodArgs));
        Object result = method.invoke(targetObject, methodArgs);  // 실제 메서드 호출
        System.out.println("Method " + method.getName() + " returned " + result);
        return result;
    }
}
