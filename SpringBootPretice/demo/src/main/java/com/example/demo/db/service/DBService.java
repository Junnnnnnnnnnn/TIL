package com.example.demo.db.service;

import java.util.List;

import com.example.demo.db.model.LoginInfo;
import com.example.demo.db.repository.LoginInfoMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


//DAO객체로 DB와 직접적으로 데이터를 주고 받는 로직이다.
//@Service를 이용해 IOC에 bean등록을 해준다.
@Service
public class DBService  {

    //mapper를 통한 sql쿼리를 전송하고 데이터를 가져오기 위해 LoginInfoMapper를 IOC를 통해 의존성 주입을 해준다.
    @Autowired
    LoginInfoMapper loginInfoMapper;

    //return 타입으로 select문을 통해 필요로 하는 값을 가져 온다.
    public List<LoginInfo> selectAllLoginInfo() throws Exception {
        return loginInfoMapper.selectAllLoginInfo();
    }
}