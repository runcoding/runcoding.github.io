package com.runcoding.sso.config;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * https://zhiwei-feng.github.io/201809142042.html
 */
public class RewriteAccessDenyFilter extends GenericFilterBean {


    /***
     * 登录跳转
     * @param request
     * @param response
     * @param chain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            chain.doFilter(request, response);
        } catch (AccessDeniedException e) {
            if("XMLHttpRequest".equals(((HttpServletRequest)request).getHeader("X-Requested-With"))){
                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/json;charset=utf-8");
                String jsonMessage = "{\"code\":\"302\"}";
                response.getWriter().print(jsonMessage);
            }
            throw  e;
        }
    }
}