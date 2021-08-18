package com.cart.aop;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.cart.core.model.RequestHelper;


@Component
@Aspect
public class RequestInterceptor {

	private static final Logger log = (Logger) LoggerFactory.getLogger(RequestInterceptor.class);

	@Autowired
	private JWTUtil jwtUtil;
	
	@Autowired 
	private RequestHelper reqHelper;
	
	@Around("allOperations()")
	public Object validateToken(ProceedingJoinPoint joinPoint) throws Throwable {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		
		String token = request.getHeader("Authorization");
		token = token.substring(7).trim();
		log.info("Authorization="+token);
		String userName = jwtUtil.getUsernameFromToken(token);
		log.info("username="+userName);
		
		reqHelper.update(userName, token);
		
		Object obj = joinPoint.proceed();
		log.info(joinPoint.getTarget().getClass().getName()+"| "+joinPoint.getSignature().getName());
		return obj;
	}

	@Pointcut("execution(* com.cart.controller.v1.*..*(..))")
	public void allOperations() {
	}
}
