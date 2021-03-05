package com.luke.springproject.filter;

import com.luke.springproject.entity.Account;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName AccountFilter
 * @Description TODO
 * @Author lulu
 * @Date 2020/11/19 20:46
 * @Version 1.0
 **/
@Component
@WebFilter(urlPatterns = "/*")
public class AccountFilter implements Filter {

    private final String[] IGNORE_URI = {"/index", "/css/", "/js/", "/images/","/account/login","/account/validataAccount"};

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

        System.out.println("---------AccountFilter ");
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String uri = request.getRequestURI();
        boolean pass =canPassIgnore(uri);
        if(pass){
            filterChain.doFilter(request,response);
            return;
        }

        Object account= request.getSession().getAttribute("account");
        System.out.println("get session:"+account);
        if(account==null){
            response.sendRedirect("/account/login");
            return;
        }

        filterChain.doFilter(request,response);

    }

    private boolean canPassIgnore(String uri) {

    for(String val:IGNORE_URI){
        if(uri.startsWith(val)){
            return  true;
        }
    }
    return false;

    }
}
