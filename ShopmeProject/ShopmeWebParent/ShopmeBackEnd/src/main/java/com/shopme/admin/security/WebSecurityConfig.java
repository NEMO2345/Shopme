package com.shopme.admin.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;



@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
	
	@Bean
	public UserDetailsService userDetailsService() {
		return new ShopmeUserDetailService();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
	
		return new BCryptPasswordEncoder();
	}
	
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService());
		authProvider.setPasswordEncoder(passwordEncoder());
		return authProvider;
	}
	@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
		 authenticationManagerBuilder.authenticationProvider(authenticationProvider());
		
        http
            .authorizeHttpRequests(authorize -> authorize
            	.requestMatchers("/users/**").hasAuthority("Admin")
            	.requestMatchers("/categories/**").hasAnyAuthority("Admin","Editor")
            	.requestMatchers("/brands/**").hasAnyAuthority("Admin","Editor")
            	.requestMatchers("/products/**").hasAnyAuthority("Admin","Salespersion","Editor","Shipper")
            	.requestMatchers("/customers/**").hasAnyAuthority("Admin","Salespersion")
            	.requestMatchers("/shipping/**").hasAnyAuthority("Admin","Salespersion")
            	.requestMatchers("/orders/**").hasAnyAuthority("Admin","Salespersion","Shipper")
            	.requestMatchers("/reports/**").hasAnyAuthority("Admin","Salespersion")
            	.requestMatchers("/articles/**").hasAnyAuthority("Admin","Editor")
            	.requestMatchers("/menus/**").hasAnyAuthority("Admin","Editor")
            	.requestMatchers("/settings/**").hasAnyAuthority("Admin")
                .anyRequest().authenticated()
            )
            .formLogin(formLogin -> formLogin
                .loginPage("/login")
                .usernameParameter("email")
                .permitAll()
               
            )
            .logout(logout -> logout
            		.permitAll() 
            )
            .rememberMe(remember-> remember
            		.key("Adjhbsdbs_2312342y378")
            		.tokenValiditySeconds(7*24*60*60)
            )
            ;
      

        return http.build();
    }
	
	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		return (web) -> web.ignoring().requestMatchers("/images/**","/js/**","/webjars/**");
	}

}
