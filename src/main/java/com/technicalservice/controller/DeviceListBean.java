package com.technicalservice.controller;

import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.technicalservice.controller.base.BaseBean;
import com.technicalservice.dao.CustomerDao;
import com.technicalservice.dao.DeviceDao;
import com.technicalservice.dao.base.BaseDao;
import com.technicalservice.model.entity.Customer;
import com.technicalservice.model.entity.Device;

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

	@ManagedProperty(value = "#{sessionObject}")
	private SessionObject sessionObject;

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

	public SessionObject getSessionObject() {
		return sessionObject;
	}

	public void setSessionObject(SessionObject sessionObject) {
		this.sessionObject = sessionObject;
	}

}
