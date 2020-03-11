package com.example.demo.db.repository;

import java.util.List;

import com.example.demo.db.model.LoginInfo;

import org.apache.ibatis.annotations.Mapper;


//sql문을 작성한 xml을 호출하기 위한 interface이다.
//interface를 mapper로 사용하기 위해 IOC에 bean등록을 해 준다.
@Mapper
public interface LoginInfoMapper {
    //return타입은 LoginInfo를 타입으로 가지는 List객체 이다.
    public List<LoginInfo> selectAllLoginInfo() throws Exception;
}