package com.onlinebidding.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.onlinebidding.model.Auction;
import com.onlinebidding.model.User;
import com.onlinebidding.service.AuctionService;
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
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AuctionService auctionService;
	
	private static final String unexistingUser = "1";
	private static final String wrongPassword = "2";
	private static final String alreadyRegistered = "3";
	
	@RequestMapping(value = "/checkforlogin", method = RequestMethod.POST)
	@ResponseBody
	public String checkForLogin(@RequestBody MultiValueMap<String, String> map) {
		String userName = map.getFirst("userName");
		User user = userService.findUser(userName);
		if (user == null) {
			return unexistingUser;
		}
		String password = map.getFirst("password");
		if (!user.getPassword().equals(password)) {
			return wrongPassword;
		}
		return "correct";
	}

	@RequestMapping(value = "/getuser", method = RequestMethod.POST)
	@ResponseBody
	public User getUser(@RequestBody String username) {
		User u = userService.findUser(username);
		return u;
	}
	
	@RequestMapping(value = "/registeruser", method = RequestMethod.POST)
	@ResponseBody
	public String registerUser(@RequestBody User user) {
		if (userService.findUser(user.getUserName()) != null) {
			return alreadyRegistered;
		}
		userService.create(user);
		return "correct";
	}
	
	@RequestMapping(value = "/getallauctions", method = RequestMethod.GET)
	@ResponseBody
	public List<Auction> getAllAuctions() {
		return auctionService.getAllAuctions();
	}
	
	
}







