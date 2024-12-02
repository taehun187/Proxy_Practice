
# JDK Dynamic Proxy Advanced Example

이 프로젝트는 Java의 **JDK Dynamic Proxy**를 사용하여 고급 프록시 활용법을 보여줍니다.  
특히, 여러 구현체를 다루는 `Delegator`와 메서드 호출 전후 로직을 처리하는 `DebugProxy`를 포함하여 프록시 기반의 다양한 동작을 구현합니다.

---

## 프로젝트 개요

Dynamic Proxy를 활용하여 다음을 구현합니다:
1. **Delegator**: 여러 인터페이스와 구현체를 다룰 수 있는 프록시 핸들러.
2. **DebugProxy**: 메서드 호출 전후에 공통 로직(로깅 등)을 삽입.
3. **BazException**: 커스텀 예외를 정의하여 동작 중 예외 처리를 수행.
4. **Foo 인터페이스와 구현체**: 프록시를 통해 메서드 호출 흐름을 제어.

---

## 주요 코드 및 구성

### 1. `Foo` 인터페이스와 구현체
- **`Foo` 인터페이스**
  ```java
  public interface Foo {
      Object bar(Object obj) throws BazException;
  }
  ```

- **`FooImpl`**
  ```java
  public class FooImpl implements Foo {
      @Override
      public Object bar(Object obj) throws BazException {
          if (obj == null) {
              throw new BazException("Input object cannot be null.");
          }
          System.out.println("FooImpl의 bar 메서드입니다");
          return obj;
      }
  }
  ```

### 2. 커스텀 예외
- **`BazException`**
  ```java
  public class BazException extends Exception {
      public BazException(String message) {
          super(message);
      }
  }
  ```

### 3. `DebugProxy`
- **핵심 역할**: 메서드 호출 전후 로깅 처리.
  ```java
  public class DebugProxy implements InvocationHandler {
      private Object obj;

      public static Object newInstance(Object obj) {
          return Proxy.newProxyInstance(
              obj.getClass().getClassLoader(),
              obj.getClass().getInterfaces(),
              new DebugProxy(obj));
      }

      private DebugProxy(Object obj) {
          this.obj = obj;
      }

      @Override
      public Object invoke(Object proxy, Method m, Object[] args) throws Throwable {
          System.out.println("before method " + m.getName());
          Object result;
          try {
              result = m.invoke(obj, args);
          } catch (InvocationTargetException e) {
              throw e.getTargetException();
          } catch (Exception e) {
              throw new RuntimeException("unexpected invocation exception: " + e.getMessage());
          } finally {
              System.out.println("after method " + m.getName());
          }
          return result;
      }
  }
  ```

### 4. `Delegator`
- **핵심 역할**: 여러 인터페이스 및 구현체를 동적으로 위임.
  ```java
  public class Delegator implements InvocationHandler {
      private Class[] interfaces;
      private Object[] delegates;

      public Delegator(Class[] interfaces, Object[] delegates) {
          this.interfaces = interfaces.clone();
          this.delegates = delegates.clone();
      }

      @Override
      public Object invoke(Object proxy, Method m, Object[] args) throws Throwable {
          for (int i = 0; i < interfaces.length; i++) {
              if (m.getDeclaringClass().isAssignableFrom(interfaces[i])) {
                  return m.invoke(delegates[i], args);
              }
          }
          throw new InternalError("unexpected method dispatched: " + m);
      }
  }
  ```

### 5. `Program`
- **프록시 생성 및 실행**
  ```java
  public class Program {
      public static void main(String[] args) throws BazException {
          Class<?>[] proxyInterfaces = new Class[] { Foo.class };
          Object proxy = Proxy.newProxyInstance(
              Foo.class.getClassLoader(),
              proxyInterfaces,
              new Delegator(proxyInterfaces, new Object[] { new FooImpl() })
          );

          Foo foo = (Foo) proxy;
          System.out.println(foo.bar("hello"));  // 정상 호출
          System.out.println(proxy.toString()); // 프록시의 toString 메서드
          System.out.println(proxy.hashCode()); // 프록시의 hashCode 메서드
      }
  }
  ```

---

## 실행 방법

1. 모든 소스 코드를 작성합니다.
2. `Program.main()` 메서드를 실행합니다.
3. 출력 결과를 통해 프록시 객체의 동작과 커스텀 로직을 확인합니다.

---

## 출력 결과

```plaintext
FooImpl의 bar 메서드입니다
hello
com.sun.proxy.$Proxy0@HASHCODE
proxyHashCode
proxyEquals
```

### 주요 해석

1. **`Delegator`를 통한 프록시 호출**
   - `Delegator`는 여러 구현체에서 올바른 메서드를 호출합니다.
2. **`DebugProxy`의 로깅**
   - 메서드 호출 전후에 로깅을 추가합니다.
3. **프록시 객체 메서드 처리**
   - `toString`, `hashCode`, `equals`와 같은 기본 메서드를 커스터마이징하여 동작을 확인합니다.

---

## 학습 포인트

1. **Dynamic Proxy 활용**
   - 런타임에 프록시를 생성하고 호출 흐름을 동적으로 제어할 수 있습니다.

2. **InvocationHandler의 역할**
   - 메서드 호출을 가로채어 커스텀 로직을 삽입하거나 위임 로직을 구현합니다.

3. **예외 처리**
   - `BazException`과 같은 커스텀 예외를 사용해 메서드 실행 중 발생하는 문제를 처리합니다.

4. **실무 활용**
   - AOP(Aspect-Oriented Programming), 권한 관리, 트랜잭션 처리 등 다양한 실무 시나리오에서 활용 가능합니다.

---

## 참고 사항

- Dynamic Proxy는 **인터페이스 기반**으로 동작하며, 구체 클래스를 지원하려면 **CGLIB**과 같은 라이브러리를 사용해야 합니다.
```
