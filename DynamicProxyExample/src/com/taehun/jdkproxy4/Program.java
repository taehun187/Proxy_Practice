package com.taehun.jdkproxy4;

import java.lang.reflect.Proxy;

public class Program {

	public static void main(String[] args) throws BazException {
//		Foo foo = (Foo) DebugProxy.newInstance(new FooImpl());
//		foo.bar(null);
		
		Class<?>[] proxyInterfaces = new Class[] { Foo.class };
		Object[] objs = new Object[] {new FooImpl()};
		Object proxy = (Foo) Proxy.newProxyInstance(Foo.class.getClassLoader(), 
				proxyInterfaces, 
				new Delegator(proxyInterfaces,
						new Object[] { new FooImpl () }));
		
		Foo foo = (Foo) proxy;
		
		String str = "hello";
		System.out.println(foo.bar(str));
		
		System.out.println(proxy.toString());
		System.out.println(proxy.hashCode());
		System.out.println(proxy.equals(foo));		
		
	}

}
