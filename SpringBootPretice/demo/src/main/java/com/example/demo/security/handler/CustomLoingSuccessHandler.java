package com.example.demo.security.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.example.demo.security.model.SecurityMember;

import org.springframework.security.core.Authentication;

import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

public class CustomLoingSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    
    public CustomLoingSuccessHandler(String defaultTargetUrl) {

        setDefaultTargetUrl(defaultTargetUrl);
    }

    //로그인 이후에 처리될 로직
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request , HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        //((SecurityMember)authentication.getPrincipal()).setIp(getClientIp(request));

        //세션을 하나 가지고 온다
        HttpSession session = request.getSession();
        //세션이 있다면
        if(session != null){
            //해당 유저의 이전 페이지 url를 가져온다
            String redirecUrl = (String) session.getAttribute("prevPage");
            //이전페이지가 있다면
            if(redirecUrl != null){
                //이전페이지의 세션을 없엔다
                session.removeAttribute("prevPage");
                //로그인이 된 request와 response로 이전 페이지로 redirect 함
                getRedirectStrategy().sendRedirect(request, response, redirecUrl);
            }
            //이전 페이지가 없다면
            else{
                super.onAuthenticationSuccess(request, response, authentication);
            }
        }
        //세션이 없다면
        else{
            super.onAuthenticationSuccess(request, response, authentication);
        } 
    }

    // public static String getClientIp(HttpServletRequest request){
    //     String ip = request.getHeader("X-Forwarded-For");
    //     if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
    //         ip = request.getHeader("Proxy-Client-IP");
    //     }
    //     if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
    //         ip = request.getHeader("WL-Proxy-Client-IP");
    //     }
    //     if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
    //         ip = request.getHeader("HTTP_CLIENT_IP");
    //     }
    //     if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
    //         ip = request.getHeader("HTTP_X_FORWARDED_FOR");
    //     }
    //     if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
    //         ip = request.getRemoteAddr();
    //     }
    //     return ip;
    // }
}