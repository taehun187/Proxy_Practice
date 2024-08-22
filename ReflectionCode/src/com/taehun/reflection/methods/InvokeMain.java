package com.taehun.reflection.methods;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class InvokeMain {
	public static void main(String... args) {
		try { 
			Class<?> c = Class.forName(args[0]);
			Class[] argTypes = new Class[] { String[].class };
			Method main = c.getDeclaredMethod("main", argTypes);
			String[] mainArgs = Arrays.copyOfRange(args, 1, args.length);
			System.out.format("invoking %s.main()%n", c.getName());
			main.invoke(null, (Object) mainArgs);

			// 실제 코드에서는 이러한 예외를 더 우아하게 처리해야 합니다.
		} catch (ClassNotFoundException x) {
			x.printStackTrace();
		} catch (NoSuchMethodException x) {
			x.printStackTrace();
		} catch (IllegalAccessException x) {
			x.printStackTrace();
		} catch (InvocationTargetException x) {
			x.printStackTrace();
		}
	}
}
