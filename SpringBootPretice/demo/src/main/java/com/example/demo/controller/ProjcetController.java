package com.example.demo.controller;

import java.util.List;

import com.example.demo.db.model.LoginInfo;
import com.example.demo.db.model.Member;
import com.example.demo.db.service.DBService;
import com.example.demo.db.service.UserService;
import com.example.demo.security.service.CustomUserDetailsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestParam;

//어노테이션으로 @Controller를 사용해 IOC에 bean등록 해준다.
@Controller
public class ProjcetController {

    // 만들어 놓은 dbSerivce interface를 IOC에 등록되어 있는 bean을 의존성 주입 해준다.
    // @Autowired DBService dbService;

    @Autowired UserService userService;

    @Autowired CustomUserDetailsService detailsService;

    // RESTfull api를 통해  "/" url으로 GET 메서드가 들어왔을때 실행하는 함수이다. 에러가 뜰 시 Exception을 통해
    // 예외 처리를 해준다. return 타입으로 String을 가지고 home을 return해준다. return 된 home은
    // application.properties에서 작성한 prefix 와 suffix를 통해 해당 경로에 있는 home 파일이
    // response된다.
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String requestMethodName()throws Exception {
        return "/home";
    }
    @RequestMapping(value = "/user/upload_picture", method = RequestMethod.GET)
    public String upload_piString()throws Exception {
        return "/user/upload_picture";
    }
    @RequestMapping(value = "/openapi/signUp_page", method = RequestMethod.GET)
    public String signUp_page()throws Exception {
        return "/openapi/signUp_page";
    }
    @RequestMapping(value = "/openapi/predict", method = RequestMethod.GET)
    public String predict_page()throws Exception {
        return "/openapi/predict";
    }
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

    // RESTfull api를 통해  "/2" url으로 GET 메서드가 들어왔을때 실행하는 함수이다. 에러가 뜰 시 Exception을 통해
    // 예외 처리를 해준다.
    // @ReponseBody를 통해 return되는 값을 html body 부분에 바로 출력한다. return 타입으로 만들어 놓은
    // LoginInfo type을 사용했고 db에 저장되어 있는 모든 정보를 가져오는 메서드이기 때문에 List메서드도 함께 사용 해준다
    // return 값으로 deService에 있는 메서드 selectAllLoginInfo()를 실행 시킨다.
    // @RequestMapping(value="/2", method=RequestMethod.GET) public @ResponseBody
    // List<LoginInfo> two() throws Exception {     return
    // dbService.selectAllLoginInfo(); }

    // @RequestMapping(value="/username/{username}", method=RequestMethod.GET)
    // public @ResponseBody Member three(@PathVariable String username) throws
    // Exception {     return userService.readUser(username); }

    // @RequestMapping(value="/authority/{username}", method=RequestMethod.GET)
    // public @ResponseBody List<String> four(@PathVariable String username) throws
    // Exception {     return userService.readAuthority(username); }
    // @RequestMapping(value="/user/denied", method=RequestMethod.GET) public
    // @ResponseBody String five() throws Exception {     return "회원가입 다시혀"; }

}