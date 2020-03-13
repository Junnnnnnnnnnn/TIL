# Spring security 라이브러리 사용해서 login 하기

- **클라이언트에서** login을 통한 접근 권한을 부여하기 위해서는 보안이 필요하다. 그 보안 부분을 spring으로 코딩을 해보자.



## DB

- DB에서 만든 로그인 테이블

![](./readme/table.jpg)

위 테이블을 select해서 정보 조회를 해야 하기 때문에 Member 객체를 만들어 준다



### model

```java
package com.example.demo.db.model;

import java.util.Collection;

import org.apache.ibatis.type.Alias;
import org.springframework.security.core.GrantedAuthority;

import lombok.Data;

//lombok 라이브러리를 이용한 자동 getter/setter 사용
@Data
//Alias를 통해 application-context에 지정해 놓은 현재 member의 경로를 type-package를 member로 해놓는다
@Alias("member")
public class Member {
    //login 기본정보 ID,PW,NAME
    private String username;
    private String password;
    private String name;
    private boolean isAccountNonExpired; //계정이 만료 되었는지 리턴 True를 리턴하면 만료되지 않음
    private boolean isAccountNonLocked; // 계정이 잠겨있는지를 리턴 Ture를 리턴하면 잠겨있지 않음
    private boolean isCredentialsNonExpired; // 계정의 패스워드가 만료되었는지를 리턴 True를 리턴하면 만료되지 않음
    private boolean isEnabled; // 계정이 사용가능한 계정인지를 리턴 True를 리턴하면 사용가능한 계정을 의미
    private Collection<? extends GrantedAuthority> authorities; // 계정이 갖고 있는 권한 목록 리턴
}
```

- **로그인의 정보를** 담는 **Member**객체는 기본적으로 **유저의 정보**(login ID , password , 사용자 이름, 주소, 전화번호 등등) 과 **계정의 정보**(만료, 잠금, 활성화, 권한)등을 담는 **필드변수**로 구성되어 있다.



### Repository , service, mapper.xml

- **repository**와 **service** 그리고 xml은 db에 쿼리문을 보내주면 데이터 처리후 그에 맞는 return 값을 보내준다.
- 상세한 기술 구현은 현 폴더에 `spring boot 와 mybatis를 이용한 mariaDB 연동.md` 파일을 참고



## security

- **security**는 **spring에서** 제공하는 보안관련 라이브러리이다.
- 구성요소로는 
  - model
  - serivce
  - handler
  - adapter

### model

- Spring security에서 제공하는 User를 상속 받아 Spring이 관리하는 Member 객체를 만든다

```java
package com.example.demo.security.model;

import com.example.demo.db.model.Member;
import org.springframework.security.core.userdetails.User;

import lombok.Data;
//lombok annotation
@Data
public class SecurityMember extends User {

    private static final long serialVersionUID = 1L;

    private String ip;

    public SecurityMember(Member member){
        super(member.getUsername() , member.getPassword(), member.getAuthorities());
    }
}
```

- 해당 VO객체는 **security** **service**에서 만들어 준다.
- 생성자 매개변수로 받은 **member**타입의 **member**를 받고 User에 있는 get 메서드를 통해 **SpringSecurityContenxt**에서 관리하는 **VO**객체에 초기화한다.

### service

```java
package com.example.demo.security.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.db.model.Member;
import com.example.demo.db.repository.UserMapper;
import com.example.demo.security.model.SecurityMember;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
//UserDetailsService 어렵다 공부!!
@Service
public class CustomUserDetailsService implements UserDetailsService {

    //Spring Security User를 위한 인증정보를 사용하기 위해서 ROLE_를 인증 문자열 앞에 붙혀 준다.
    private static final String ROLE_PREFIX = "ROLE_";

    //Member의 정보를 DB에서 검색하기 위해서 의존성 주입
    @Autowired
    UserMapper userMapper;

    //로그인이 될때 자동 실행 되는 메서드
    //클라이언트에서 입력한 ID가 DB에 있는가 찾아 본후 있다면 그 Member를 Spring에서 관리하는 Member에 추가해준다.
    //없다면 Exception을 던져 준다.
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //매개변수로 받은 username을 member로 받는다
        Member member = userMapper.readUser(username);
        //만약 Memver가 null이 아니라면 
        //DB에 만들어 놓은 해당 유저의 권한을 부여한다.
        if (member != null) {
            member.setAuthorities(makeGrantedAuthority(userMapper.readAuthority(username)));
        }
        //Spring이 관리하고 있는 Member객체로 저장된다.
        return new SecurityMember(member);
    }
    //GrantedAuthority란 허가또는 권리 라는 뜻이다.
    //다음 메서드는 허가 또는 권리를 부여하는 메서드이다.
    //다음과 같이 만들어진 허가는 getAuthority()메서드를 사용하여 해당 유저의 엑서스 권할을 부여할지 안할지 여부를 결정한다.
    public static List<GrantedAuthority> makeGrantedAuthority(List<String> roles){
        List<GrantedAuthority> list = new ArrayList<>();

        //java의 forEach란
        //roles라는 String타입의 List의 내용을 하나식 꺼내서
        //-> 이후에 오는 로직에 role이라는 매개변수를 너어 로직을 수행한다.
        roles.forEach(role -> list.add(new SimpleGrantedAuthority(ROLE_PREFIX + role)));
        return list;
    }
}
```

