package com.capg.expensetracker.exceptionaspect;

import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ExpenseTrackerException {
	
Logger logger=LoggerFactory.getLogger(ExpenseTrackerException.class);
	
	@AfterThrowing(pointcut  = "execution(* com.capg.expensetracker.service.ExpenseTrackerService.*(..))", throwing = "ex")
	public String afterThrowingExceptionAdvice( Exception ex)
	{
		logger.error(ex.getMessage());
		
		return ""+ex.getMessage();
		
	}
	
	@AfterThrowing(pointcut  = "execution(* com.capg.expensetracker.controller.ExpenseTrackerController.*(..))", throwing = "ex1")
	public String afterThrowingExceptionAdviceController( Exception ex1)
	{
		logger.error(ex1.getMessage());
		
		return ""+ex1.getMessage();
		
	}

}
