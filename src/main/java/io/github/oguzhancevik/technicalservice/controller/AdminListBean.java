package io.github.oguzhancevik.technicalservice.controller;

import java.io.IOException;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import io.github.oguzhancevik.technicalservice.controller.base.BaseBean;
import io.github.oguzhancevik.technicalservice.dao.UserDao;
import io.github.oguzhancevik.technicalservice.dao.base.BaseDao;
import io.github.oguzhancevik.technicalservice.model.entity.User;
import io.github.oguzhancevik.technicalservice.model.type.MemberStatu;
import io.github.oguzhancevik.technicalservice.util.UtilLog;

/**
 * Admin ekranında admin kullanıcılarının listelendiği sınıftır.
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
			UtilLog.log(e);
			UtilLog.logToScreen(FacesMessage.SEVERITY_ERROR, "HATA",
					"Admin üyelik durumu " + memberStatu.getDisplayName() + " olarak değiştirilemedi!");
		}

	}

}
