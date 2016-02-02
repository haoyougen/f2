package com.f2.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;

import com.f2.exceptions.AuthenticationException;
import com.f2.exceptions.BadParameterException;
import com.f2.exceptions.BusinessException;

@Controller
public class CommonController extends BaseController{
	 
	@Override
	public void authenticate(HttpServletRequest requset, HttpServletResponse response) throws AuthenticationException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dataValidate(HttpServletRequest requset, HttpServletResponse response) throws BadParameterException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postProcess(HttpServletRequest requset, HttpServletResponse response) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String doBusiness(HttpServletRequest requset, HttpServletResponse reponse) throws BusinessException {
		 
		return "hello common service";
	}
}
