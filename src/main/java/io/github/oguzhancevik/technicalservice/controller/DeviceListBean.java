package io.github.oguzhancevik.technicalservice.controller;

import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import io.github.oguzhancevik.technicalservice.controller.base.BaseBean;
import io.github.oguzhancevik.technicalservice.dao.CustomerDao;
import io.github.oguzhancevik.technicalservice.dao.DeviceDao;
import io.github.oguzhancevik.technicalservice.dao.base.BaseDao;
import io.github.oguzhancevik.technicalservice.model.entity.Customer;
import io.github.oguzhancevik.technicalservice.model.entity.Device;

/**
 * Müşteri ekranında müşterinin cihazlarının listelendiği sınıftır.
 * 
 * @author oguzhan
 *
 */
@ManagedBean(name = "deviceListBean")
@ViewScoped
public class DeviceListBean extends BaseBean<Device> {

	private static final long serialVersionUID = 1L;

	@EJB
	private CustomerDao customerDao;

	@EJB
	private DeviceDao deviceDao;

	@Override
	public BaseDao<Device> getBaseDao() {
		return deviceDao;
	}

	/**
	 * Login olan kullanıcının bilgisine göre cihazları listeleyen metoddur.
	 */
	@Override
	public List<Device> listInitial() {
		Customer customer=new Customer();
		customer=customerDao.findByUser(getSessionObject().getUser());
		return deviceDao.listDevicesByCustomer(customer);
	}

}
