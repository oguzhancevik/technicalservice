package com.technicalservice.controller;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;

import com.technicalservice.dao.CustomerDao;
import com.technicalservice.model.entity.Customer;
import com.technicalservice.model.entity.User;
import com.technicalservice.util.UtilLog;

/**
 * Admin kullanıcısı tarafından yeni müşteri eklemek için veya müşteriyi
 * güncellemek için kullanılan sınıftır.
 * 
 * @author oguzhan
 */
@ManagedBean(name = "customerDetailBean")
@ViewScoped
public class CustomerDetailBean {

	@EJB
	private CustomerDao customerDao;

	private Customer customer;

	/**
	 * Müşteri listesi sayfasından Flash ile bir obje gönderilmiş ise burada kontrol
	 * ediliyor. Dolu ise customer değişkenine gelen obje atanıyor.
	 */
	@PostConstruct
	public void init() {
		customer = new Customer(new User());
		Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
		if (flash.containsKey("model")) {
			customer = (Customer) flash.get("model");
		}
	}

	/**
	 * @param page Yönlendirilecek sayfa adresi
	 */
	public void save(String page) {
		try {
			customer.getUser().setRole("Customer");
			customerDao.save(customer);
			FacesContext.getCurrentInstance().getExternalContext().redirect(page);
		} catch (Exception e) {
			UtilLog.logToScreen(FacesMessage.SEVERITY_ERROR, "HATA", "Müşteri Kaydedilemedi!");
			e.printStackTrace();
		}
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

}
