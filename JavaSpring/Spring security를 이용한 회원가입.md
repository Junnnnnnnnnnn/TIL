# Spring security를 이용한 회원가입

회원가입에서 **security를** 써야하는 이유는 여러가지 이지만 내가 생각하는 기능은 **비밀번호를** **암호화** 한다음 db에 저장하는것이라고 생각한다.

개발자가 만든 웹 어플리케이션이라고는 하지만 사용자의 비밀번호는 보안을 보장 받을 **권리가** 있다고 생각한다. 그래서 이번 개발은 **security** **라이브러리를** 이용한 회원 가입이다.

## CustomUserDetailsService.java

비밀번호를 암호화 하고 db에 insert하는 구문이다.

DetailsService에서 security 관련 로직을 처리한다

```java
    @Autowired
    UserMapper userMapper;

    //로그인이 될때 자동 실행 되는 메서드
    //클라이언트에서 입력한 ID가 DB에 있는가 찾아 본후 있다면 그 Member를 Spring에서 관리하는 Member에 추가해준다.
    //없다면 Exception을 던져 준다.
    @Transactional
    public void Insert_Member(Member member){
        //db에 비밀번호를 입력하기 전에 사용자의 보안을 위해 password를 암호화 해준다
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        //암호화된 password를 member 변수에 set해 준다
        member.setPassword(passwordEncoder.encode(member.getPassword()));
        //userMapper에 정의 되어 있는 insert 가 실행 된다.
        userMapper.insertMember(member);
    }
```

- UserMapper를 `@Autowired` 한 이유는 mybatis특성상 xml에 정의되어 있는 sql문을 사용해야 하기 때문에 mapper를 사용해야 한
- BcryptPasswordEncoder는 spring security에 있는 기능으로서 특정 문자를 암호화 하는 기능을 가졌다.
- BcryptPasswordEncoder를 통해 입력 받은 비밀번호를 암호화 한뒤 db에 insert한다.



## userMapper.xml

sql 구문 처리는 userMapper.xml에서 해준다



```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.example.demo.db.repository.UserMapper">
    
    <select id="readUser" parameterType = "String" resultType="member">
        select * from user where username = #{username}
    </select>

    <select id="readAuthority" parameterType = "String" resultType="String">
        select authority_name from authority where username = #{username}
    </select>

    <!-- insert 구문 -->
    <!-- id는 service에 구현된 메서드 명이고 parameterType은 Alias-package를 통해 com.example.demo.db.model.Member타입을 member로 간소화 시켰다 -->
    <insert id="insertMember" parameterType = "member">
        INSERT INTO `user` (`username`, `password`, `name`, `isAccountNonExpired`, `isAccountNonLocked`, `isCredentialsNonExpired`, `isEnabled`)
        VALUES (#{username ,jdbcType = VARCHAR}, #{password,jdbcType = VARCHAR}, #{name,jdbcType = VARCHAR}, 1, 1, 1, 1);    
    </insert>

</mapper>
```

- insert 태그를 사용해서 insert 쿼리문을 작성한다.
- insert의 id값은 메서드 명 , parameterType은 VO객체인 member 변수를 사용해준다.
- insert 문의 values로는 member의 필드 변수인 username, password, name을 사용해주고 db column 과 타입을 맞추기 위해 `jdbcType = VARCHAR` 로 설정 해준다.



## ProjcetController.java

@Controller에 해당하는 파일로 브라우저에서 요청한 url을 dispatcher servlet가 가장 적절한 servlet과 매핑을 시켜주고 그에 해당하는 메서드를 실행시키는 java파일이다.



```java
    // 회원가입을 통한 사용자 정보 insert 구문 회원가입 할때 정보를 숨기기 위해 POST 방식을 구현
    @RequestMapping(value = "/openapi/insertMember", method = RequestMethod.POST)
    //브라우저에서 요청과 함께 날라온 id , password , name 값을 매개변수로 받아 온다.
    public String inser_member(
        @RequestParam("id")String id,
        @RequestParam("password")String password,
        @RequestParam("name")String name
    ) {
        //매개변수의 값을 Member 생성자를 통해 member에 set해준다.
        Member member = new Member(id, password, name);
        // Service에서 구현해 놓은 insert_Member메서드를 실행 시켜준다
        detailsService.Insert_Member(member);
        //redirect를 통해 / <- url에 대한 servlet을 실행 시켜 준다.
        return "redirect:/";
    }
```

- 매개 변수로 받은 3개의 매개변수 id, password, name를 member타입을가지는 변수에 생성자를 이용해 초기화 시킨다
- 왜냐하면 detailsService에 구현해 놓은 메서드는 member 변수를 매개변수로 받기 때문이다.
- return 값으로는 redirect를 통해 다음 url에 해당되는 jsp파일을 출력 시켜 준다.