package com.technicalservice.controller;

import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.technicalservice.controller.base.BaseBean;
import com.technicalservice.dao.CustomerDao;
import com.technicalservice.dao.IssueDao;
import com.technicalservice.dao.base.BaseDao;
import com.technicalservice.model.entity.Customer;
import com.technicalservice.model.entity.Issue;

/**
 * Müşteri ekranında müşterinin Onarım / Bakım işlemlerinin listelendiği
 * sınıftır.
 * 
 * @author oguzhan
 *
 */
@ManagedBean(name = "customerIssueListBean")
@ViewScoped
public class CustomerIssueListBean extends BaseBean<Issue> {

	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{sessionObject}")
	private SessionObject sessionObject;

	@EJB
	private IssueDao issueDao;

	@EJB
	private CustomerDao customerDao;

	@Override
	public BaseDao<Issue> getBaseDao() {
		return issueDao;
	}

	/**
	 * Login olan kullanıcının bilgisine göre Onarım / Bakım işlemlerini listeleyen
	 * metoddur.
	 */
	@Override
	public List<Issue> listInitial() {
		Customer customer = new Customer();
		customer = customerDao.findByUser(getSessionObject().getUser());
		return issueDao.listIssuesByCustomer(customer);
	}

	public SessionObject getSessionObject() {
		return sessionObject;
	}

	public void setSessionObject(SessionObject sessionObject) {
		this.sessionObject = sessionObject;
	}

}
