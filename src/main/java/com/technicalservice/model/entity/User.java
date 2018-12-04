package com.technicalservice.model.entity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.technicalservice.model.base.ExtendedModel;
import com.technicalservice.model.type.MemberStatu;

/**
 * @author oguzhan
 */
@Entity
@Table(name = "users")
public class User extends ExtendedModel {

	private static final long serialVersionUID = 1L;

	private String email;

	private String password;

	private String role;

	@Enumerated(EnumType.STRING)
	private MemberStatu memberStatu;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		if (email != null) {
			email = email.trim();
		}
		if (email != null && email.trim().equals("")) {
			email = null;
		}
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public MemberStatu getMemberStatu() {
		return memberStatu;
	}

	public void setMemberStatu(MemberStatu memberStatu) {
		this.memberStatu = memberStatu;
	}

}
