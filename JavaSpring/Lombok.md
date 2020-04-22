# Lombok

- TODO 
  -  lombok 개념과 장점

- 개념
  - DTO 클래스의 필드 값을 접근하기 위해서  get/set 메서드를 작성해 줘야 하지만 Lombok에서 Aonnotation을 사용해 동적으로 생성 하게 만들어 줌



## 사용 방법

#### lombok 사용하기

1. **lombok**을 통해 **Getter**와 **Setter**의 코드를 사용하지 않고 구현할 수 있다.

2. **lombok**의 **visual source code** 설치는 다음과 같다.

3. Ctrl + Shift + X 를 통해 Extenstions로 이동한다.

4. Lombok Annoations Support for VS Code 를 설치 한다.

5. **dependence를** 통한 라이브러리 설치가 필요하기 때문에 **maven이라면** 다음과 같은 코드를 pom.xml에 작성해준다.

```xml
<dependency>

      <groupId>org.projectlombok</groupId>

	  <artifactId>lombok</artifactId>

      <version>1.18.12</version>

      <scope>provided</scope>

</dependency>
```

#### 