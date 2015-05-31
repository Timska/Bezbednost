package onlinebidding.model;

public class UserAuction {

	private Long ID;

	private User user;

	private Auction auction;

	public UserAuction() {

	}

	public UserAuction(User user, Auction auction) {
		this.user = user;
		this.auction = auction;
	}

	public Long getID() {
		return ID;
	}

	public void setID(Long iD) {
		ID = iD;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Auction getAuction() {
		return auction;
	}

	public void setAuction(Auction auction) {
		this.auction = auction;
	}

}
