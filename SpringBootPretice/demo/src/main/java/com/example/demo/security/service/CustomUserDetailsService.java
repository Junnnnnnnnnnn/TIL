package com.example.demo.security.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.db.model.Member;
import com.example.demo.db.repository.UserMapper;
import com.example.demo.security.model.SecurityMember;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
//UserDetailsService 어렵다 공부!!
@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    //Spring Security User를 위한 인증정보를 사용하기 위해서 ROLE_를 인증 문자열 앞에 붙혀 준다.
    private static final String ROLE_PREFIX = "ROLE_";

    //Member의 정보를 DB에서 검색하기 위해서 의존성 주입
    @Autowired
    UserMapper userMapper;

    //로그인이 될때 자동 실행 되는 메서드
    //클라이언트에서 입력한 ID가 DB에 있는가 찾아 본후 있다면 그 Member를 Spring에서 관리하는 Member에 추가해준다.
    //없다면 Exception을 던져 준다.
    @Transactional
    public void Insert_Member(Member member){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        member.setPassword(passwordEncoder.encode(member.getPassword()));
        userMapper.insertMember(member);
    }
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