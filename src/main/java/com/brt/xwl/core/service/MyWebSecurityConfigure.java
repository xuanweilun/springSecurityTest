package com.brt.xwl.core.service;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

@Configuration
@EnableWebSecurity
public class MyWebSecurityConfigure extends WebSecurityConfigurerAdapter{

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().passwordEncoder(NoOpPasswordEncoder.getInstance())
			.withUser("root").password("1234").roles("admin")
			.and().withUser("xuan").password("1234").roles("user");
		super.configure(auth);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
//		  http.csrf().disable()
//           	.authorizeRequests()
//			.antMatchers("/login").permitAll()
//			.antMatchers("/hello").hasRole("admin")
//			.antMatchers("/hi").hasRole("user")
//			.anyRequest().authenticated()
//			.and().httpBasic();
		super.configure(http);
	}
}
