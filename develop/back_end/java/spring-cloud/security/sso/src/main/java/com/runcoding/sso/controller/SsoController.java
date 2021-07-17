package com.runcoding.sso.controller;

import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author runcoding
 * @date 2019-03-14
 * @desc:
 */
@Controller
@RequestMapping("/")
public class SsoController {

    @RequestMapping("/dashboard/login")
    public String login(String state) {
        System.out.println("state="+state);
        return "redirect:/#/";
    }

    @RequestMapping("/dashboard/logout")
    public void exit(HttpServletRequest request, HttpServletResponse response) {
        // token can be revoked here if needed
        new SecurityContextLogoutHandler().logout(request, null, null);
        try {
            //sending back to client app
            response.sendRedirect(request.getHeader("referer"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/dashboard/message")
    @ResponseBody
    public Map<String, Object> dashboardMessage() {
        return Collections.singletonMap("message", "Yay!");
    }

    @RequestMapping("/dashboard/user")
    @ResponseBody
    public Principal user(Principal user) {
        return user;
    }


}
