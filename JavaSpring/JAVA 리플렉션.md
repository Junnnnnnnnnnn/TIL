# JAVA 리플렉션

자동화 처리를 위해 리플렉션 기법을 사용한다.

JVM에서 실행되는 애플리케이션의 런타임 동작을 **검사**하거나 **수정**할 수 있는 기능이 필요한 프로그램에서 사용된다.

#### 즉 클래스의 구조를 개발자가 확인 할 수 있고 값을 가져오거나 메소드를 호출하는데 사용된다.

**Reflection을** 사용하는 기술은 **spring** **Framework**, **ORM의** 대표적인 기술인 **Hibernate**,**jackson** 라이브러리 등에 사용 됨

> ### Spring Framework 에서의 Reflection
>
> 어플리케이션이 실행 후 객체가 호출 되면 Spring container에서 빈에 등록 되어 있는 객체들 중  호출 될 당시 객채의 인스턴스를 생성한다. 
>
> 그때 필요한 기술이 java 의 Reflection이다.



- TODO
  - ~~더 정확한 개념~~
  - 이해 돕는 예제

### 예제

```java

Class c = Data.class;
//Class c = Class.forName("클래스이름");

Method[] m = c.getMethods();                     

Field[] f = c.getFields();
Constructor[] cs = c.getConstructors();
Class[] inter = c.getInterfaces();
Class superClass = c.getSuperclass();


출처: https://gyrfalcon.tistory.com/entry/Java-Reflection [Minsub's Blog]
```

**위 코드와 같이 클래스를 개발자가 직접 수정하고 확인할 수 있다.**

- TODO
  - ~~더 정확한 개념~~
  - ~~이해 돕는 예제~~

