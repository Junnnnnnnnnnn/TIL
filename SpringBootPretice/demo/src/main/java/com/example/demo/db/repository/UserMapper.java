package com.example.demo.db.repository;

import java.util.List;

import com.example.demo.db.model.Member;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    public Member readUser(String username);
    public List<String> readAuthority(String username);
}