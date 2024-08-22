package com.taehun.reflection;

public class Child extends Parent {

	public Integer age;
	
	// Class<T>.getClasses 메서드는 해당 클래스의
	// Static Nested class 또는 inner class 의 정보를 리턴한다
	// (단 public일 경우) 
	
	// Member Class
	public static class staticNestedClassOfChild {
		
	}
	
	public static class innerClassOfChild {
		
	}
	
	
}
