
# Spring Bean Configuration Example

이 프로젝트는 Spring Framework를 사용하여 다양한 방식으로 **Bean**을 정의하고 관리하는 예제를 보여줍니다.  
다음과 같은 주제를 다룹니다:
1. **Java Config**를 사용한 Bean 정의.
2. **JavaBean 스타일 객체**와 **Legacy 객체**의 차이.
3. **Static Factory Method**를 활용한 Bean 생성.

---

## 프로젝트 구성

### 1. `AppConfig`
- Spring의 **Java Config** 스타일을 사용하여 Bean을 정의.
- `@Configuration`과 `@Bean` 어노테이션을 활용.
- **코드**:
  ```java
  @Configuration
  public class AppConfig {

      @Bean
      public MyBean myBean() {
          MyBean myBean = new MyBean();
          myBean.setName("John Doe");
          myBean.setAge(30);
          return myBean;
      }
  }
  ```

### 2. `MyBean`
- **JavaBean 스타일 객체**로, Spring에서 기본적으로 관리 가능한 Bean.
- **코드**:
  ```java
  public class MyBean {
      private String name;
      private int age;

      public MyBean() {
      }

      public String getName() {
          return name;
      }

      public void setName(String name) {
          this.name = name;
      }

      public int getAge() {
          return age;
      }

      public void setAge(int age) {
          this.age = age;
      }
  }
  ```

### 3. `LegacyConnectionPool`
- JavaBean 규약을 따르지 않는 **Legacy 객체**.
- Spring에서 기본적인 방식으로 관리할 수 없으며, 사용자 정의 설정이 필요.
- **코드**:
  ```java
  public class LegacyConnectionPool {
      private String connectionString;

      public LegacyConnectionPool(String connectionString) {
          this.connectionString = connectionString;
      }

      public void connect() {
          System.out.println("Connecting to: " + connectionString);
      }
  }
  ```

### 4. `MyService`
- **Static Factory Method**를 사용해 인스턴스를 생성하는 클래스.
- **코드**:
  ```java
  public class MyService {

      private String message;

      private MyService(String message) {
          this.message = message;
      }

      public static MyService createInstance() {
          return new MyService("Hello from MyService!");
      }

      public String getMessage() {
          return message;
      }
  }
  ```

---

## 실행 방법

1. `AppConfig`에서 정의된 Bean을 Spring Context에 등록.
2. Application Context에서 Bean을 가져와 사용하는 코드를 작성:
   ```java
   AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
   MyBean myBean = context.getBean(MyBean.class);
   System.out.println("Name: " + myBean.getName() + ", Age: " + myBean.getAge());
   context.close();
   ```
3. Legacy 객체와 Static Factory Bean은 별도 설정이 필요:
   - `LegacyConnectionPool`은 사용자 정의 설정을 통해 Spring Bean으로 등록.
   - `MyService`는 `@Bean` 메서드에서 Static Factory Method를 호출.

---

## 학습 포인트

1. **Java Config 기반의 Bean 정의**
   - `@Configuration`과 `@Bean` 어노테이션을 사용하여 Bean을 정의.
   - XML 설정을 대체하여 더 간결하고 직관적으로 Bean 관리 가능.

2. **JavaBean 스타일의 객체 관리**
   - 디폴트 생성자와 Getter/Setter를 가진 클래스는 Spring에서 기본적으로 관리 가능.

3. **Legacy 객체 관리**
   - JavaBean 규약을 따르지 않는 객체는 Spring에서 직접 관리하기 어렵기 때문에 사용자 정의 설정 필요.

4. **Static Factory Method를 통한 Bean 생성**
   - Static Factory Method를 활용하여 객체 생성 로직을 캡슐화하고 Bean 정의.

---

## 코드 실행 예제

### `AppConfig`로 Bean 가져오기
```java
AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

// MyBean 사용
MyBean myBean = context.getBean(MyBean.class);
System.out.println("Name: " + myBean.getName() + ", Age: " + myBean.getAge());

// Static Factory Method로 생성된 MyService 사용
MyService myService = MyService.createInstance();
System.out.println(myService.getMessage());

context.close();
```

---

## 주요 출력 결과

```plaintext
Name: John Doe, Age: 30
Hello from MyService!
```

---

## 참고 사항

- Java Config는 XML 기반 설정보다 간결하며, 유지보수가 용이.
- Legacy 객체는 Spring에서 직접 관리하기 어려우므로 사용자 정의 설정이 필요.
- Static Factory Method는 특정 로직을 포함한 객체 생성을 간단히 처리할 수 있지만, Bean 정의 시 추가 설정이 필요.
```
