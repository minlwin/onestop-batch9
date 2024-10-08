package com.jdc.balance.exceptions;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.BindingResult;

@Aspect
@Configuration
public class ValidationResultAspect {
	
	@Pointcut("within(com.jdc.balance.api.*)")
	public void apiClasses() {}

	@Before(value = "apiClasses() and args(..,result)", argNames = "result")
	public void handle(BindingResult result) {
		if(result.hasErrors()) {
			throw new ApiValidationException(result);
		}
	}
}
