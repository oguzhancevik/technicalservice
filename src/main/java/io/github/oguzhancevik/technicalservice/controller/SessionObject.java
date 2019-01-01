package io.github.oguzhancevik.technicalservice.controller;

import java.io.Serializable;
import java.security.Principal;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import io.github.oguzhancevik.technicalservice.dao.UserDao;
import io.github.oguzhancevik.technicalservice.model.entity.User;

/**
 * Session işlemlerini tutan sınıftır.
 * 
 * @author oguzhan
 *
 */
@ManagedBean(name = "sessionObject")
@SessionScoped
public class SessionObject implements Serializable {

	private static final long serialVersionUID = 1L;

	private User user;

	@EJB
	private UserDao userDao;

	/**
	 * @return login olan kullanıcının mail bilgisine göre User bilgisini döndürür.
	 */
	public User getUser() {
		if (user == null) {
			HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
					.getRequest();
			Principal principal = request.getUserPrincipal();
			if (principal != null) {
				user = userDao.findByEmail(principal.getName());
			}
		}
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
