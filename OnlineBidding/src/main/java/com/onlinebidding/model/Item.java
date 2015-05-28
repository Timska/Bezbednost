package com.onlinebidding.model;

import java.sql.Date;

public class Item {

	private String itemName;
	private Date created;
	private String description;
	
	public Item(String itemName, Date created, String description){
		this.itemName = itemName;
		this.created = created;
		this.description = description;
	}

	public String getItemName() {
		return itemName;
	}

	public Date getCreated() {
		return created;
	}

	public String getDescription() {
		return description;
	}
	
	
}
