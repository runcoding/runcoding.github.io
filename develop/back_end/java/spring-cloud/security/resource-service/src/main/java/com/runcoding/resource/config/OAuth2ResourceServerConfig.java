package com.runcoding.resource.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@EnableResourceServer
public class OAuth2ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Autowired
    private CustomAccessTokenConverter customAccessTokenConverter;


    @Override
    public void configure(final HttpSecurity http) throws Exception {
        // @formatter:off
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
		    .and()
		    .authorizeRequests().anyRequest().permitAll();
	// @formatter:on		
    }

    /*@Primary
    @Bean
    public RemoteTokenServices tokenServices() {
        final RemoteTokenServices tokenService = new RemoteTokenServices();
        tokenService.setCheckTokenEndpointUrl("http://localhost:8080/oauth/check_token");
        tokenService.setClientSecret("secret");
        tokenService.setClientId("fooClientIdPassword");
        return tokenService;
    }
*/

   private String publicKey = "-----BEGIN PUBLIC KEY-----\n" +
           "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAkNcERjHvpqxXCAoI2HEN\n" +
           "rZCbKeaEcE7KWiCx9LXAMHbOQra8gg3Cx5UZqkKSIWTLOy0yLf57LI6QoVWlC2UA\n" +
           "hOihUKJQ/Alj2MUyPutaHxY9nDeeZ6CN5xKf1oYDsyMuhr2wWYq0k8XMjLjYrAb4\n" +
           "e3jTnrpl/gsyAiOEZnGufNEx2nVuJX4EB15mI1tgW2fNMbTs2MtAsen05qaR99rh\n" +
           "Cxsc7ohnKKZ5P78lVz45g+ULhFJHAahrCV0tjIMFoyb+j3QqW9Tal6ZwOTome0UL\n" +
           "xhxhn6aeUU2Ws8x76v/BTdZKZJUIAfrwwxPjQFWWbfx4aM/DLnK7h0mf2ydKK7Dq\n" +
           "gwIDAQAB\n" +
           "-----END PUBLIC KEY-----";

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setAccessTokenConverter(customAccessTokenConverter);
       // converter.setSigningKey("secret");
        converter.setVerifierKey(publicKey);
        return converter;
    }

    @Bean
    public TokenStore tokenStore() {
        // 使用JdbcTokenStore把token存储到数据库中，RedisTokenStore的使用方法也类似
        //return new JdbcTokenStore(dataSource());
        return new JwtTokenStore(accessTokenConverter());
    }

    @Primary
    @Bean
    public RemoteTokenServices tokenServices() {
        final RemoteTokenServices tokenService = new RemoteTokenServices();
        tokenService.setCheckTokenEndpointUrl("http://localhost:8080/oauth/check_token");
        tokenService.setClientSecret("secret");
        tokenService.setClientId("sampleClientId");
        tokenService.setAccessTokenConverter(customAccessTokenConverter);
        return tokenService;
    }

}
