# Spring boot jar로 jsp view 패키징 하기

## 개요

- 백엔드 기술이 마이크로 서비스에 도달 함으로서 적은 용량의 리소스 파일로 웹 어플리케이션을 서비스 하는 시대가 열렸다.

  war로 패키징을 해서 웹 서비스를 배포 할 수 있지만 더 가벼운 jar로 배포를 하기위해 오늘도 공부 한다.

## Code

### WAR 배포

- war 배포 환경에서 jsp를 패키징을 하기 위해서는 

  - main
    - webapp
      - WEB-INF
        - views

  로 이루어져 있다.

  이 이유는 war로 배포를 했을때 스프링에서 기본적으로 webapp를 기본 패스로 지정 하기 때문이다. 그래서 properties 파일에서 `WEB-INF/views` 만 prefix 설정 한다면 jsp를 패키징 해 view에 응답 할 수 있다.

**그러나!!**

### JAR 배포

jar 환경은 다르다

- jar 배포 환경에서 jsp를 패키징을 하기 위해서는 

  - resources
    - META-INF
      - resources
        - WEB-INF
          - views

  로 이루어져 있다.

  war배포와 달리 `resources/META-INF/resources` 로 경로가 잡혀 있어서 다음과 같이 폴더를 생성 해야한다.

### dependency

```xml
<dependency>
			<groupId>org.apache.tomcat.embed</groupId> 
			<artifactId>tomcat-embed-jasper</artifactId> 
			<scope>provided</scope> 
</dependency>
<dependency> 
			<groupId>javax.servlet</groupId> 
			<artifactId>jstl</artifactId> 
</dependency>
```

- dependency설청을 jar와 war 둘다 동일하게 jstl 과 jasper를 의존성 추가를 한다.