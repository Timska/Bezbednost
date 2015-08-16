package onlinebidding.model;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.Date;

public class Auction implements Comparable<Auction>, Serializable {

	private static final long serialVersionUID = -4176502115158816420L;
	
	private Long auctionID;
	private String auctionName;
	private User creator;
	private User winner;
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

	public User getWinner() {
		return winner;
	}

	public void setWinner(User winner) {
		this.winner = winner;
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

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public Date getStartDate() {
		return startDate;
	}
	
	public String convertStartDateToString() {
		return DateFormat.getInstance().format(startDate);
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

	public int compareTo(Auction another) {
		return another.startDate.compareTo(this.startDate);
		// return this.startDate.compareTo(another.startDate);
	}

	@Override
	public String toString() {
		return auctionName + "-" + currentPrice + " "
				+ DateFormat.getInstance().format(startDate);
	}

}




