package io.github.oguzhancevik.technicalservice.model.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import io.github.oguzhancevik.technicalservice.model.base.ExtendedModel;
import io.github.oguzhancevik.technicalservice.model.type.Gender;

/**
 * 
 * @author oguzhan
 *
 */
@Entity
@Table
public class Customer extends ExtendedModel {

	private static final long serialVersionUID = 1L;

	@OneToOne(cascade = CascadeType.ALL)
	private User user;

	private String name;

	private String surname;

	@Transient
	private String fullName;

	private String mobileNo;

	private String address;

	@Enumerated(EnumType.STRING)
	private Gender gender;

	@Temporal(TemporalType.DATE)
	private Date birthDate;
	
	@OneToMany(mappedBy = "owner", fetch = FetchType.LAZY)
	private List<Device> devices;

	public Customer() {
	}

	public Customer(User user) {
		super();
		this.user = user;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		if (name != null) {
			name = name.trim();
		}
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		if (surname != null) {
			surname = surname.trim();
		}
		this.surname = surname;
	}

	public String getFullName() {
		fullName = name + " " + surname;
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		if (mobileNo != null) {
			mobileNo = mobileNo.replaceAll(" ", "").replaceAll("\\(", "").replaceAll("\\)", "").trim();
		}
		if (mobileNo != null && mobileNo.trim().equals("")) {
			mobileNo = null;
		}
		this.mobileNo = mobileNo;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public List<Device> getDevices() {
		return devices;
	}

	public void setDevices(List<Device> devices) {
		this.devices = devices;
	}

	@Override
	public String toString() {
		return "Customer [fullName=" + fullName + ", mobileNo=" + mobileNo + "]";
	}

}
