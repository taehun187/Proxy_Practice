# JDK Dynamic Proxy with Multiple Interfaces Example

이 프로젝트는 Java의 **JDK Dynamic Proxy**를 사용하여 두 개의 인터페이스(`InterfaceA`, `InterfaceB`)를 동적으로 구현하는 프록시 객체를 생성하고, 동일한 메서드 이름(`duplicateMethod`)을 가진 두 인터페이스를 처리하는 방법을 보여줍니다. 

---

## 프로젝트 개요

Java의 Dynamic Proxy를 활용하여 여러 인터페이스를 구현한 프록시 객체를 생성하고,  
각 인터페이스의 메서드 호출이 서로 다른 구현체로 위임되는 방식을 구현합니다.

이 프로젝트에서는 다음을 다룹니다:
1. 두 개의 인터페이스(`InterfaceA`, `InterfaceB`)와 각 인터페이스를 구현하는 클래스(`ClassA`, `ClassB`).
2. Dynamic Proxy를 통해 두 인터페이스를 구현하는 프록시 객체 생성.
3. 동일한 메서드 이름(`duplicateMethod`)을 가진 메서드 호출을 적절한 클래스(`ClassA` 또는 `ClassB`)로 위임.

---

## 주요 코드 구성

### 1. 인터페이스 정의
- **`InterfaceA`**
  ```java
  interface InterfaceA {
      void duplicateMethod();
  }
  ```

- **`InterfaceB`**
  ```java
  interface InterfaceB {
      void duplicateMethod();
  }
  ```

### 2. 클래스 구현
- **`ClassA`**: `InterfaceA`를 구현하는 클래스.
  ```java
  class ClassA implements InterfaceA {
      @Override
      public void duplicateMethod() {
          System.out.println("ClassA: duplicateMethod");
      }
  }
  ```

- **`ClassB`**: `InterfaceB`를 구현하는 클래스.
  ```java
  class ClassB implements InterfaceB {
      @Override
      public void duplicateMethod() {
          System.out.println("ClassB: duplicateMethod");
      }
  }
  ```

### 3. `InvocationHandler` 구현
- **핵심 역할**: 프록시 메서드 호출을 적절한 구현체(`ClassA` 또는 `ClassB`)로 위임.
  ```java
  class MyInvocationHandler implements InvocationHandler {
      private final Object objA;
      private final Object objB;

      public MyInvocationHandler(Object objA, Object objB) {
          this.objA = objA;
          this.objB = objB;
      }

      @Override
      public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
          System.out.println("Invoked method: " + method.getName());

          if (method.getDeclaringClass().isAssignableFrom(InterfaceA.class)) {
              return method.invoke(objA, args);
          } else if (method.getDeclaringClass().isAssignableFrom(InterfaceB.class)) {
              return method.invoke(objB, args);
          }

          return null;
      }
  }
  ```

### 4. Dynamic Proxy 생성 및 실행
- **프록시 객체 생성**:
  ```java
  Object proxyInstance = Proxy.newProxyInstance(
          DynamicProxyExample.class.getClassLoader(),
          new Class<?>[]{InterfaceA.class, InterfaceB.class},
          new MyInvocationHandler(classA, classB)
  );
  ```

- **프록시 메서드 호출**:
  ```java
  ((InterfaceA) proxyInstance).duplicateMethod();  // ClassA: duplicateMethod 출력
  ((InterfaceB) proxyInstance).duplicateMethod();  // ClassB: duplicateMethod 출력
  ```

---

## 실행 방법

1. Java 프로젝트를 생성합니다.
2. 위의 모든 소스 코드를 작성합니다.
3. `DynamicProxyExample` 클래스의 `main` 메서드를 실행합니다.
4. 콘솔에 출력되는 결과를 확인합니다.

---

## 출력 결과

```plaintext
Invoked method: duplicateMethod
ClassA: duplicateMethod
Invoked method: duplicateMethod
ClassB: duplicateMethod
```

### 주요 해석

1. **Dynamic Proxy 생성**
   - 프록시 객체는 `InterfaceA`와 `InterfaceB`를 동시에 구현하며, `InvocationHandler`를 통해 메서드 호출을 처리합니다.

2. **메서드 호출 위임**
   - `InterfaceA.duplicateMethod()` 호출 → `ClassA.duplicateMethod()` 실행.
   - `InterfaceB.duplicateMethod()` 호출 → `ClassB.duplicateMethod()` 실행.

---

## 학습 포인트

1. **Dynamic Proxy의 활용**
   - 런타임에 프록시 객체를 생성하여 여러 인터페이스를 구현할 수 있습니다.
   - 동일한 메서드 이름이라도 인터페이스 타입에 따라 호출이 적절히 처리됩니다.

2. **InvocationHandler 구현**
   - 프록시 객체의 모든 메서드 호출을 가로채고, 적절한 구현체로 위임하는 로직을 작성할 수 있습니다.

3. **실무 활용**
   - 다중 인터페이스를 구현하는 프록시를 사용해 AOP, 리모트 호출, 또는 다형성을 활용한 메서드 위임 로직을 구현할 수 있습니다.

---

## 참고 사항

- Dynamic Proxy는 **구체 클래스가 아닌 인터페이스**만 구현할 수 있습니다.
- 구체 클래스를 상속받아 프록시를 생성하려면 **CGLIB** 같은 라이브러리를 사용해야 합니다.
```
