package com.technicalservice.controller;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.technicalservice.dao.CustomerDao;
import com.technicalservice.dao.UserDao;

/**
 * Admin kullanıcısının anasayfasında sistemde kayıtlı kaç kullanıcı, ürün vs
 * gibi bilgilerin verildiği sınıftır.
 * 
 * @author oguzhan
 */
@ManagedBean(name = "dashboardAdminBean")
@ViewScoped
public class DashboardAdminBean {

	@EJB
	private CustomerDao customerDao;

	@EJB
	private UserDao userDao;

	private Integer customerCount;

	private Integer adminCount;

	@PostConstruct
	public void init() {
		customerCount = customerDao.listAll().size();
		adminCount = userDao.listAdmin().size();
	}

	public Integer getCustomerCount() {
		return customerCount;
	}

	public void setCustomerCount(Integer customerCount) {
		this.customerCount = customerCount;
	}

	public Integer getAdminCount() {
		return adminCount;
	}

	public void setAdminCount(Integer adminCount) {
		this.adminCount = adminCount;
	}

}
