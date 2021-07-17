package com.runcoding.auth.config;

import com.runcoding.auth.service.AuthUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private AuthUserDetailService authUserDetailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**全局的用户，基于内存*/
	/*@Autowired
	public void globalUserDetails(final AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication()
				.withUser("runcoding").password(passwordEncoder.encode("runcoding")).roles("USER").and()
				.withUser("admin").password(passwordEncoder.encode("admin")).roles("ADMIN");
	}*/

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/login").permitAll()
				.antMatchers("/oauth/token/revokeById/**").permitAll()
				.antMatchers("/tokens/**").permitAll()
				.anyRequest().authenticated()
				.and().formLogin().permitAll()
				.and().csrf().disable();
	}

	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	public UserDetailsService userDetailsService() {
		return authUserDetailService;
	}


}