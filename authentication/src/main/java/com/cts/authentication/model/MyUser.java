package com.cts.authentication.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Entity
@Table(name = "user")
public class MyUser {
	@Id
	private String userid;
	@Column
	@NotNull
	@Pattern(regexp = "^[a-zA-Z ]{5,8}$", message = "User name should contain only alphabets with minimum 5 characters, maximum 8 characters")
	private String username;
	@Column
	@Email(message = "Invalid Email address, please match the email format as xyz@xyz.com")
	private String emailaddress;
	@Column
	@NotNull
	@Size(min = 10, max = 10, message = "Please provide 10 digit contact number")
	private String contactno;
	@Column
	//@Past
	private Date dob;
	@Column
	private String usertype;
	@Column
	@NotNull
	@Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$", message = "Password should be greater than 8 characters long and should contain atleast one uppercase,one numeric and a special character")
	private String password;

	/*
	 * public String randomGenerator() { Random random = new Random(); int n =
	 * random.nextInt(100, 999); System.out.println(n); return "R-" + n; }
	 */

	// @Id
	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getUserid() {
		return userid;
	}

	public String getUserName() {
		return username;
	}

	public void setUserName(String username) {
		this.username = username;
	}

	public String getEmailaddress() {
		return emailaddress;
	}

	public void setEmailaddress(String emailaddress) {
		this.emailaddress = emailaddress;
	}

	public String getContactno() {
		return contactno;
	}

	public void setContactno(String contactno) {
		this.contactno = contactno;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public String getUsertype() {
		return usertype;
	}

	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public MyUser(String userid,
			 String username,
			 String emailaddress,
			 String contactno,
			 Date dob,  String usertype,
			 String password) {
		super();
		this.userid = userid;
		this.username = username;
		this.emailaddress = emailaddress;
		this.contactno = contactno;
		this.dob = dob;
		this.usertype = usertype;
		this.password = password;
	}

	public MyUser() {
		super();
	}

	
	

}
