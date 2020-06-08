# Spring boot tamplate

## 개요

- 부트 프레임워크를 보다 빠르게 사용하기 위해서 만들어 놓음

## 환경

- spring boot framework
  - 2.3.0
- sql mapper
  - mybatis
- DB
  - mariaDB
- JAVA 객체 전달
  - thymeleaf

## 설정

### dependency

#### thymeleaf

```xml
<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-thymeleaf</artifactId>
</dependency>
```

#### mariaDB Connection

```xml
<dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-jdbc</artifactId>
</dependency>
<dependency>
            <groupId>org.mariadb.jdbc</groupId>
            <artifactId>mariadb-java-client</artifactId>
</dependency>

```

#### mybatis

```xml
<dependency>
            <groupId>org.mybatis.spring.boot</groupId>
            <artifactId>mybatis-spring-boot-starter</artifactId>
            <version>2.1.2</version>
</dependency>
```

#### lombok

```xml
<dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <version>1.18.12</version>
            <scope>provided</scope>
</dependency>
```

- vs code 에디터 사용시 extention 설치 필요



### 필요 파일

application.proterties

```properties
spring.datasource.driverClassName=org.mariadb.jdbc.Driver
spring.datasource.url=jdbc:mysql:#############
spring.datasource.username=#########
spring.datasource.password=############

mybatis.mapper-locations: mybatis/mapper/*.xml


spring.thymeleaf.prefix=classpath:templates/
spring.thymeleaf.check-template-location=true
spring.thymeleaf.suffix=.html
spring.thymeleaf.mode=HTML5
spring.thymeleaf.cache=false
spring.thymeleaf.order=0
```

- db 설정
- mapper 설정
- thymeleaf 설정