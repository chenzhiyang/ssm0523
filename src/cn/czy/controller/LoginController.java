package cn.czy.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/Login")
public class LoginController {
	@RequestMapping("/login")
	public String login(String username,String pwd,HttpServletRequest request) {
		HttpSession session = request.getSession();
		if(!username.isEmpty())
		session.setAttribute("username", username);
		return "redirect:/items/list";
	} 
}
