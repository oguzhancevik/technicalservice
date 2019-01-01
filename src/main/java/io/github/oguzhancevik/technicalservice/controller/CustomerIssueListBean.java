package io.github.oguzhancevik.technicalservice.controller;

import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import io.github.oguzhancevik.technicalservice.controller.base.BaseBean;
import io.github.oguzhancevik.technicalservice.dao.CustomerDao;
import io.github.oguzhancevik.technicalservice.dao.IssueDao;
import io.github.oguzhancevik.technicalservice.dao.base.BaseDao;
import io.github.oguzhancevik.technicalservice.model.entity.Customer;
import io.github.oguzhancevik.technicalservice.model.entity.Issue;

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

}
