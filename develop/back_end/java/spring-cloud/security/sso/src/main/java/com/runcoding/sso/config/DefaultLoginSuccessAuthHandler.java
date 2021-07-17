package com.runcoding.sso.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author runcoding
 * @date 2019-03-29
 * @desc: 登录成功自定义处理类
 */
public class DefaultLoginSuccessAuthHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Override
    protected void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String targetUrl = determineTargetUrl(request, response);

        if (response.isCommitted()) {
            logger.debug("Response has already been committed. Unable to redirect to "
                    + targetUrl);
            return;
        }
        String[] parameterValues = request.getParameterValues("state");
        System.out.println("parameterValues="+parameterValues);
        /**登录成功跳转*/
       // targetUrl = "https://runcoding.github.io/";
        super.getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }
}
