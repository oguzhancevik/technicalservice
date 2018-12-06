package com.technicalservice.rest;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.technicalservice.dao.CustomerDao;
import com.technicalservice.dao.DeviceDao;
import com.technicalservice.dao.IssueDao;
import com.technicalservice.dao.UserDao;
import com.technicalservice.model.entity.Customer;
import com.technicalservice.model.entity.User;
import com.technicalservice.model.type.IssueStatu;

/**
 * Rest servis ile cihaz işlemleri yapılan sınıftır.
 * 
 * @author oguzhan
 */
@Stateless
@Path("/dashboard")
public class DashboardEndpoint {

	@EJB
	private UserDao userDao;

	@EJB
	private CustomerDao customerDao;

	@EJB
	private DeviceDao deviceDao;

	@EJB
	private IssueDao issueDao;

	/**
	 * Url: http://localhost:8080/technicalservice/rest/dashboard/getCustomerCount
	 * 
	 * @return Müşteri sayısını döndürür.
	 */
	@GET
	@Path("/getCustomerCount")
	@Produces("application/json; charset=UTF-8")
	public Response getCustomerCount() {
		Long customerCount = 0L;
		try {
			customerCount = customerDao.getCustomerCount();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.ok(customerCount).build();
	}

	/**
	 * Url: http://localhost:8080/technicalservice/rest/dashboard/getAdminCount
	 * 
	 * @return Admin sayısını döndürür.
	 */
	@GET
	@Path("/getAdminCount")
	@Produces("application/json; charset=UTF-8")
	public Response getAdminCount() {
		Long adminCount = 0L;
		try {
			adminCount = userDao.getAdminCount();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.ok(adminCount).build();
	}

	/**
	 * Url: http://localhost:8080/technicalservice/rest/dashboard/getDeviceCount
	 * 
	 * @return Sistemde kayıtlı cihazların sayısını döndürür
	 */
	@GET
	@Path("/getDeviceCount")
	@Produces("application/json; charset=UTF-8")
	public Response getDeviceCount() {
		Long deviceCount = 0L;
		try {
			deviceCount = deviceDao.getDeviceCount(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.ok(deviceCount).build();
	}

	/**
	 * Url:
	 * http://localhost:8080/technicalservice/rest/dashboard/getDeviceCount/mail@gmail.com
	 * 
	 * @param email
	 *            cihaz sayısı döndürülecek olan müşteri maili
	 * @return Cihaz sayısını döndürür
	 */
	@GET
	@Path("/getDeviceCount/{email}")
	@Produces("application/json; charset=UTF-8")
	public Response getCustomerDeviceCount(@PathParam("email") String email) {
		Long deviceCount = 0L;
		try {
			Customer customer = customerDao.findByUser(userDao.findByEmail(email));
			deviceCount = deviceDao.getDeviceCount(customer);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.ok(deviceCount).build();
	}

	/**
	 * Url1: http://localhost:8080/technicalservice/rest/dashboard/getIssueCount
	 * 
	 * Url2: http://localhost:8080/technicalservice/rest/dashboard/getIssueCount?deviceOwner=mail@gmail.com
	 * 
	 * Url3: http://localhost:8080/technicalservice/rest/dashboard/getIssueCount?issueStatu=REPAIR
	 * 
	 * Url4:
	 * http://localhost:8080/technicalservice/rest/dashboard/getIssueCount?deviceOwner=mail@gmail.com&issueStatu=REPAIR
	 * 
	 * @return Sistemde kayıtlı olan Bakım / Onarım sayısını döndürür.
	 */
	@GET
	@Path("/getIssueCount")
	@Produces("application/json; charset=UTF-8")
	public Response getIssueCount(@Context UriInfo uriInfo) {
		Long issueCount = 0L;
		try {

			String deviceOwner = uriInfo.getQueryParameters().getFirst("deviceOwner");
			String issueStatu = uriInfo.getQueryParameters().getFirst("issueStatu");

			Customer customer = null;
			IssueStatu is = null;
			if (deviceOwner != null) {
				customer = new Customer(new User());
				customer = customerDao.findByUser(userDao.findByEmail(deviceOwner));
			}
			if (issueStatu != null) {
				is = IssueStatu.valueOf(issueStatu);
			}

			issueCount = issueDao.getIssueCount(customer, is);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.ok(issueCount).build();
	}

}
