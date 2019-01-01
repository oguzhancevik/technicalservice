package io.github.oguzhancevik.technicalservice.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;

import io.github.oguzhancevik.technicalservice.dao.base.BaseDao;
import io.github.oguzhancevik.technicalservice.model.entity.Customer;
import io.github.oguzhancevik.technicalservice.model.entity.Issue;
import io.github.oguzhancevik.technicalservice.model.type.IssueStatu;
import io.github.oguzhancevik.technicalservice.model.type.ProcessType;

/**
 * Issue nesnesi için database işlemlerinin yapıldığı sınıftır.
 * 
 * @author oguzhan
 */
@Stateless
public class IssueDao extends BaseDao<Issue> {

	private static final long serialVersionUID = 1L;

	/**
	 * Müşterinin açtığı bakım onarım kayıtları listelenir.
	 * 
	 * @param deviceOwner
	 *            Bakım/ Onarım bilgisi listelenecek olan müşteri
	 * @return Bakım/ Onarım listesi döndürür.
	 */
	@SuppressWarnings("unchecked")
	public List<Issue> listIssuesByCustomer(Customer deviceOwner) {
		return entityManager
				.createQuery(
						"SELECT i FROM Issue i JOIN FETCH i.device d WHERE i.deviceOwner= :deviceOwner AND i.status=1 ORDER BY i.id")
				.setParameter("deviceOwner", deviceOwner).getResultList();
	}

	/**
	 * 
	 * @param deviceOwner
	 *            Bakım/ Onarım sayısı döndürülecek olan müşteri
	 * @param issueStatu
	 *            Bakım / Onarım tipi
	 * @return deviceOwner Bakım / Onarım sayısı döndürülür
	 */
	public Long getIssueCount(Customer deviceOwner, IssueStatu issueStatu) {

		Long issueCount = 0L;

		StringBuilder query = new StringBuilder(" SELECT COUNT(i) FROM Issue i WHERE i.status=1 ");

		if (deviceOwner != null && issueStatu == null) {
			query.append(" AND i.deviceOwner= :deviceOwner ");
			issueCount = (Long) entityManager.createQuery(query.toString()).setParameter("deviceOwner", deviceOwner)
					.getSingleResult();
		} else if (issueStatu != null && deviceOwner == null) {
			query.append(" AND i.issueStatu= :issueStatu ");
			issueCount = (Long) entityManager.createQuery(query.toString()).setParameter("issueStatu", issueStatu)
					.getSingleResult();
		} else if (issueStatu != null && deviceOwner != null) {
			query.append(" AND i.deviceOwner= :deviceOwner AND i.issueStatu= :issueStatu ");
			issueCount = (Long) entityManager.createQuery(query.toString()).setParameter("deviceOwner", deviceOwner)
					.setParameter("issueStatu", issueStatu).getSingleResult();
		} else {
			issueCount = (Long) entityManager.createQuery(query.toString()).getSingleResult();
		}

		return issueCount;
	}

	/**
	 * 
	 * @param issueStatu
	 *            Bakım / Onarım tipi
	 * @param processType
	 *            Bakım onarımın devam ettiği bittiği değeri
	 * @return Bakım/ Onarım listesi döndürür.
	 */
	@SuppressWarnings("unchecked")
	public List<Issue> getIssuesByProcess(IssueStatu issueStatu, ProcessType processType) {

		List<Issue> issues = new ArrayList<>();

		StringBuilder query = new StringBuilder(" SELECT i FROM Issue i JOIN FETCH i.device d WHERE i.status=1 ");

		if (processType != null && issueStatu == null) {
			query.append(" AND i.processType= :processType ORDER BY i.id");
			issues = entityManager.createQuery(query.toString()).setParameter("processType", processType)
					.getResultList();
		} else if (issueStatu != null && processType == null) {
			query.append(" AND i.issueStatu= :issueStatu ORDER BY i.id");
			issues = entityManager.createQuery(query.toString()).setParameter("issueStatu", issueStatu).getResultList();
		} else if (issueStatu != null && processType != null) {
			query.append(" AND i.processType= :processType AND i.issueStatu= :issueStatu ORDER BY i.id");
			issues = entityManager.createQuery(query.toString()).setParameter("processType", processType)
					.setParameter("issueStatu", issueStatu).getResultList();
		} else {
			issues = entityManager.createQuery(query.toString()).getResultList();
		}

		return issues;
	}

	@SuppressWarnings("unchecked")
	public List<Issue> getIssueByMaintenanceDay(Date date, IssueStatu issueStatu, ProcessType processType) {
		return entityManager.createQuery(
				"SELECT i FROM Issue i where i.date <= :date AND i.issueStatu= :issueStatu AND i.processType= :processType AND i.status=1 ORDER BY i.id")
				.setParameter("date", date).setParameter("issueStatu", issueStatu)
				.setParameter("processType", processType).getResultList();
	}

}
