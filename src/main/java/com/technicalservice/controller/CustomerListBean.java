package com.technicalservice.controller;

import java.io.IOException;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.technicalservice.controller.base.BaseBean;
import com.technicalservice.dao.CustomerDao;
import com.technicalservice.dao.base.BaseDao;
import com.technicalservice.model.entity.Customer;
import com.technicalservice.model.type.MemberStatu;
import com.technicalservice.util.UtilLog;

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
			e.printStackTrace();
			UtilLog.logToScreen(FacesMessage.SEVERITY_ERROR, "HATA",
					"Müşteri üyelik durumu " + memberStatu.getDisplayName() + " olarak değiştirilemedi!");
		}

	}

	/**
	 * Sistemden pasif edilecek müşterinin user statusu 0 yapılır.
	 */
	@Override
	public void remove(Customer removeModel) {
		removeModel.getUser().setStatus(0);
		super.remove(removeModel);
	}

}
