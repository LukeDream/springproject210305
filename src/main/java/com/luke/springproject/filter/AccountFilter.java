package com.luke.springproject.filter;

import com.luke.springproject.entity.Account;
import com.luke.springproject.entity.Permission;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

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

    private final String[] IGNORE_URI = {"/index", "/css/",
            "/js/", "/images/","/account/login","/account/validataAccount"
            ,"/errorPage"};

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

        Account account= (Account) request.getSession().getAttribute("account");
        System.out.println("get session:"+account);
        if(account==null){
            response.sendRedirect("/account/login");
            return;
        }
        if(!hasAuth(account.getPermissionList(),uri)){
            //关闭权限校验
//            request.setAttribute("msg","您无权访问当前页面"+uri);
//            request.getRequestDispatcher("/errorPage").forward(request,response);
            filterChain.doFilter(request,response);
        }else{
            filterChain.doFilter(request,response);
        }


    }

    private boolean hasAuth(List<Permission> permissionList, String uri) {
        for(Permission permission:permissionList){
            if(uri.startsWith(permission.getUri())){
                return true;
            }
        }
        return false;
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
