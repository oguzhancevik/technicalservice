package com.technicalservice.controller;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;

import com.technicalservice.dao.UserDao;
import com.technicalservice.model.entity.User;
import com.technicalservice.util.UtilLog;

/**
 * Admin kullanıcısı tarafından yeni admin eklemek için veya admin kullanıcısını
 * güncellemek için kullanılan sınıftır.
 * 
 * @author oguzhan
 */
@ManagedBean(name = "adminDetailBean")
@ViewScoped
public class AdminDetailBean {

	@EJB
	private UserDao userDao;

	private User user;

	/**
	 * Admin listesi sayfasından Flash ile bir obje gönderilmiş ise burada kontrol
	 * ediliyor. Dolu ise customer değişkenine gelen obje atanıyor.
	 */
	@PostConstruct
	public void init() {
		user = new User();
		Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
		if (flash.containsKey("model")) {
			user = (User) flash.get("model");
		}
	}

	/**
	 * @param page
	 *            yönlendirilecek sayfa adresi
	 */
	public void save(String page) {
		try {
			user.setRole("Admin");
			userDao.save(user);
			FacesContext.getCurrentInstance().getExternalContext().redirect(page);
		} catch (Exception e) {
			UtilLog.logToScreen(FacesMessage.SEVERITY_ERROR, "HATA", "Admin Kaydedilemedi!");
			e.printStackTrace();
		}
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
