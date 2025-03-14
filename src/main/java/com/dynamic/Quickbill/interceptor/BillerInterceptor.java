package com.dynamic.Quickbill.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.dynamic.Quickbill.exceptions.BillerAuthenticationException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
public class BillerInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)throws BillerAuthenticationException {
		HttpSession session=request.getSession(false);
		if(session==null || session.getAttribute("Biller")==null) {
			System.out.println("biller session not found or not authenticated");
			throw new BillerAuthenticationException("You need to log in as an admin to access this resource.");
		}
		return true;
	}
}
