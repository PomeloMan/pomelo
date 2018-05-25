/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pomeloman.springboot.core.system.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import pomeloman.springboot.core.system.persistence.repo.UserRepository;

/**
 *
 * @author gino
 */
@Controller
public class SimpleController {

	@Value("${spring.application.name}")
	String appName;

	@Autowired
	UserRepository repo;

	@ResponseBody
	@PostMapping("/upload")
	public String upload(/*@RequestParam("file")*/ MultipartFile[] file, HttpServletRequest req) {
//		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) req;
		System.out.println(file.length);
		return "{data:{success:true}}";
	}
}
