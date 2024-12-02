
# JDK Dynamic Proxy with InvocationHandler Example

이 프로젝트는 Java의 **JDK Dynamic Proxy**를 활용하여 인터페이스(`HelloWorld`)의 메서드 호출을 동적으로 가로채고, 런타임에 동작을 추가하거나 변경하는 방법을 보여줍니다. Dynamic Proxy는 메서드 호출 전후에 로직을 삽입하거나 호출 흐름을 제어할 수 있는 강력한 도구입니다.

---

## 프로젝트 개요

Dynamic Proxy를 사용하여 다음을 구현합니다:
1. `HelloWorld` 인터페이스와 그 구현체 `HelloWorldImpl`를 프록시로 감쌉니다.
2. `InvocationHandler`를 사용하여 메서드 호출 전후에 공통 로직(로깅)을 삽입합니다.
3. Java 17 이전 방식(프록시 클래스를 직접 생성)과 최신 방식(`Proxy.newProxyInstance`)을 모두 다룹니다.

---

## 주요 코드 및 구성

### 1. 인터페이스 정의
- **`HelloWorld`**
  ```java
  public interface HelloWorld {
      void sayHello();
      void sayGoodbye();
      void greet(String name);
      void setLanguage(String language);
      String getGreeting();
  }
  ```

### 2. 구현 클래스
- **`HelloWorldImpl`**
  ```java
  public class HelloWorldImpl implements HelloWorld {
      private String language = "English";
      private String greeting = "Hello";

      @Override
      public void sayHello() {
          System.out.println(greeting + ", world!");
      }

      @Override
      public void sayGoodbye() {
          System.out.println("Goodbye, world!");
      }

      @Override
      public void greet(String name) {
          System.out.println(greeting + ", " + name + "!");
      }

      @Override
      public void setLanguage(String language) {
          this.language = language;
          if ("English".equalsIgnoreCase(language)) {
              this.greeting = "Hello";
          } else if ("Spanish".equalsIgnoreCase(language)) {
              this.greeting = "Hola";
          } else if ("French".equalsIgnoreCase(language)) {
              this.greeting = "Bonjour";
          } else {
              this.greeting = "Hello"; 
          }
      }

      @Override
      public String getGreeting() {
          return greeting;
      }
  }
  ```

### 3. InvocationHandler 구현
- **`MyInvocationHandler`**
  ```java
  public class MyInvocationHandler implements InvocationHandler {
      private Object target;

      public MyInvocationHandler(Object target) {
          this.target = target;
      }

      @Override
      public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
          System.out.println("Before method: " + method.getName());
          Object result = method.invoke(target, args);  // 실제 메서드 호출
          System.out.println("After method: " + method.getName());
          return result;
      }
  }
  ```

### 4. Proxy 생성 및 호출
- **Java 17 이전 방식**:
  ```java
  ClassLoader classLoader = HelloWorldImpl.class.getClassLoader();
  Class<?> proxyClass = Proxy.getProxyClass(classLoader, HelloWorld.class);

  HelloWorld proxyInstance = (HelloWorld) proxyClass
      .getConstructor(InvocationHandler.class)
      .newInstance(new MyInvocationHandler(new HelloWorldImpl()));

  proxyInstance.sayHello();  // Hello, world!
  ```

- **Java 최신 방식**:
  ```java
  HelloWorld proxyInstance = (HelloWorld) Proxy.newProxyInstance(
      HelloWorldImpl.class.getClassLoader(),
      new Class<?>[]{HelloWorld.class},
      new MyInvocationHandler(new HelloWorldImpl())
  );

  proxyInstance.sayHello();  // Hello, world!
  proxyInstance.setLanguage("Spanish");
  proxyInstance.greet("Alice");  // Hola, Alice!
  ```

---

## 실행 방법

1. 모든 소스 코드를 작성합니다.
2. `Program.main()` 메서드를 실행합니다.
3. 콘솔에 출력되는 프록시 동작 결과를 확인합니다.

---

## 출력 결과

```plaintext
Before method: sayHello
Hello, world!
After method: sayHello
Before method: sayGoodbye
Goodbye, world!
After method: sayGoodbye
Before method: greet
Hello, Alice!
After method: greet
Before method: setLanguage
After method: setLanguage
Before method: greet
Hola, Bob!
After method: greet
Before method: getGreeting
After method: getGreeting
Current greeting: Hola
```

---

## 학습 포인트

1. **Dynamic Proxy 활용**
   - `Proxy.newProxyInstance`를 사용해 런타임에 프록시 객체를 생성합니다.
   - 프록시 객체는 인터페이스를 구현하고, 메서드 호출을 가로채서 원하는 동작을 추가할 수 있습니다.

2. **InvocationHandler의 역할**
   - 메서드 호출 전후에 로깅, 트랜잭션 관리, 권한 검증 등의 로직을 삽입할 수 있습니다.

3. **Java 17 이전 방식 vs 최신 방식**
   - Java 17 이전 방식에서는 `Proxy.getProxyClass`를 사용해 프록시 클래스를 생성하고 인스턴스를 만듭니다.
   - 최신 방식에서는 `Proxy.newProxyInstance`를 사용해 더 간편하게 프록시 객체를 생성합니다.

4. **실무 활용**
   - AOP(Aspect-Oriented Programming) 구현.
   - 원격 호출(RPC) 프록시 구현.
   - 공통 로직(예: 로깅, 트랜잭션)을 삽입하여 코드 중복 제거.

---

## 참고 사항

- Dynamic Proxy는 인터페이스를 기반으로 프록시 객체를 생성합니다.
- 구체 클래스를 프록시로 만들려면 CGLIB 또는 다른 라이브러리를 사용해야 합니다.
```
