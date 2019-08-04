package com.lwy.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PasswordEncoderInteceptor implements HandlerInterceptor {
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String password = request.getParameter("password");
        if (password!=null&&password.length()>0){
            password = bCryptPasswordEncoder.encode(password);
        }
        return true;
    }
}
