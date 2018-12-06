package com.technicalservice.controller;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;

import com.technicalservice.dao.CustomerDao;
import com.technicalservice.dao.DeviceDao;
import com.technicalservice.dao.IssueDao;
import com.technicalservice.model.entity.Customer;
import com.technicalservice.model.entity.User;
import com.technicalservice.model.type.IssueStatu;
import com.technicalservice.util.UtilLog;

/**
 * Customer kullanıcısının anasayfasında sistemde kayıtlı cihaz vs gibi
 * bilgilerin verildiği sınıftır.
 * 
 * @author oguzhan
 */
@ManagedBean(name = "dashboardUserBean")
@ViewScoped
public class DashboardUserBean {

	@ManagedProperty(value = "#{sessionObject}")
	private SessionObject sessionObject;

	@EJB
	private DeviceDao deviceDao;

	@EJB
	private CustomerDao customerDao;

	@EJB
	private IssueDao issueDao;

	private Long deviceCount;

	private Long maintenanceCount;

	private Long repairCount;

	@PostConstruct
	public void init() {
		Customer customer = customerDao.findByUser(sessionObject.getUser());

		deviceCount = deviceDao.getDeviceCount(customer);
		maintenanceCount=issueDao.getIssueCount(customer, IssueStatu.MAINTENANCE);
		repairCount=issueDao.getIssueCount(customer, IssueStatu.REPAIR);
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
			Customer customer = customerDao.findByUser(updateModel);
			Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
			flash.put("model", customer);

			FacesContext.getCurrentInstance().getExternalContext().redirect(page);
		} catch (Exception e) {
			UtilLog.logToScreen(e);
		}
	}

	public SessionObject getSessionObject() {
		return sessionObject;
	}

	public void setSessionObject(SessionObject sessionObject) {
		this.sessionObject = sessionObject;
	}

	public Long getDeviceCount() {
		return deviceCount;
	}

	public void setDeviceCount(Long deviceCount) {
		this.deviceCount = deviceCount;
	}

	public Long getMaintenanceCount() {
		return maintenanceCount;
	}

	public void setMaintenanceCount(Long maintenanceCount) {
		this.maintenanceCount = maintenanceCount;
	}

	public Long getRepairCount() {
		return repairCount;
	}

	public void setRepairCount(Long repairCount) {
		this.repairCount = repairCount;
	}

}