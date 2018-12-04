package com.technicalservice.controller;

import java.io.IOException;
import java.security.Principal;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import com.technicalservice.model.type.MemberStatu;

/**
 * Login yetki işlemlerini gerçekleştiren sınıftır.
 * 
 * @author oguzhan
 */
@ManagedBean
@SessionScoped
public class LoginAuthenticator {

	private Boolean roleAdmin;
	private Boolean roleCustomer;

	@ManagedProperty(value = "#{sessionObject}")
	private SessionObject sessionObject;

	/**
	 * Kullanıcı login olurken çalışan ilk metoddur. Kullanıcının email bilgisi
	 * alınır.
	 */
	@PostConstruct
	public void init() {
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();

		Principal principal = request.getUserPrincipal();
		if (principal != null) {
			roleAdmin = request.isUserInRole("Admin");
			roleCustomer = request.isUserInRole("Customer");
		}
	}

	/**
	 * Kullanıcı'nın rolüne göre ilgili sayfaya yönlendiriyor.
	 * 
	 * @see #redirectUrlByRole()
	 * @return Yönlendirilecek sayfanın adresi dönüyor.
	 * @throws IOException
	 */
	public String redirectByRole() throws IOException {
		String redirectUrl = redirectUrlByRole();
		FacesContext.getCurrentInstance().getExternalContext().redirect(redirectUrl);
		return redirectUrl;
	}

	/**
	 * Kullanıcı'nın rolüne göre ilgili sayfanın adresini döndürüyor.
	 * 
	 * @see com.technicalservice.controller.SessionObject#getUser()
	 * @see #invalidateSession()
	 * @return Kullanıcı'nın rolüne göre ilgili sayfanın adresini döndürüyor
	 * @throws IOException
	 */
	public String redirectUrlByRole() throws IOException {
		String redirectUrl = "";
		if (getRoleAdmin() == null || getRoleCustomer() == null) {
			init();
		}

		MemberStatu memberStatu = getSessionObject().getUser().getMemberStatu();
		if (memberStatu.equals(MemberStatu.APPROVED)) {
			if (getRoleAdmin()) {
				redirectUrl = "admin/dashboard.jsf";
			} else if (getRoleCustomer()) {
				redirectUrl = "customer/dashboard.jsf";
			} else {
				invalidateSession();
			}
		} else if (memberStatu.equals(MemberStatu.WAITING)) {
			invalidateSession();
		} else if (memberStatu.equals(MemberStatu.DENIED)) {
			invalidateSession();
		}

		return redirectUrl;

	}

	/**
	 * Kullanıcı geçersiz bilgiler ile giriş yapmış ise yada var olan bir session'ı
	 * sonlandırmak istiyorsa bu metod session'ı sonlandırır.
	 * 
	 * @throws IOException
	 */
	public void invalidateSession() throws IOException {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();
		externalContext.invalidateSession();
		externalContext.redirect(externalContext.getRequestContextPath() + "/giris.jsf");
	}

	public SessionObject getSessionObject() {
		return sessionObject;
	}

	public void setSessionObject(SessionObject sessionObject) {
		this.sessionObject = sessionObject;
	}

	public Boolean getRoleAdmin() {
		if (roleAdmin == null) {
			init();
		}
		return roleAdmin;
	}

	public void setRoleAdmin(Boolean roleAdmin) {
		this.roleAdmin = roleAdmin;
	}

	public Boolean getRoleCustomer() {
		if (roleCustomer == null) {
			init();
		}
		return roleCustomer;
	}

	public void setRoleCustomer(Boolean roleCustomer) {
		this.roleCustomer = roleCustomer;
	}

}
