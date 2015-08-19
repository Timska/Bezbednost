package com.onlinebidding.model;

import java.io.Serializable;
import java.util.Date;


public class Item implements Serializable{

	private static final long serialVersionUID = -6496566490456053772L;

	private Long itemID;
	private String itemName;
	private Date created;
	private String description;

	public Item() {

	}

	public Item(String itemName, Date created, String description) {
		this.itemName = itemName;
		this.created = created;
		this.description = description;
	}
	
	public Long getItemID() {
		return itemID;
	}

	public void setItemID(Long itemID) {
		this.itemID = itemID;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
