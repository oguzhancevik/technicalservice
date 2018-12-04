package com.technicalservice.dao;

import javax.ejb.Stateless;

import com.technicalservice.dao.base.BaseDao;
import com.technicalservice.model.entity.Customer;
import com.technicalservice.model.entity.User;

/**
 * Customer nesnesi için database işlemlerinin yapıldığı sınıftır.
 * 
 * @author oguzhan
 *
 */
@Stateless
public class CustomerDao extends BaseDao<Customer> {

	private static final long serialVersionUID = 1L;

	public CustomerDao() {
		super(Customer.class);
	}

	/**
	 * @param user
	 *            Sisteme giriş yapan kullanıcı
	 * @return Sisteme giriş yapan kullanıcıya göre hangi müşteri olduğunu döndürür
	 */
	public Customer findByUser(User user) {
		return (Customer) entityManager.createQuery("SELECT c from Customer c WHERE c.user= :user")
				.setParameter("user", user).getSingleResult();
	}

}