- UserMapper를 `@Autowired`를 한다 로그인한 아이디가 db에 있다면 member객체를 가져오는 역할을 등 db와의 연결을 위해 의존성 주입을 한다.
- `loadUserByUsername` 는 UserDetailsService에서 핵심이라고 생각하는 메서드이다.
  - **위 함수**는 로그인이 될때 실행되는 함수로서 **매개변수**로 받은 로그인 id를 와 db에 있는 username을 비교하여 **username**이 있다면 **mapper**를 통해 가져온다.
  - 해당 **username**이 존재 한다면 db에 존재하는 **authority**테이블에 해당 **user**의 **auth**를 **member**에 설정 해준다.
- `makeGrantedAuthority` 메서드는 매개변수로 들어온 인증들을 **list에** 넣어주는 역할을 한다
- return된 list는 **member**로 들어가고 `loadUserByUsername`은 **SecurityMember**를 리턴해준다



### handler

- 로그인 이후에 처리할 로직을 설정해 주는 파일로 원래 **Spring** **security**에 기본으로 **default** 되어 있지만, 개발자가 직접 핸들러를 설정하기 위해서는 `SavaedRequestAwareAuthenticationSuccessHandler` 를 상속받는 class를 작성 해야 한다.



```java
package com.example.demo.security.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.example.demo.security.model.SecurityMember;

import org.springframework.security.core.Authentication;

import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

public class CustomLoingSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    //로그인 이후에 처리될 로직
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request , HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        ((SecurityMember)authentication.getPrincipal()).setIp(getClientIp(request));

        //세션을 하나 가지고 온다
        HttpSession session = request.getSession();
        //세션이 있다면
        if(session != null){
            //해당 유저의 이전 페이지 url를 가져온다
            String redirecUrl = (String) session.getAttribute("prevPage");
            //이전페이지가 있다면
            if(redirecUrl != null){
                //이전페이지의 세션을 없엔다
                session.removeAttribute("prevPage");
                //로그인이 된 request와 response로 이전 페이지로 redirect 함
                getRedirectStrategy().sendRedirect(request, response, redirecUrl);
            }
            //이전 페이지가 없다면
            else{
                super.onAuthenticationSuccess(request, response, authentication);
            }
        }
        //세션이 없다면
        else{
            super.onAuthenticationSuccess(request, response, authentication);
        } 
    }
}
```

- `SavedRequestAwareAuthenticationSuccessHandler` 의 메서드인 `onAuthentiacionSuccess`는  로그인이 처리된 이후 로직을 작성한 것이다.
- 매개변수로는 **Servelt을** 통한 **request**(요청) , **response**(응답) , 그리고 보안 정보를 저장할 **authentision** 세개를 받는다.
- **request로** 들어온 **session을** **getSession**()을 이용해 가져온다.
- **session이** 있다면 **session** 내부적으로 설정되어 있는 **prevPage라는** 키워드를 **getAttribute를** 통해 가져오면 로그인 하기전의 페이지 **url을** 가져올 수 있다.
- 이전 페이지가 있다면 인증 되어 있지 않은 이전페이지를 **removeAttribute**를 통해 삭제 하고 `getRedirectStrategy().sendRedirect(request, response, redirecUrl);` 를 이전 url을 인증된 페이지로 **redirect한다**

### adapter

- 어뎁터 부분에서 security 부분의 모든 컨트롤을 담당한다.

```java
package com.example.demo.security.adapter;

import com.example.demo.security.handler.CustomLoingSuccessHandler;
import com.example.demo.security.service.CustomUserDetailsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@EnableWebSecurity
public class CustomWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {
    //Service는 login입력을 받아 스프링이 제공하는 User에 login 정보 저장
    @Autowired
    CustomUserDetailsService customUserDetailsService;

    //패스워드를 암호화하는 역할
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    //로그인 성공 이후 default 핸들러
    @Bean
    public AuthenticationSuccessHandler successHandler() {
        return new CustomLoingSuccessHandler("/");
    }
    //ignoring
    //인증정보가 있던 없던 openapi하위 폴더내에 있는 모든 것들은 접속 가능
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
        .antMatchers("/openapi/**");
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //보안 문제 csrf 허용 불가
        http.csrf().disable()
        //요청권한 해주겠다
        .authorizeRequests()
        // /2는 권한 잠겨있고
        .antMatchers("/2").authenticated()
        .antMatchers("/username/*").authenticated()
        // 그 이외는 모두 허용    
        .anyRequest().permitAll()
        //그리고 로그인을 하면
        .and().formLogin()
        //핸들러 작동
        .successHandler(successHandler())
        .and()
        .exceptionHandling().accessDeniedPage("/user/denied");
    }
    //암호화된 비밀번호를 encoding하는 역할
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
    }
}
```

- 어뎁터 기능중 패스워드를 암호화하는 역할인 `BCryptPasswordEncoder()` 객체를 만들어 준다
- 인증이 필요없이 접속할 수 있는 api를 지정하는 메서드를 configure에 설정해준다. 방법은 **ignoring**()에 **antMatchres**(해당 경로)에 경로값을 지정하면 된다.
- 로그인 했을때 로직 처리를 담당하는 **configure에** 보안문제, 요쳥권한, 핸들러 작동 등 보안 관련 로직을 설정할 수 있다.
- 암호화된 비밀번호를 **encoding**하는 **configure**도 필요 하다.