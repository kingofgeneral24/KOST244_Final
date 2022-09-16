package com.kosta.finalproject.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

	//아주 기본, 그리고 구현하지않음 단순 암호화 모듈을위한 기본설정만
	@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    	http
    	.cors().disable()  //cors방지
    	.csrf().disable()  //csrf방지
    	.formLogin().disable() //기본 로그인 페이지 없애기
    	.headers().frameOptions().disable(); 
    	return http.build();
    }
	
	//암호화 모듈을 사용하기 위함.
    @Bean
    public PasswordEncoder passwordEncoder() {
    	return new BCryptPasswordEncoder();
    }
    
}
