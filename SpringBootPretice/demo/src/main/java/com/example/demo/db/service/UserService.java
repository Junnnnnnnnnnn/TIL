package com.example.demo.db.service;

import java.util.List;

import com.example.demo.db.model.Member;
import com.example.demo.db.repository.UserMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserMapper userMapper;

    public Member readUser(String username) {
        return userMapper.readUser(username);    
    }

    public List<String> readAuthority(String username) {
        return userMapper.readAuthority(username);    
    }
}