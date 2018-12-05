package com.technicalservice.dao;

import java.util.List;

import javax.ejb.Stateless;

import com.technicalservice.dao.base.BaseDao;
import com.technicalservice.model.entity.Customer;
import com.technicalservice.model.entity.Device;

/**
 * Device nesnesi için database işlemlerinin yapıldığı sınıftır.
 * 
 * @author oguzhan
 */
@Stateless
public class DeviceDao extends BaseDao<Device> {

	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	public List<Device> listDevicesByCustomer(Customer owner) {
		return entityManager.createQuery("SELECT d FROM Device d where d.owner= :owner").setParameter("owner", owner)
				.getResultList();
	}

	/**
	 * 
	 * @param owner
	 *            Cihaz sayısı döndürülecek olan müşteri
	 * @return Kayıtlı Cihaz sayısını döndürür.
	 */
	public Long getDeviceCount(Customer owner) {

		Long deviceCount;
		StringBuilder query = new StringBuilder(" SELECT COUNT(d) FROM Device d WHERE d.status=1 ");

		if (owner == null) {
			deviceCount = (Long) entityManager.createQuery(query.toString()).getSingleResult();
		} else {
			query.append(" AND d.owner= :owner ");
			deviceCount = (Long) entityManager.createQuery(query.toString()).setParameter("owner", owner)
					.getSingleResult();
		}

		return deviceCount;

	}

}
