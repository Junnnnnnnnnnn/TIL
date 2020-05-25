# Lombok

### 개념

- DTO(VO) 클래스의 필드 값을 접근하기 위해서  get/set 메서드를 작성해 줘야 하지만 Lombok에서 Aonnotation을 사용해 동적으로 생성 하게 만들어 줌



## 사용 방법

#### lombok 사용하기

1. **lombok**을 통해 **Getter**와 **Setter**의 코드를 사용하지 않고 구현할 수 있다.
2. **lombok**의 **visual source code** 설치는 다음과 같다.
3. Ctrl + Shift + X 를 통해 Extenstions로 이동한다.
4. Lombok Annoations Support for VS Code 를 설치 한다.
5. **dependence를** 통한 라이브러리 설치가 필요하기 때문에 **maven이라면** 다음과 같은 코드를 pom.xml에 작성해준다.

> pom.xml

```xml
<dependency>

      <groupId>org.projectlombok</groupId>

	  <artifactId>lombok</artifactId>

      <version>1.18.12</version>

      <scope>provided</scope>

</dependency>
```

> personVO.java

```
@Data
public class personVO{
	private String name;
	private int phoneNum;
}
```

- 다음과 같이  필드 변수를 private로 설정하면 외부에서 변수 값들을 접근 할 수 없다. 그래서 getter/setter를 설정해 줘야 하는데 지금은 필드 변수가 두개라 복잡하지 않지만 여러개가 된다면 코드가 길어져 가독성이 떨어질 수가 있다.

  그래서 lombok 라이브러리를 사요하여 `@Data` annotation을 사용한다면 자동으로 getter/setter기능이 추가가 된다.