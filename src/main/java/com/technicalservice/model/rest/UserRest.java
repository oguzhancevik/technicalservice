package com.technicalservice.model.rest;

import java.io.Serializable;

/**
 * @author oguzhan
 */
public class UserRest implements Serializable {

	private static final long serialVersionUID = 1L;

	private String email;

	private String password;

	private String name;

	private String surname;

	private String mobileNo;

	private String memberStatu;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getMemberStatu() {
		return memberStatu;
	}

	public void setMemberStatu(String memberStatu) {
		this.memberStatu = memberStatu;
	}

	@Override
	public String toString() {
		return "CustomerRest [email=" + email + ", name=" + name + ", surname=" + surname + "]";
	}

}
