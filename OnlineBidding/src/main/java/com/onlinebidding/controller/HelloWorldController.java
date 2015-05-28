package com.onlinebidding.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.onlinebidding.model.User;

@Controller
public class HelloWorldController {
	
	static final String DATABASE_URL = "jdbc:mysql://localhost:3306/onlinebidding";
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
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		
		try{
			connection = DriverManager.getConnection(DATABASE_URL, "root", "MySQLServerPass");
			statement = connection.createStatement();
			String query = "SELECT * FROM user WHERE userid = 1";
			resultSet = statement.executeQuery(query);
			if(resultSet.next()){
				System.out.println(resultSet.getString("Name"));
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return "Hello everyone! How are you? Doing goooood?";
	}
	
	@RequestMapping(value = "/json", method = RequestMethod.GET)
	@ResponseBody
	public User getJSON(){
		User user = new User();
		return user;
	}
	
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
		
		return ((User)u).getFirstName();
		
	}
}
