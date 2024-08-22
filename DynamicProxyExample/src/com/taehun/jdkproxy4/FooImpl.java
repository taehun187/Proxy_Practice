package com.taehun.jdkproxy4;

public class FooImpl implements Foo {
	@Override
    public Object bar (Object obj) throws BazException {
		if (obj == null) {
			throw new BazException ("Input object cannot be null.");
		}
       // 추가 로직 이곳에 작성 가능 
       // 예를 들어, Obj의 특정 작업을 수행할 수 있음. 
		System.out.println("FooImpl의 bar메서드입니다");
		return obj;
    }
}
