package io.github.oguzhancevik.technicalservice.controller;

import java.io.IOException;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import io.github.oguzhancevik.technicalservice.controller.base.BaseBean;
import io.github.oguzhancevik.technicalservice.dao.CustomerDao;
import io.github.oguzhancevik.technicalservice.dao.base.BaseDao;
import io.github.oguzhancevik.technicalservice.model.entity.Customer;
import io.github.oguzhancevik.technicalservice.model.type.MemberStatu;
import io.github.oguzhancevik.technicalservice.util.UtilLog;

/**
 * Admin ekranında müşterilerin listelendiği sınıftır.
 * 
 * @author oguzhan
 *
 */
@ManagedBean(name = "customerListBean")
@ViewScoped
public class CustomerListBean extends BaseBean<Customer> {

	private static final long serialVersionUID = 1L;

	@EJB
	private CustomerDao customerDao;

	@Override
	public List<Customer> listInitial() {
		return customerDao.listAll();
	}

	@Override
	public BaseDao<Customer> getBaseDao() {
		return customerDao;
	}

	/**
	 * Müşterinin üyelik durumu değiştirilmek istenince bu metod kullanılır.
	 * 
	 * @param customer
	 *            Sisteme kayıtlı olan müşteri
	 * @param memberStatu
	 *            Müşterinin üyelik durumu
	 * @throws IOException
	 */
	public void save(Customer customer, MemberStatu memberStatu) throws IOException {

		try {
			customer.getUser().setMemberStatu(memberStatu);
			customerDao.save(customer);
			UtilLog.logToScreen(FacesMessage.SEVERITY_INFO, "BAŞARILI",
					"Müşteri üyelik durumu " + memberStatu.getDisplayName() + " olarak güncellendi!");
		} catch (Exception e) {
			UtilLog.log(e);
			UtilLog.logToScreen(FacesMessage.SEVERITY_ERROR, "HATA",
					"Müşteri üyelik durumu " + memberStatu.getDisplayName() + " olarak değiştirilemedi!");
		}

	}

	/**
	 * Sistemden pasif edilecek müşterinin user statusu de 0 yapılır.
	 */
	@Override
	public void remove(Customer removeModel) {
		removeModel.getUser().setStatus(0);
		super.remove(removeModel);
	}

}
