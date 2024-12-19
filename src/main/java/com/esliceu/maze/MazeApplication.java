package com.esliceu.maze;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

import com.esliceu.maze.interceptors.NotLoginInterceptor;
import com.esliceu.maze.interceptors.OngoingGameInterceptor;
import com.esliceu.maze.interceptors.LoginInterceptor;

@SpringBootApplication
public class MazeApplication implements WebMvcConfigurer{

	public static void main(String[] args) {
		SpringApplication.run(MazeApplication.class, args);
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new NotLoginInterceptor()).excludePathPatterns("/login", "/register");
		registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/login", "/register");
		registry.addInterceptor(new OngoingGameInterceptor()).addPathPatterns("/open", "/getcoin", "/getkey", "/nav");
	}

}
