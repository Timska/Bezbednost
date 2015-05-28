package tables;

import java.sql.Date;

public class User {

	private String firstName;
	private String lastName;
	private String userName;
	private String password;
	private String mail;
	private Date birth;
	
	public User(String firstName, String lastName, String userName, String password, String mail, Date birth){
		this.firstName = firstName;
		this.lastName = lastName;
		this.userName = userName;
		this.password = password;
		this.mail = mail;
		this.birth = birth;
	}

	public Date getBirth() {
		return birth;
	}

	public String getMail() {
		return mail;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getUserName() {
		return userName;
	}

	public String getPassword() {
		return password;
	}
	
	
}
