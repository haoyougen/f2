package com.f2.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RestController;

import com.f2.exceptions.AuthenticationException;
import com.f2.exceptions.BadParameterException;
import com.f2.exceptions.BusinessException;

 
public abstract class BaseController {
	public abstract void authenticate(HttpServletRequest requset, HttpServletResponse response) throws AuthenticationException;

	public abstract void dataValidate(HttpServletRequest requset, HttpServletResponse response) throws BadParameterException;

	public abstract void postProcess(HttpServletRequest requset, HttpServletResponse response);

	public String execute(HttpServletRequest requset, HttpServletResponse response) throws AuthenticationException, BadParameterException, BusinessException {
		authenticate(requset, response);
		dataValidate(requset, response);
		String result= doBusiness(requset,response);
		postProcess(requset,response);
		return result;
        
	}

	public abstract String doBusiness(HttpServletRequest requset, HttpServletResponse reponse)throws BusinessException;
}
