package com.f2.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommonController {
	@RequestMapping("/")
	String home() {
		return "Hello World f2";
	}
}
