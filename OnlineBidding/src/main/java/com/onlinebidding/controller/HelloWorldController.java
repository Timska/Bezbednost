package com.onlinebidding.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.android.gcm.server.Sender;
import com.onlinebidding.model.Administrator;
import com.onlinebidding.model.Auction;
import com.onlinebidding.model.Item;
import com.onlinebidding.model.User;
import com.onlinebidding.model.UserAuction;
import com.onlinebidding.service.AdministratorService;
import com.onlinebidding.service.AuctionService;
import com.onlinebidding.service.ItemService;
import com.onlinebidding.service.UserAuctionService;
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
	
	@RequestMapping(value="/message", method = RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
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
	
	@Autowired
	private ItemService itemService;
	
	@Autowired 
	private UserAuctionService userAuctionService;
	
	@Autowired
	private AdministratorService administratorService;
	
	private static final String unexistingUser = "1";
	private static final String wrongPassword = "2";
	private static final String alreadyRegistered = "3";
	
	@RequestMapping(value = "/getallusers", method = RequestMethod.GET)
	@ResponseBody
	public List<User> getAllUsers() {
		return userService.getAllUsers();
	}
	
	@RequestMapping(value = "/updateuser", method = RequestMethod.POST)
	@ResponseBody
	public String updateUser(@RequestBody User user) {
		userService.create(user);
		return "correct";
	}
	
	@RequestMapping(value = "/deleteuser", method = RequestMethod.POST)
	@ResponseBody
	public String deleteUser(@RequestBody User user) {
		userService.delete(user);
		return "correct";
	}
	
	@RequestMapping(value = "/checkforlogin", method = RequestMethod.POST)
	@ResponseBody
	public String checkForLogin(@RequestBody MultiValueMap<String, String> map) {
		String userName = map.getFirst("userName");
		User user = userService.findUser(userName);
		if (user == null) {
			return unexistingUser;
		}
		String password = String.valueOf(map.getFirst("password").hashCode());
		if (!user.getPassword().equals(password)) {
			return wrongPassword;
		}
		return "correct";
	}
	
	@RequestMapping(value = "/checkloginadministrator", method = RequestMethod.POST)
	@ResponseBody
	public String checkForAdministrator(@RequestBody MultiValueMap<String, String> map) {
		String adminName = map.getFirst("userName");
		System.out.println(adminName);
		Administrator admin = administratorService.findAdministrator(adminName);
		System.out.println("vleze vo check login");
		if (admin == null) {
			return unexistingUser;
		}
		String password = map.getFirst("password").toString();
		if (!admin.getPassword().equals(password)) {
			return wrongPassword;
		}
		return "correct";
	}
	
	@RequestMapping(value = "/loginadministrator", method = RequestMethod.GET)
	public ModelAndView loginadministrator(){
		System.out.println("vleze vo login");
		ModelAndView view = new ModelAndView("login");
		return view;
	}
	
	@RequestMapping(value = "/administrator", method = RequestMethod.GET)
	@Transactional
	public ModelAndView auctions(){
		ModelAndView mv = new ModelAndView("administrator");
		List<Auction> list = auctionService.getAllAuctions();
		mv.addObject(list);
		return mv;
	}

	@RequestMapping(value = "/getuser", method = RequestMethod.POST)
	@ResponseBody
	public User getUser(@RequestBody String username) {
		User user = userService.findUser(username);
		System.out.println(user.getUserName());
		return user;
	}
	
	@RequestMapping(value = "/getadministrator", method = RequestMethod.POST)
	@ResponseBody
	public Administrator getAdministrator(@RequestBody String username) {
		return administratorService.findAdministrator(username);
	}
	
	@RequestMapping(value = "/registeruser", method = RequestMethod.POST)
	@ResponseBody
	public String registerUser(@RequestBody User user) {
		if (userService.findUser(user.getUserName()) != null) {
			return alreadyRegistered;
		}
		String password = user.getPassword();
		user.setPassword(String.valueOf(password.hashCode()));
		userService.create(user);
		return "correct";
	}
	
	@RequestMapping(value = "/getallauctions", method = RequestMethod.GET)
	@ResponseBody
	public List<Auction> getAllAuctions() {
		return auctionService.getAllAuctions();
	}
	
	@RequestMapping(value = "/notfinishedauctions", method = RequestMethod.GET)
	@ResponseBody
	public List<Auction> getNotFinishedAuctions() {
		return auctionService.getNotFinishedAuctions();
	}
	
	@RequestMapping(value = "/userauctions", method = RequestMethod.POST)
	@ResponseBody
	public List<Auction> getUserAuctions(@RequestBody String userName) {
		return auctionService.getUserAuctions(userName);
	}
	
	@RequestMapping(value = "/usernotfinishedauctions", method = RequestMethod.POST)
	@ResponseBody
	public List<Auction> getUserNotFinishedAuctions(@RequestBody String userName) {
		return auctionService.getUserNotFinishedAuctions(userName);
	}
	
	@RequestMapping(value = "/wonuserauctions", method = RequestMethod.POST)
	@ResponseBody
	public List<Auction> getWonUserAuctions(@RequestBody String userName) {
		return auctionService.getWonUserAuctions(userName);
	}

	@RequestMapping(value = "/updateauctionprice", method = RequestMethod.POST)
	@ResponseBody
	public Auction updateAuctionPrice(@RequestBody MultiValueMap<String, String> map) {
		Long auctionID = Long.parseLong(map.getFirst("auctionID").toString());
		String userName = map.getFirst("userName").toString();
		String price = map.getFirst("auctionPrice").toString();
		return auctionService.updateAuction(auctionID, userService.findUser(userName), price);
	}
	///////////////////////////////////////Changed from here////////////////////////////////////////////
	@RequestMapping(value = "/userenteredauctions", method = RequestMethod.POST)
	@ResponseBody
	public List<Auction> getUserEnteredAuctions(@RequestBody String userName) {
		return userAuctionService.getUserEnteredAuctions(userName);
	}
	
	@RequestMapping(value = "/auctionenteredusers", method = RequestMethod.POST)
	@ResponseBody
	public List<User> getAuctionEnteredUsers(@RequestBody Long auctionID) {
		return userAuctionService.getAuctionEnteredUsers(auctionID);
	}
	
	@RequestMapping(value = "/hasuserentered", method = RequestMethod.POST)
	@ResponseBody
	public Boolean hasUserEntered(@RequestBody MultiValueMap<String, String> map) {
		String userName = map.getFirst("userName").toString();
		Long auctionID = Long.valueOf(map.getFirst("auctionID").toString());
		UserAuction userAuction = userAuctionService.findUserAuctionByUserAndAuction(userName, auctionID);
		if (userAuction == null) {
			return false;
		}
		return true;
	}
	
	@RequestMapping(value = "/getauction", method = RequestMethod.POST)
	@ResponseBody
	public Auction getAuctionByID(@RequestBody Long auctionID) {
		return auctionService.findAuction(auctionID);
	}
	
	@RequestMapping(value = "/deleteauction", method = RequestMethod.POST)
	@ResponseBody
	public String deleteAuction(@RequestBody Long auctionID){
		System.out.println("vo delete method");
		auctionService.deleteAuction(auctionID);
		return "success";
	}
	
	@RequestMapping(value = "/enterauction", method = RequestMethod.POST)
	@ResponseBody
	public String enterAuction(@RequestBody UserAuction userAuction) {
		userAuctionService.create(userAuction);
		return "correct";
	}
	
	@RequestMapping(value = "/exitauction", method = RequestMethod.POST)
	@ResponseBody
	public String exitAuction(@RequestBody MultiValueMap<String, String> map) {
		String userName = map.getFirst("userName").toString();
		Long auctionID = Long.valueOf(map.getFirst("auctionID").toString());
		userAuctionService.delete(userName, auctionID);
		return "correct";
	}
	
	@RequestMapping(value = "/addauction", method = RequestMethod.POST)
	@ResponseBody
	public String addAuction(@RequestBody Auction auction) {
		Item item = auction.getItem();
		itemService.create(item);
		auctionService.create(auction);
		return "correct";
	}

	public static void send(String msg) {
		Sender sender = new Sender("");
	}
	
}







