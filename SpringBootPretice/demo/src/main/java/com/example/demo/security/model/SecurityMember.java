package com.example.demo.security.model;

import com.example.demo.db.model.Member;
import org.springframework.security.core.userdetails.User;

import lombok.Data;

@Data
public class SecurityMember extends User {

    private static final long serialVersionUID = 1L;

    private String ip;

    public SecurityMember(Member member){
        super(member.getUsername() , member.getPassword(), member.getAuthorities());
    }
}