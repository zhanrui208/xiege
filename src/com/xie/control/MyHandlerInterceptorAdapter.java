package com.xie.control;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class MyHandlerInterceptorAdapter extends  HandlerInterceptorAdapter {
	@Override  
    public   boolean  preHandle(HttpServletRequest req, HttpServletResponse resp, Object handler) {  
		 System.out.println("111");
        String className  =  handler.getClass().getName(); // package Name .ClassName  
        System.out.println(className);
        return true ;  
  }  
}
