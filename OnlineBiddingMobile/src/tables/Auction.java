package tables;

import java.util.Date;
import java.util.List;


public class Auction {

	private Long auctionID;
	private String auctionName;
	private User creator;
	private List<User> entrants;
	private Item item;
	private Date startDate;
	private Date endDate;
	private String currentPrice;

	public Auction() {

	}

	public Auction(String auctionName, User creator, List<User> entrants,
			Item item, Date startDate, Date endDate, String currentPrice) {
		this.auctionName = auctionName;
		this.creator = creator;
		this.entrants = entrants;
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

	public String getCurrentPrice() {
		return currentPrice;
	}

	public void setCurrentPrice(String currentPrice) {
		this.currentPrice = currentPrice;
	}

}
