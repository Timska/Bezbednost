package com.onlinebidding.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "items")
public class Item {

	@Id
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
