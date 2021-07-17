package com.runcoding.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import java.security.KeyPair;

/**
 * @author runcoding
 * @date 2019-03-21
 * @desc: JwtTokenConfig
 */
@Configuration
public class JwtTokenConfig {

    @Bean
    public TokenStore tokenStore() {
        /**使用JdbcTokenStore把token存储到数据库中，RedisTokenStore的使用方法也类似*/
        //return new JdbcTokenStore(dataSource());
        /**将token保存在jwt中*/
        return new JwtTokenStore(accessTokenConverter());
    }


    /**
     * 将token保存在jwt中
     * */
    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
       /** 非对称加密*/
        KeyPair keyPair = new KeyStoreKeyFactory(
                new ClassPathResource("keystore/jwtkey.p12"), "running".toCharArray())
                .getKeyPair("jwtkey");
        converter.setKeyPair(keyPair);

        /**使用对称加密*/
//        converter.setSigningKey("secret");
        return converter;
    }


}
