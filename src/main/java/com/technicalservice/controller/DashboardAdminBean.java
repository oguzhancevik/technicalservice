package com.technicalservice.controller;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;

import com.technicalservice.dao.CustomerDao;
import com.technicalservice.dao.DeviceDao;
import com.technicalservice.dao.IssueDao;
import com.technicalservice.dao.UserDao;
import com.technicalservice.model.entity.User;
import com.technicalservice.util.UtilLog;

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

	@EJB
	private DeviceDao deviceDao;

	@EJB
	private IssueDao issueDao;

	private Long customerCount;

	private Long adminCount;

	private Long deviceCount;

	private Long issueCount;

	@PostConstruct
	public void init() {
		customerCount = customerDao.getCustomerCount();
		adminCount = userDao.getAdminCount();
		deviceCount = deviceDao.getDeviceCount(null);
		issueCount = issueDao.getIssueCount(null, null);
	}

	public void redirectNew(String page) {
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect(page);
		} catch (IOException e) {
			UtilLog.logToScreen(e);
		}
	}

	public void updatePage(User updateModel, String page) {
		try {
			Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
			flash.put("model", updateModel);

			FacesContext.getCurrentInstance().getExternalContext().redirect(page);
		} catch (Exception e) {
			UtilLog.logToScreen(e);
		}
	}

	public Long getCustomerCount() {
		return customerCount;
	}

	public void setCustomerCount(Long customerCount) {
		this.customerCount = customerCount;
	}

	public Long getAdminCount() {
		return adminCount;
	}

	public void setAdminCount(Long adminCount) {
		this.adminCount = adminCount;
	}

	public Long getDeviceCount() {
		return deviceCount;
	}

	public void setDeviceCount(Long deviceCount) {
		this.deviceCount = deviceCount;
	}

	public Long getIssueCount() {
		return issueCount;
	}

	public void setIssueCount(Long issueCount) {
		this.issueCount = issueCount;
	}

}
