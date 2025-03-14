package com.dynamic.Quickbill.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.dynamic.Quickbill.interceptor.BillerInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {
	
	@Autowired
	private BillerInterceptor interceptor;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(interceptor)
				.addPathPatterns("/api/**")
				.excludePathPatterns("/api//auth/{email}/{password}");
	}
}
