package io.github.oguzhancevik.technicalservice.controller;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;

import io.github.oguzhancevik.technicalservice.dao.CustomerDao;
import io.github.oguzhancevik.technicalservice.dao.DeviceDao;
import io.github.oguzhancevik.technicalservice.dao.IssueDao;
import io.github.oguzhancevik.technicalservice.model.entity.Customer;
import io.github.oguzhancevik.technicalservice.model.entity.Device;
import io.github.oguzhancevik.technicalservice.model.entity.Issue;
import io.github.oguzhancevik.technicalservice.model.type.IssueStatu;
import io.github.oguzhancevik.technicalservice.model.type.ProcessType;
import io.github.oguzhancevik.technicalservice.util.UtilLog;

/**
 * Customer kullanıcısı tarafından bakım onarım kaydı eklemek için veya
 * güncellemek için kullanılan sınıftır.
 * 
 * @author oguzhan
 */
@ManagedBean(name = "customerIssueDetailBean")
@ViewScoped
public class CustomerIssueDetailBean {

	@ManagedProperty(value = "#{sessionObject}")
	private SessionObject sessionObject;

	@EJB
	private CustomerDao customerDao;

	@EJB
	private DeviceDao deviceDao;

	@EJB
	private IssueDao issueDao;

	private List<Device> devices;

	private Customer customer;

	private Issue issue;

	/**
	 * Bakım/Onarım listesi sayfasından Flash ile bir obje gönderilmiş ise burada
	 * kontrol ediliyor. Dolu ise issue değişkenine gelen obje atanıyor.
	 */
	@PostConstruct
	public void init() {
		customer = new Customer();
		customer = customerDao.findByUser(sessionObject.getUser());
		devices = deviceDao.listDevicesByCustomer(customer);
		issue = new Issue();
		Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
		if (flash.containsKey("model")) {
			issue = (Issue) flash.get("model");
		}
	}

	/**
	 * @param page
	 *            Yönlendirilecek sayfa adresi
	 */
	public void save(String page) {
		try {

			if (issue.getIssueStatu().equals(IssueStatu.REPAIR)) {
				issue.setDate(new Date());
			}
			issue.setDeviceOwner(customer);
			issue.setProcessType(ProcessType.WAITING);
			issueDao.save(issue);
			FacesContext.getCurrentInstance().getExternalContext().redirect(page);
		} catch (Exception e) {
			UtilLog.logToScreen(FacesMessage.SEVERITY_ERROR, "HATA", "Bakım/Onarım Kaydedilemedi!");
			UtilLog.log(e);
		}
	}

	public SessionObject getSessionObject() {
		return sessionObject;
	}

	public void setSessionObject(SessionObject sessionObject) {
		this.sessionObject = sessionObject;
	}

	public List<Device> getDevices() {
		return devices;
	}

	public void setDevices(List<Device> devices) {
		this.devices = devices;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Issue getIssue() {
		return issue;
	}

	public void setIssue(Issue issue) {
		this.issue = issue;
	}

}
