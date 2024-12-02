# JDK Dynamic Proxy Example

이 프로젝트는 Java의 **JDK Dynamic Proxy**를 사용하여 두 개의 인터페이스(`FirstInterface`, `SecondInterface`)를 구현하는 프록시 객체를 생성하고, 이를 활용하여 메서드 호출을 동적으로 처리하는 방법을 보여줍니다.

---

## 프로젝트 개요

Dynamic Proxy는 런타임에 프록시 클래스를 생성하여 특정 인터페이스를 구현하는 객체를 동적으로 생성합니다.  
이 프로젝트에서는 다음을 구현합니다:
1. 두 개의 인터페이스(`FirstInterface`, `SecondInterface`)를 활용한 프록시 객체 생성.
2. `InvocationHandler`를 통해 메서드 호출을 가로채고, 호출된 메서드 정보를 동적으로 처리.

---

## 주요 코드 및 구성

### 1. 인터페이스 정의

- **`FirstInterface`**
  ```java
  interface FirstInterface {
      void firstMethod();
  }
  ```

- **`SecondInterface`**
  ```java
  interface SecondInterface {
      void secondMethod();
  }
  ```

### 2. `InvocationHandler` 구현

- **핵심 역할**: 프록시 객체의 모든 메서드 호출을 가로채어 동적으로 처리.
  ```java
  class MyInvocationHandler implements InvocationHandler {
      @Override
      public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
          System.out.println("MyInvocationHandler:: invoke : class: " + proxy.getClass());
          if (method.getDeclaringClass().equals(FirstInterface.class)) {
              System.out.println("Invoked method from FirstInterface: " + method.getName());
          } else if (method.getDeclaringClass().equals(SecondInterface.class)) {
              System.out.println("Invoked method from SecondInterface: " + method.getName());
          } else {
              System.out.println("Invoked method from unknown interface: " + method.getName());
          }
          return null;
      }
  }
  ```

### 3. 프록시 생성 및 호출

- **프록시 객체 생성**:
  두 개의 인터페이스를 동적으로 구현하는 프록시 객체를 생성.
  ```java
  Object proxy1 = Proxy.newProxyInstance(
          classLoader,
          new Class<?>[]{FirstInterface.class, SecondInterface.class},
          new MyInvocationHandler()
  );

  Object proxy2 = Proxy.newProxyInstance(
          classLoader,
          new Class<?>[]{SecondInterface.class, FirstInterface.class},
          new MyInvocationHandler()
  );
  ```

- **프록시 메서드 호출**:
  인터페이스의 메서드를 호출하면 `InvocationHandler`의 `invoke` 메서드가 실행됩니다.
  ```java
  FirstInterface firstProxy1 = (FirstInterface) proxy1;
  firstProxy1.firstMethod();

  SecondInterface secondProxy1 = (SecondInterface) proxy1;
  secondProxy1.secondMethod();
  ```

---

## 실행 방법

1. 프로젝트에 소스 코드를 작성합니다.
2. `ProxyExample` 클래스의 `main` 메서드를 실행합니다.
3. 출력 결과를 확인하여 Dynamic Proxy의 동작을 분석합니다.

---

## 출력 결과

```plaintext
proxy1 class: class com.sun.proxy.$Proxy0
proxy2 class: class com.sun.proxy.$Proxy1
MyInvocationHandler:: invoke : class: class com.sun.proxy.$Proxy0
Invoked method from FirstInterface: firstMethod
MyInvocationHandler:: invoke : class: class com.sun.proxy.$Proxy0
Invoked method from SecondInterface: secondMethod
MyInvocationHandler:: invoke : class: class com.sun.proxy.$Proxy1
Invoked method from FirstInterface: firstMethod
MyInvocationHandler:: invoke : class: class com.sun.proxy.$Proxy0
Invoked method from SecondInterface: secondMethod
Are proxy1 and proxy2 classes the same? false
```

### 주요 해석

1. **프록시 클래스**
   - `proxy1`과 `proxy2`는 동일한 인터페이스를 기반으로 하지만 순서가 다르기 때문에 서로 다른 프록시 클래스를 생성(`$Proxy0`, `$Proxy1`).
   
2. **`InvocationHandler` 호출**
   - `invoke` 메서드가 호출되어 메서드의 소속 인터페이스와 이름을 출력.

---

## 학습 포인트

1. **Dynamic Proxy 동작 원리**
   - `Proxy.newProxyInstance()`를 사용하여 런타임에 프록시 클래스를 생성합니다.
   - 지정된 인터페이스에 따라 프록시 클래스가 결정됩니다.

2. **`InvocationHandler` 활용**
   - 메서드 호출을 가로채서 커스터마이징된 로직을 실행합니다.
   - 호출된 메서드의 이름, 소속 인터페이스를 동적으로 확인할 수 있습니다.

3. **프록시 클래스의 특성**
   - 프록시 클래스는 컴파일 시점에 존재하지 않으며, 런타임에 동적으로 생성됩니다.
   - 같은 인터페이스 조합이라도 순서가 다르면 다른 프록시 클래스가 생성됩니다.

4. **실무 활용**
   - AOP(Aspect-Oriented Programming): 메서드 호출 전후 로직 삽입.
   - 로깅 및 권한 검사: 메서드 호출 시 로깅 처리 및 권한 확인.
   - 원격 호출: 메서드 호출을 원격 서버로 전달.

---

## 참고 사항

- Dynamic Proxy는 구체 클래스가 아닌 **인터페이스**만 구현할 수 있습니다.
- 구체 클래스를 상속받아 프록시를 생성하려면 **CGLIB** 또는 다른 라이브러리를 사용해야 합니다.
```
