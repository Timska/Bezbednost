package com.onlinebidding.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "auctions")
public class Auction {

	@Id
	private String auctionName;
	@ManyToOne
	private User creator;
	@ManyToMany(mappedBy = "enteredAuctions")
	private List<User> entrants;
	@OneToOne
	private Item item;
	private Date startDate;
	private Date endDate;

	public Auction() {

	}

	public Auction(String auctionName, User creator, List<User> entrants,
			Item item, Date startDate, Date endDate) {
		this.auctionName = auctionName;
		this.creator = creator;
		this.entrants = entrants;
		this.item = item;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public String getAuctionName() {
		return auctionName;
	}

	public void setAuctionName(String auctionName) {
		this.auctionName = auctionName;
	}

	public User getCreator() {
		return creator;
	}

	public void setCreator(User creator) {
		this.creator = creator;
	}

	public List<User> getEntrants() {
		return entrants;
	}

	public void setEntrants(List<User> entrants) {
		this.entrants = entrants;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

}
