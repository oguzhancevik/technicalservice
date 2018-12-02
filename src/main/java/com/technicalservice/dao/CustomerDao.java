package com.technicalservice.dao;

import javax.ejb.Stateless;

import com.technicalservice.dao.base.BaseDao;
import com.technicalservice.model.entity.Customer;

@Stateless
public class CustomerDao extends BaseDao<Customer> {

	private static final long serialVersionUID = 1L;

	public CustomerDao() {
		super(Customer.class);
	}

}
