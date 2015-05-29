package com.onlinebidding.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.onlinebidding.model.User;
import com.onlinebidding.service.UserService;

@Controller
public class HelloWorldController {
	
	String message = "Welcome to Spring MVC!";
	
	@RequestMapping("/hello")
	public ModelAndView showMessage(@RequestParam(value = "name", required = false, defaultValue = "World") String name) {
		System.out.println("In controller");
		ModelAndView mv = new ModelAndView("helloworld");
		mv.addObject("message", message);
		mv.addObject("name", name);
		return mv;
	}
	
	@RequestMapping("/message")
	@ResponseBody
	public String getMessage() {
		return "Hello everyone! How are you? Doing goooood?";
	}
	
	@RequestMapping(value = "/json", method = RequestMethod.GET)
	@ResponseBody
	public User getJSON(){
		User user = new User();
		return user;
	}
	
	////////////////////////////////////////////////////////////////////////////
	@RequestMapping(value = "/sendmessagemap", method = RequestMethod.POST)
	@ResponseBody
	public String sendMessageMap(@RequestBody LinkedMultiValueMap<String, String> map) {
		//Check if username and password exist in database and return result
		return "correct";
	}
	
	@RequestMapping(value = "/sendmessageobject", method = RequestMethod.POST)
	@ResponseBody
	public String sendMessageMap(@RequestBody User u) {
		//Check if username and password exist in database and return result
		
		return u.getFirstName();	
	}
	/////////////////////////////////////////////////////////////////////////////
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "/checkforlogin", method = RequestMethod.POST)
	@ResponseBody
	public String checkForLogin(@RequestBody MultiValueMap<String, String> map) {
		String userName = map.getFirst("userName");
		User user = userService.findUser(userName);
		if (user == null) {
			return "1";
		}
		String password = map.getFirst("password");
		if (!user.getPassword().equals(password)) {
			return "2";
		}
		return "correct";
	}

}







