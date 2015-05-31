package com.onlinebidding.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "auctions")
public class Auction {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long auctionID;
	private String auctionName;
	@ManyToOne
	private User creator;
	@ManyToOne
	private User winner;
	@OneToOne
	private Item item;
	private Date startDate;
	private Date endDate;
	private String currentPrice;

	public Auction() {

	}

	public Auction(String auctionName, User creator, User winner, Item item,
			Date startDate, Date endDate, String currentPrice) {
		this.auctionName = auctionName;
		this.creator = creator;
		this.winner = winner;
		this.item = item;
		this.startDate = startDate;
		this.endDate = endDate;
		this.currentPrice = currentPrice;
	}

	public Long getAuctionID() {
		return auctionID;
	}

	public void setAuctionID(Long auctionID) {
		this.auctionID = auctionID;
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

	public User getWinner() {
		return winner;
	}

	public void setWinner(User winner) {
		this.winner = winner;
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

	public String getCurrentPrice() {
		return currentPrice;
	}

	public void setCurrentPrice(String currentPrice) {
		this.currentPrice = currentPrice;
	}

}
