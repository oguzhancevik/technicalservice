package io.github.oguzhancevik.technicalservice.controller;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;

import io.github.oguzhancevik.technicalservice.dao.CustomerDao;
import io.github.oguzhancevik.technicalservice.dao.UserDao;
import io.github.oguzhancevik.technicalservice.model.entity.Customer;
import io.github.oguzhancevik.technicalservice.model.entity.User;
import io.github.oguzhancevik.technicalservice.util.UtilLog;

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
	private UserDao userDao;

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
	 * Müşteri kaydetmek için kullanılan metoddur. Daha önceden var olan bir email ile
	 * kayıt olunacak ise uyarı mesajı vermekte. Yeni kayıt olacak ise rolü Customer
	 * olarak atandıktan sonra database kayıt yapılmaktadır. Daha sonra page
	 * adresindeki sayfaya yönlendirilmektedir.
	 * 
	 * @param page
	 *            Yönlendirilecek sayfa adresi
	 */
	public void save(String page) {
		try {
			if (customer.getId() == null && userDao.findByEmail(customer.getUser().getEmail()) != null) {
				UtilLog.logToScreen(FacesMessage.SEVERITY_ERROR, "HATA", "Email zaten kayıtlı!");
			} else {
				customer.getUser().setRole("Customer");
				customerDao.save(customer);
			}
			FacesContext.getCurrentInstance().getExternalContext().redirect(page);
		} catch (Exception e) {
			UtilLog.logToScreen(FacesMessage.SEVERITY_ERROR, "HATA", "Müşteri Kaydedilemedi!");
			UtilLog.log(e);
		}
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

}
