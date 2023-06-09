package com.pro.www.filter;

import com.alibaba.fastjson.JSON;
import com.pro.www.common.BaseContext;
import com.pro.www.dto.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// 检查用户登录
@Slf4j
@WebFilter(filterName = "loginCheckFilter",urlPatterns = {"/backend/**","/front/**","/employee/**","/user/**"})
public class LoginCheckFilter implements Filter {
    public static final AntPathMatcher PATH_MATCHER= new AntPathMatcher();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request=(HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String requestURL = request.getRequestURI();
        log.info("[INFO] 拦截到请求: {}",request.getRequestURI());
        String[] urls = new String[]{
                "/employee/login",
                "/employee/logout",
                "/backend/**",
                "/front/**",
                "/user/sendMsg",
                "/user/login"



        };
        boolean check = check(urls,requestURL);

        if(check){
            log.info("[INFO] 请求 {} 不需要处理",requestURL);


            filterChain.doFilter(request,response);
            return;
        }
        if(request.getSession().getAttribute("employee")!=null){
            log.info("[INFO] 用户已登录,ID:{}",request.getSession().getAttribute("employee"));
            Long employeeid = (Long) request.getSession().getAttribute("employee");
            BaseContext.setCurrentId(employeeid);

            filterChain.doFilter(request,response);
            return;
        }
        if(request.getSession().getAttribute("user")!=null){
            log.info("[INFO] 用户已登录,ID:{}",request.getSession().getAttribute("user"));
            Long userid = (Long) request.getSession().getAttribute("user");
            BaseContext.setCurrentId(userid);

            filterChain.doFilter(request,response);
            return;
        }

        log.error("[ERROR] 用户未登录");
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));


        return;

    }
    public boolean check(String[] URL,String requestURL){
        for(String i:URL){
            boolean match = PATH_MATCHER.match(i,requestURL);
            if(match){
                return true;
            }
        }
        return false;


    }
}
