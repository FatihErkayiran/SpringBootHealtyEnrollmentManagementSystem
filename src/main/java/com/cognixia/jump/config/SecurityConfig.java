package com.cognixia.jump.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	 @Autowired
		UserDetailsService userDetailsService;
		
	// specify the users for authentication
	@Override
	protected void configure(AuthenticationManagerBuilder auth)throws Exception {
		
		// for in memory
//		auth.inMemoryAuthentication()
//			.withUser("user1")
//			.password("123")
//			.roles("USER")
//			.and()
//			.withUser("admin1")
//			.password("123")
//			.roles("ADMIN");
		
		// using users from a database
		auth.userDetailsService(userDetailsService);
		
	}
	//specify which users can access which apis
		protected void configure(HttpSecurity httpSecurity) throws Exception {
			
			httpSecurity.csrf().disable()
			            .authorizeRequests()
			            .antMatchers(HttpMethod.GET,"/api/enrollees").hasRole("ADMIN")
			            .antMatchers(HttpMethod.POST,"api/add/enrollee").hasRole("ADMIN")
			            .antMatchers(HttpMethod.PUT, "api/update/enrollee").hasRole("USER")
			            .antMatchers(HttpMethod.DELETE,"api/delete/enrollee").hasRole("ADMIN")
			            .antMatchers(HttpMethod.GET,"api/enrollee/{id}").hasRole("ADMIN")
			            .antMatchers(HttpMethod.GET,"api/dependents/{id}").hasRole("ADMIN")
			            .antMatchers(HttpMethod.GET,"api/dependents/{id}").hasRole("ADMIN")
			            .antMatchers(HttpMethod.GET,"api/enrollee/{id}/dependents").hasAnyRole("USER","ADMIN")
			            .antMatchers(HttpMethod.POST,"api/enrollee/{id}/add/dependents").hasRole("USER")
			            .antMatchers(HttpMethod.PUT,"api/enrollee/{id}/update/dependents").hasRole("USER")
			            .antMatchers(HttpMethod.DELETE,"api/enrollee/{id}/delete/dependents").hasRole("USER")
			            .antMatchers("/").hasAnyRole("USER","ADMIN")
			            .antMatchers("/api/car/{id}").permitAll()
			            .and().formLogin()
			            .and().httpBasic();
			
		}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		
		return NoOpPasswordEncoder.getInstance();
	}
	
}
