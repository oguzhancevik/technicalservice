package io.github.oguzhancevik.technicalservice.controller;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import io.github.oguzhancevik.technicalservice.dao.CustomerDao;
import io.github.oguzhancevik.technicalservice.dao.UserDao;
import io.github.oguzhancevik.technicalservice.model.entity.Customer;
import io.github.oguzhancevik.technicalservice.model.entity.User;
import io.github.oguzhancevik.technicalservice.model.type.MemberStatu;
import io.github.oguzhancevik.technicalservice.util.UtilLog;

/**
 * Kullanıcının kayıt işlemlerini gerçekleştiren sınıftır.
 * 
 * @author oguzhan
 */
@ManagedBean(name = "loginBean")
@ViewScoped
public class LoginBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private UserDao userDao;

	@EJB
	private CustomerDao customerDao;

	private Customer customer;

	private String pass2;

	private String redirectPage;

	@PostConstruct
	public void init() {
		customer = new Customer(new User());
	}

	/**
	 * Yeni bir kullanıcı kayıt olurken bu fonksiyon çalışır. Şifreler istenen
	 * formatda ise kayıt gerçekleşir değilse ekrana hata mesajını verir.
	 * 
	 * @see io.github.oguzhancevik.technicalservice.dao.UserDao#controlPassword(String, String)
	 * @see io.github.oguzhancevik.technicalservice.dao.base.BaseDao#save(io.github.oguzhancevik.technicalservice.model.base.ExtendedModel)
	 */
	public void save() {
		try {
			String message = userDao.controlPassword(customer.getUser().getPassword(), getPass2());
			if (message.equals("success")) {
				customer.getUser().setMemberStatu(MemberStatu.WAITING);
				customer.getUser().setRole("Customer");
				customerDao.save(customer);
				redirectPage = "success";
			} else {
				UtilLog.logToScreen(FacesMessage.SEVERITY_ERROR, "HATA", message);
			}
		} catch (Exception e) {
			UtilLog.logToScreen(e);
		}
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public String getPass2() {
		return pass2;
	}

	public void setPass2(String pass2) {
		this.pass2 = pass2;
	}

	public String getRedirectPage() {
		return redirectPage;
	}

	public void setRedirectPage(String redirectPage) {
		this.redirectPage = redirectPage;
	}

}
