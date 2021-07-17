package com.runcoding.auth.service;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.savedrequest.DefaultSavedRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * @author runcoding
 * @date 2019-03-22
 * @desc: 用户授权账号
 */
@Service
public class AuthUserDetailService implements UserDetailsService {

    private static final String DEFAULT_SAVED_REQUEST_ATTR = "SPRING_SECURITY_SAVED_REQUEST";

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();
        HttpSession session = request.getSession();

        DefaultSavedRequest attribute = (DefaultSavedRequest) session.getAttribute(DEFAULT_SAVED_REQUEST_ATTR);
        if(attribute != null){
            String[] clientIds = attribute.getParameterValues("client_id");
            String   clientId  = clientIds == null ? null :  clientIds[0];
            System.out.println("clientId="+clientId);
        }
        System.out.println("attribute="+JSON.toJSONString(attribute));
        List<GrantedAuthority> authList = new ArrayList<>();
        authList.add(new SimpleGrantedAuthority("ROLE_USER"));
        authList.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        String runcoding = passwordEncoder.encode("runcoding");
        User user = new User("runcoding", runcoding,
                true, true, true, true, authList);
        return user;
    }

}
