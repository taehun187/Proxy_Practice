
# Reflection and Array Manipulation Example

이 프로젝트는 Java의 **Reflection API**와 **배열 처리**를 활용하여 두 가지 주요 작업을 수행합니다:
1. 클래스 내 배열 필드를 동적으로 검색하는 프로그램.
2. `BufferedReader`의 백업 배열을 Reflection을 사용하여 조작하는 프로그램.

---

## 목차
1. [프로젝트 개요](#프로젝트-개요)
2. [주요 코드 구성](#주요-코드-구성)
3. [실행 방법](#실행-방법)
4. [출력 결과](#출력-결과)
5. [학습 포인트](#학습-포인트)

---

## 프로젝트 개요

### 1. ArrayFind
- 주어진 클래스의 **배열 필드**를 Reflection을 사용해 동적으로 검색하고 해당 배열의 메타데이터(필드명, 타입, 구성 요소 타입 등)를 출력합니다.

### 2. GrowBufferedReader
- Reflection을 사용하여 `BufferedReader` 내부의 **백업 배열(cb)**을 검색하고, 필요 시 배열 크기를 동적으로 확장합니다.

---

## 주요 코드 구성

### 1. ArrayFind
- **역할**: 클래스 내 배열 필드를 찾아 메타데이터를 출력.
- **코드**
  ```java
  public class ArrayFind {
      public static void main(String... args) {
          boolean found = false;
          try {
              Class<?> cls = Class.forName(args[0]);  // 입력받은 클래스 이름
              Field[] flds = cls.getDeclaredFields(); // 클래스의 필드 검색

              for (Field f : flds) {
                  Class<?> c = f.getType();
                  if (c.isArray()) { // 배열 필드인지 확인
                      found = true;
                      System.out.format(
                          "%s%n Field: %s%n Type: %s%n Component Type: %s%n",
                          f, f.getName(), c, c.getComponentType()
                      );
                  }
              }
              if (!found) {
                  System.out.format("No array fields%n");
              }
          } catch (ClassNotFoundException x) {
              x.printStackTrace();
          }
      }
  }
  ```

- **주요 동작**
  1. 클래스 이름을 인자로 받아 `Class.forName`으로 클래스 정보를 로드.
  2. `getDeclaredFields()`로 모든 필드를 검색.
  3. 배열 필드 여부를 확인 후 배열의 구성 요소 타입 출력.

---

### 2. GrowBufferedReader
- **역할**: `BufferedReader` 내부의 백업 배열(`cb`)을 검색하고, 필요 시 배열 크기를 확장.
- **코드**
  ```java
  public class GrowBufferedReader {
      private static final int srcBufSize = 10 * 1024; // 원본 버퍼 크기
      private static char[] src = new char[srcBufSize]; // 원본 데이터 배열
      static {
          src[srcBufSize - 1] = 'x'; // 마지막 요소를 'x'로 설정
      }
      private static CharArrayReader car = new CharArrayReader(src);

      public static void main(String... args) {
          try {
              BufferedReader br = new BufferedReader(car);
              Class<?> c = br.getClass();
              Field f = c.getDeclaredField("cb"); // BufferedReader의 "cb" 필드 검색

              f.setAccessible(true); // private 필드 접근 가능 설정
              char[] cbVal = char[].class.cast(f.get(br)); // 기존 배열 값 가져오기

              char[] newVal = Arrays.copyOf(cbVal, cbVal.length * 2); // 배열 크기 확장
              if (args.length > 0 && args[0].equals("grow")) {
                  f.set(br, newVal); // 확장된 배열로 설정
              }

              for (int i = 0; i < srcBufSize; i++) {
                  br.read(); // BufferedReader 읽기 작업
              }

              if (newVal[srcBufSize - 1] == src[srcBufSize - 1]) {
                  System.out.format("Using new backing array, size=%d%n", newVal.length);
              } else {
                  System.out.format("Using original backing array, size=%d%n", cbVal.length);
              }
          } catch (Exception x) {
              x.printStackTrace();
          }
      }
  }
  ```

- **주요 동작**
  1. `BufferedReader` 내부의 배열 필드(`cb`)를 Reflection으로 검색.
  2. 배열을 확장하고 `BufferedReader`에 설정.
  3. 배열 변경 여부를 확인하여 결과 출력.

---

## 실행 방법

### 1. ArrayFind 실행
1. 실행 시 클래스 이름을 인자로 전달:
   ```bash
   java com.taehun.arrayenum.ArrayFind com.taehun.arrayenum.SomeClass
   ```
2. 해당 클래스에 배열 필드가 있다면 필드 정보 출력.

### 2. GrowBufferedReader 실행
1. `grow` 플래그 없이 실행:
   ```bash
   java com.taehun.arrayenum.GrowBufferedReader
   ```
   - 원본 배열이 사용됩니다.
2. `grow` 플래그와 함께 실행:
   ```bash
   java com.taehun.arrayenum.GrowBufferedReader grow
   ```
   - 배열 크기를 확장하여 새로운 배열이 사용됩니다.

---

## 출력 결과

### 1. ArrayFind
- 배열 필드가 있을 경우:
  ```plaintext
  private char[] com.example.SomeClass.someArray
           Field: someArray
            Type: class [C
  Component Type: char
  ```
- 배열 필드가 없을 경우:
  ```plaintext
  No array fields
  ```

### 2. GrowBufferedReader
- 원본 배열 사용:
  ```plaintext
  Using original backing array, size=10240
  ```
- 확장된 배열 사용:
  ```plaintext
  Using new backing array, size=20480
  ```

---

## 학습 포인트

1. **Reflection 활용**
   - 클래스의 구조(필드, 메서드 등)를 런타임에 동적으로 탐색 및 조작.
   - `Field.get()`와 `Field.set()`을 사용해 필드 값을 가져오거나 설정 가능.

2. **배열 조작**
   - `Arrays.copyOf`를 사용해 배열 크기를 동적으로 확장.
   - 기존 배열과 확장된 배열 간 동작 확인.

3. **Private 필드 접근**
   - `setAccessible(true)`를 통해 private 필드에 접근.
   - Reflection으로 외부에서 접근 불가능한 데이터를 다룰 수 있음.

4. **예외 처리**
   - Reflection 사용 시 발생 가능한 다양한 예외(`ClassNotFoundException`, `NoSuchFieldException`, `IllegalAccessException`)를 우아하게 처리.

---

## 참고 사항

- Reflection은 강력하지만 성능에 영향을 미칠 수 있으므로 필요할 때만 사용하는 것이 좋습니다.
- Private 필드 접근은 보안 상의 문제를 야기할 수 있으므로 신중히 사용해야 합니다.
```
