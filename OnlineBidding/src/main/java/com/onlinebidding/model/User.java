package com.onlinebidding.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class User {

	@Id
	private String userName;
	private String password;
	private String firstName;
	private String lastName;
	private String mail;
	private Date birth;
	@ManyToMany(fetch = FetchType.EAGER)
	private List<Auction> enteredAuctions;

	public User() {

	}

	public String getFirstName() {
		return firstName;
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
		this.enteredAuctions = enteredAuctions;
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

	public List<Auction> getEnteredAuctions() {
		return enteredAuctions;
	}

	public void setEnteredAuctions(List<Auction> enteredAuctions) {
		this.enteredAuctions = enteredAuctions;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

}
