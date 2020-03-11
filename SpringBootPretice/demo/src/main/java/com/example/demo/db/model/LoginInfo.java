package com.example.demo.db.model;

import org.apache.ibatis.type.Alias;

import lombok.Data;

//lombok을 통해 Getter와 Setter의 코드를 사용하지 않고 구현할 수 있다.
//lombok의 visual source code 설치는 다음과 같다.
//1. Ctrl + Shift + X 를 통해 Extenstions로 이동한다.
//2. Lombok Annoations Support for VS Code 를 설치 한다.
//3. dependence를 통한 라이브러리 설치가 필요하기 때문에 maven이라면 다음과 같은 코드를 pom.xml에 작성해준다.
//         <dependency>
//           <groupId>org.projectlombok</groupId>
//           <artifactId>lombok</artifactId>
//           <version>1.18.12</version>
//           <scope>provided</scope>
//          </dependency>


//@Data를 어노테이션 함으로서 getter, setter를 구현할 수 있다.
@Data
//type-alias-package를 통해 해당 class를 타입으로 사용할때 따로 경로를 지정할 필요 없이 loginInfo를 입력하면 해당 class를 타입으로 사용할 수 있다.
@Alias("loginInfo")
public class LoginInfo {

    //db table에 있는 id와 name을 가져오기 위해서 필드 변수 지정
    private int id;
    private String name;

    //생성자 생성
    public LoginInfo(){}

    public LoginInfo (int id , String name){
        this.id = id;
        this.name = name;
    }
}