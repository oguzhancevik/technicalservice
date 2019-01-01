package io.github.oguzhancevik.technicalservice.dao;

import javax.ejb.Stateless;

import io.github.oguzhancevik.technicalservice.dao.base.BaseDao;
import io.github.oguzhancevik.technicalservice.model.entity.Customer;
import io.github.oguzhancevik.technicalservice.model.entity.User;

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
		return (Customer) entityManager.createQuery("SELECT c FROM Customer c WHERE c.user= :user")
				.setParameter("user", user).getSingleResult();
	}

	/**
	 * @return Kayıtlı Müşteri sayısını döndürür.
	 */
	public Long getCustomerCount() {
		return (Long) entityManager.createQuery("SELECT COUNT(c) FROM Customer c WHERE c.status=1").getSingleResult();
	}

}
