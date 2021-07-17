package com.runcoding.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import javax.sql.DataSource;
import java.util.Arrays;

/**
 * @author runcoding
 * 开启授权服务器
 */
@Configuration
@EnableAuthorizationServer
public  class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

	@Autowired
	private Environment env;

	@Value("classpath:sql/schema.sql")
	private Resource schemaScript;

	@Value("classpath:sql/data.sql")
	private Resource dataScript;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private TokenStore tokenStore;

	@Autowired
	private JwtAccessTokenConverter jwtAccessTokenConverter;


	@Bean
	public DataSource dataSource() {
		final DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(env.getProperty("jdbc.driverClassName"));
		dataSource.setUrl(env.getProperty("jdbc.url"));
		dataSource.setUsername(env.getProperty("jdbc.user"));
		dataSource.setPassword(env.getProperty("jdbc.pass"));
		return dataSource;
	}

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
         clients.jdbc(dataSource()).passwordEncoder(passwordEncoder());
	}


	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints)
			throws Exception {
		final TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
		tokenEnhancerChain.setTokenEnhancers(Arrays.asList(tokenEnhancer(), jwtAccessTokenConverter));
		//tokenEnhancerChain.setTokenEnhancers(Arrays.asList(tokenEnhancer()));
		endpoints.tokenStore(tokenStore).tokenEnhancer(tokenEnhancerChain).authenticationManager(authenticationManager);
	}

    @Bean
	public DefaultTokenServices tokenServices(TokenStore tokenStore) {
		final DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
		defaultTokenServices.setTokenStore(tokenStore);
		defaultTokenServices.setSupportRefreshToken(true);
		return defaultTokenServices;
	}

	@Override
	public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
		oauthServer.realm("oauth2-service");
		oauthServer.tokenKeyAccess("permitAll()");
		oauthServer.checkTokenAccess("isAuthenticated()");
		oauthServer.allowFormAuthenticationForClients();
	}

	@Bean
	public DataSourceInitializer dataSourceInitializer(final DataSource dataSource) {
		final DataSourceInitializer initializer = new DataSourceInitializer();
		initializer.setDataSource(dataSource);
		ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
		populator.addScript(schemaScript);
		populator.addScript(dataScript);
		initializer.setDatabasePopulator(populator);
		return initializer;
	}

	@Bean
	public TokenEnhancer tokenEnhancer() {
		return new CustomTokenEnhancer();
	}


	/***加密方式*/
	@Bean
	public PasswordEncoder passwordEncoder() {
		//return new BCryptPasswordEncoder();
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

}