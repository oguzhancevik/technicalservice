package com.technicalservice.controller;

import java.io.IOException;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.technicalservice.controller.base.BaseBean;
import com.technicalservice.dao.UserDao;
import com.technicalservice.dao.base.BaseDao;
import com.technicalservice.model.entity.User;
import com.technicalservice.model.type.MemberStatu;
import com.technicalservice.util.UtilLog;

/**
 * Admin ekranında adminlerin listelendiği sınıftır.
 * 
 * @author oguzhan
 *
 */
@ManagedBean(name = "adminListBean")
@ViewScoped
public class AdminListBean extends BaseBean<User> {

	private static final long serialVersionUID = 1L;

	@EJB
	private UserDao userDao;

	@Override
	public List<User> listInitial() {
		return userDao.listAdmin();
	}

	@Override
	public BaseDao<User> getBaseDao() {
		return userDao;
	}

	
	/**
	 * Admin kullanıcısının üyelik durumu değiştirilmek istenince bu metod kullanılır.
	 * @param user Sisteme kayıtlı olan admin
	 * @param memberStatu admin üyelik durumu
	 * @throws IOException
	 */
	public void save(User user, MemberStatu memberStatu) throws IOException {

		try {
			user.setMemberStatu(memberStatu);
			userDao.save(user);
			UtilLog.logToScreen(FacesMessage.SEVERITY_INFO, "BAŞARILI",
					"Admin üyelik durumu " + memberStatu.getDisplayName() + " olarak güncellendi!");
		} catch (Exception e) {
			e.printStackTrace();
			UtilLog.logToScreen(FacesMessage.SEVERITY_ERROR, "HATA",
					"Admin üyelik durumu " + memberStatu.getDisplayName() + " olarak değiştirilemedi!");
		}

	}

}
