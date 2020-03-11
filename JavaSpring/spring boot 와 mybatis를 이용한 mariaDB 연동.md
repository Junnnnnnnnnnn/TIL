# spring boot 와 mybatis를 이용한 mariaDB 연동

- 이번 프로젝트에서는 ORM으로 mybatis를 DB로는 mariaDB를 사용하기로 결정했다.
- mybatis는 개발자가 원한는 sql 쿼리문을 작성할 수 있기때문에 사용했다
- mariaDB는 mysql, oracleDB에 비해 빠르고 opensource라는 장점을 가진다.
- spring boot의 IDE는 visual studio code를 사용했다



## 구현 방법

### pom.xml의 dependence 작성

```xml
 <!--mybatis 설정-->
        <dependency>
            <groupId>org.mybatis.spring.boot</groupId>
            <artifactId>mybatis-spring-boot-starter</artifactId>
            <version>2.1.1</version>
        </dependency>
<!--mybatis 설정-->
<!-- mariaDb -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-jdbc</artifactId>
        </dependency>
        <dependency>
            <groupId>org.mariadb.jdbc</groupId>
            <artifactId>mariadb-java-client</artifactId>
        </dependency>
<!-- mariaDb -->
```

- mybatis , mariaDB 라이브러리를 사용하기 위해서 등록 해준다.

### model (DTO)

```java
package com.example.demo.db.model;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Data
@Alias("loginInfo")
public class LoginInfo {

    private int id;
    private String name;

    public LoginInfo(){}
    public LoginInfo (int id , String name){
        this.id = id;
        this.name = name;
    }
}
```

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

#### type-alias-package 사용하기

- 개발자가 만든 DTO를 사용하기 위해서는 보통 경로값을 작성하는데 코드가 길어지므로 코드 간결화를 하기 위해 alias를 사용한다. 
- model 코드에서 `@Alias`에 작성한 문자열을 사용하면 해당 DTO의 경로값을 가진것과 같다.
- Alias 지정은 application.properties에서 해준다.

```properties
# application.proterties
mybatis.type-aliases-package=com.example.demo.db.model
```

- 다음과 같이 선언 하면 model이하 class는 alias를 사용할 수 있다.

### DAO

- DB와 직접적으로 연결, 연산 , 처리를 하기 위한 로직을 담당한다.

```java
package com.example.demo.db.repository;

import java.util.List;

import com.example.demo.db.model.LoginInfo;

import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface LoginInfoMapper {
    //return타입은 LoginInfo를 타입으로 가지는 List객체 이다.
    public List<LoginInfo> selectAllLoginInfo() throws Exception;
}
```

- sql문을 작성한 xml을 호출하기 위한 interface이다.
- `@mapper` 란 해당 interface를  mapper로 사용하기 위해 IOC에 bean등록을 해준다.
- 사용 메서드는 selectAllLoginInfo() 로 Login정보를 모두 검색하는 select문이다. 반환 타입으로는 LoginInfo타입 List객체이다.

### Service

- 실직적으로 로직이 이루어 지는 곳이다.

```java
//DAO객체로 DB와 직접적으로 데이터를 주고 받는 로직이다.
//@Service를 이용해 IOC에 bean등록을 해준다.
@Service
@Transactional
public class DBService  {

    //mapper를 통한 sql쿼리를 전송하고 데이터를 가져오기 위해 LoginInfoMapper를 IOC를 통해 의존성 주입을 해준다.
    @Autowired
    LoginInfoMapper loginInfoMapper;

    //return 타입으로 select문을 통해 필요로 하는 값을 가져 온다.
    public List<LoginInfo> selectAllLoginInfo() throws Exception {
        return loginInfoMapper.selectAllLoginInfo();
    }

}
```

- Mapper를 사용하기 위해 LoginInfoMapper를 의존성 주입을 해준다.
- selectAllLoginInfo() 메서드를 정의해준다.



### Controller

- 브라우저의 Request , Reponse를 당담한다. 
- 서버와 밀접한 연관이 있다.

```java
//어노테이션으로 @Controller를 사용해 IOC에 bean등록 해준다.
@Controller
public class ProjcetController {

    //만들어 놓은 dbSerivce interface를 IOC에 등록되어 있는 bean을 의존성 주입 해준다.
    @Autowired
    DBService dbService;
    
    @RequestMapping(value="/2", method=RequestMethod.GET)
    public @ResponseBody List<LoginInfo> two() throws Exception {
        return dbService.selectAllLoginInfo();
    }   
}   
```

1. RESTfull api를 통해  "/2" url으로 GET 메서드가 들어왔을때 실행하는 함수이다.
2. 에러가 뜰 시 Exception을 통해 예외 처리를 해준다.
3. @ReponseBody를 통해 return되는 값을 html body 부분에 바로 출력한다.
4. return 타입으로 만들어 놓은 LoginInfo type을 사용했고 db에 저장되어 있는 모든 정보를 가져오는 메서드이기 때문에 List메서드도 함께 사용 해준다.
5. return 값으로 deService에 있는 메서드 selectAllLoginInfo()를 실행 시킨다.



### Mapper.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.example.demo.db.repository.LoginInfoMapper">

    <select id="selectAllLoginInfo" resultType="loginInfo">
        select * from testTable
    </select>

</mapper>
```

1. **Mapper**용 **xml**를 사용하기 위해 DTD를 설정

2. **namespace는** **mapper와** 매칭이 되는 **interface의** 경로를 지정해준다.

3.     **select문을** 사용할 것이고 id는 **interface에서** 만들어 놓은 메서드 **selectAllLoginInfo를** 작성해주고 **resultType으로는** interface만들어 놓은 return 타입을 작성해 주는데 resultType으로 model로 만들어놓은 DTO의 해당 경로를 기입해야 하지만 **Alias로** **type_package를** 했으므로 간결하게
       **loginInfo로** 작성 할 수 있다.