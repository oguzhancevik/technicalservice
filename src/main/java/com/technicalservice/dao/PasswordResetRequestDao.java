package com.technicalservice.dao;

import java.math.BigInteger;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.technicalservice.dao.base.BaseDao;
import com.technicalservice.model.entity.PasswordResetRequest;

/**
 * PasswordResetRequest nesnesi için database işlemlerinin yapıldığı sınıftır.
 * @author oguzhan
 *
 */
@Stateless
@LocalBean
public class PasswordResetRequestDao extends BaseDao<PasswordResetRequest> {

	private static final long serialVersionUID = 1L;

	public PasswordResetRequestDao() {
		super(PasswordResetRequest.class);
	}

	/**
	 * Kulanıcı mailine göre aktif kaç tane şifre yenileme talebi gönderildiğini
	 * döner.
	 * 
	 * @param email şifre sıfırlama sayısını bulmak için kullanıcı mailidir.
	 * @return şifre yenileme talep sayısını döndürür.
	 */
	public BigInteger passwordTokenControl(String email) {
		String query = "SELECT pr FROM PasswordResetRequest pr WHERE pr.user.email= :email and status=1";
		BigInteger tokenCount = new BigInteger(
				Integer.toString(entityManager.createQuery(query).setParameter("email", email).getResultList().size()));
		return tokenCount;
	}

}
