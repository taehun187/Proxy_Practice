package com.taehun.reflection;

import java.io.PrintStream;
import java.util.HashSet;
import java.util.Set;

enum E { A, B }

public class Program {
	
	static Object o = new Object() {
        public void m() {} 
    };
    
    static Class<?> c = o.getClass().getEnclosingClass();
	
	
	public static void getObjectgetClass () {
		// Class<?>
		// <? extends String>
		Class c1 = "foo".getClass();
		
		Class c2 = E.A.getClass();
		
		byte[] bytes = new byte[1024];
		Class c3 = bytes.getClass(); 
		
		String[] strs = new String[10];
		Class c4 = strs.getClass();
		
		Set<String> s = new HashSet<String>();
		Class c5 = s.getClass();
	}
	
	public static void getClassSyntax() {
		Class c1 = String.class;
		Class c2 = Double.class;
		Class c3 = PrintStream.class;
		
	}
	
	public static void main(String[] args) {
		getObjectgetClass();
		
		getClassSyntax();	
		
		//program 클래스의 스태틱 필드인 c가 main메서드의 로컬 변수인
		// C에 의해 shadow 되었음
		
		
		Class<?>[] cs = Child.class.getClasses();
		for (Class clazz : cs)
			System.out.println(clazz.getTypeName());
		
		
		
		
	}

}
