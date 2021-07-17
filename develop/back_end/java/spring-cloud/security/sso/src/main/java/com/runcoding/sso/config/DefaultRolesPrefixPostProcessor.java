package com.runcoding.sso.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.PriorityOrdered;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.Filter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 */
@Component
public  class DefaultRolesPrefixPostProcessor implements BeanPostProcessor, PriorityOrdered {

        
        @Override
        public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
            return bean;
        }

        @Override
        public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
            if (bean instanceof FilterChainProxy) {

                FilterChainProxy chains = (FilterChainProxy) bean;

                for (SecurityFilterChain chain : chains.getFilterChains()) {
                    for (Filter filter : chain.getFilters()) {
                        if (filter instanceof OAuth2ClientAuthenticationProcessingFilter) {
                            OAuth2ClientAuthenticationProcessingFilter oAuth2ClientAuthenticationProcessingFilter = (OAuth2ClientAuthenticationProcessingFilter) filter;
                            oAuth2ClientAuthenticationProcessingFilter.setAuthenticationSuccessHandler(new DefaultLoginSuccessAuthHandler());
                        }
                    }
                }
            }
            return bean;
        }

       
        @Override
        public int getOrder() {
            return 0;
        }
    }