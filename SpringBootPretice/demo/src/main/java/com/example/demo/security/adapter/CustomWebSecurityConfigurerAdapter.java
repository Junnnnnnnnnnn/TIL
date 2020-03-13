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
    //다음 Service는 login입력을 받아 스프링이 제공하는 User에 login 정보 저장
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

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
    }
}