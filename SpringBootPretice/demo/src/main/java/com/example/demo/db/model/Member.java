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

    public Member(String username , String password , String name){
        this.username = username;
        this.password = password;
        this.name = name;
        this.isAccountNonExpired = true;
        this.isAccountNonLocked = true;
        this.isCredentialsNonExpired = true;
        this.isEnabled = true;
        this.authorities = null;
    }
}