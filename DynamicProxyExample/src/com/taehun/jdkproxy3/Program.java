package com.taehun.jdkproxy3;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Proxy;

public class Program {
	// 자바 17 이전 방식 ! 
	public static void callPrv17VCreationProxyAPI() throws Exception {
		// Step 1: Get the class loader of the target object
        ClassLoader classLoader = HelloWorldImpl.class.getClassLoader();

        // Step 2: Get the proxy class using Proxy.getProxyClass
        Class<?> proxyClass = Proxy.getProxyClass(classLoader, HelloWorld.class);

        // Step 3: Create an instance of the proxy class
        HelloWorld proxyInstance = (HelloWorld) proxyClass
            .getConstructor(InvocationHandler.class)
            .newInstance(new MyInvocationHandler(new HelloWorldImpl()));

        // Step 4: Use the proxy instance
        proxyInstance.sayHello();  // This will invoke the handler's invoke method
	}

	public static void main(String[] args) throws Exception{
		// Step 1: Use Proxy.newProxyInstance to create the proxy instance directly
        HelloWorld proxyInstance = (HelloWorld) Proxy.newProxyInstance(
            HelloWorldImpl.class.getClassLoader(),
            new Class<?>[]{HelloWorld.class},
            new MyInvocationHandler(new HelloWorldImpl())
        );
        
        // Step 2: Use the proxy instance
        proxyInstance.sayHello();  // This will invoke the handler's invoke method
        
        proxyInstance.sayGoodbye();         // Goodbye, world!
        proxyInstance.greet("Alice");       // Hello, Alice!
        
        proxyInstance.setLanguage("Spanish");
        proxyInstance.greet("Bob");         // Hola, Bob!

        String currentGreeting = proxyInstance.getGreeting();
        System.out.println("Current greeting: " + currentGreeting);  // Hola
		
	}

}
