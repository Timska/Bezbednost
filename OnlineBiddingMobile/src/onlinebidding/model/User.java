package onlinebidding.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;


public class User implements Serializable{

	private String userName;
	private String password;
	private String firstName;
	private String lastName;
	private String mail;
	private Date birth;
	
	public User() {

	}

	public String getFirstName() {
		return firstName;
	}
	
	public User(String userName, String password, String firstName,
			String lastName, String mail, String birth) {
		this.userName = userName;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.mail = mail;
		StringTokenizer st = new StringTokenizer(birth, "-");
		int year = Integer.parseInt(st.nextToken());
		int month = Integer.parseInt(st.nextToken());
		int day = Integer.parseInt(st.nextToken());
		this.birth = new Date(year-1900, month-1, day);
		System.out.println(this.birth);
	}

	public User(String userName, String password, String firstName,
			String lastName, String mail, Date birth,
			List<Auction> enteredAuctions) {
		this.userName = userName;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.mail = mail;
		this.birth = birth;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public Date getBirth() {
		return birth;
	}

	public void setBirth(Date birth) {
		this.birth = birth;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@Override
	public String toString(){
		return this.userName + "\t" + this.mail;
	}
}
