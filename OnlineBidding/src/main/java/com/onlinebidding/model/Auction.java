package com.onlinebidding.model;

import java.util.ArrayList;
import java.util.List;

public class Auction {

	private User creator;
	private List<User> entrants;
	private Item item;
	
	public Auction(User creator, Item item){
		this.creator = creator;
		this.item = item;
		this.entrants = new ArrayList<User>();
	}

	public User getCreator() {
		return creator;
	}

	public List<User> getEntrants() {
		return entrants;
	}

	public Item getItem() {
		return item;
	}
	
	public void addEntrant(User u){
		entrants.add(u);
	}
	
	public int getNumberOfParticipants(){
		return entrants.size();
	}
}
