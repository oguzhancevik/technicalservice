package com.technicalservice.rest;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.technicalservice.dao.CustomerDao;
import com.technicalservice.dao.DeviceDao;
import com.technicalservice.dao.UserDao;
import com.technicalservice.model.entity.Customer;
import com.technicalservice.model.entity.Device;
import com.technicalservice.model.pojo.Result;
import com.technicalservice.model.rest.DeviceRest;
import com.technicalservice.util.UtilLog;

/**
 * Rest servis ile cihaz işlemleri yapılan sınıftır.
 * 
 * @author oguzhan
 */
@Stateless
@Path("/device")
public class DeviceEndpoint {

	@EJB
	private UserDao userDao;

	@EJB
	private CustomerDao customerDao;

	@EJB
	private DeviceDao deviceDao;

	/**
	 * Url:http://localhost:8080/technicalservice/rest/device/create
	 * 
	 * @param deviceRest
	 *            Kayıt edilecek cihaz
	 * @return Cihazın kayıt edilip edilmediği bilgisini döndürür.
	 * @throws Exception
	 */
	@POST
	@Path("/create")
	@Consumes(MediaType.APPLICATION_JSON + "; charset=UTF-8")
	@Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
	public Response registerCustomer(DeviceRest deviceRest) throws Exception {

		Result result = new Result();
		try {
			Customer customer = customerDao.findByUser(userDao.findByEmail(deviceRest.getOwnerEmail()));
			Device device = new Device(customer);
			device.setBrand(deviceRest.getBrand());
			device.setModel(deviceRest.getModel());
			device.setDeviceType(deviceRest.getDeviceType());
			device.setGuaranteePeriod(deviceRest.getGuaranteePeriod());
			device.setColor(deviceRest.getColor());
			device.setHeight(deviceRest.getHeight());
			device.setWidth(deviceRest.getWidth());
			device.setKg(deviceRest.getKg());
			deviceDao.save(device);
			result.setResult(true);
			result.setMessage("Cihaz kayıt edildi!");
		} catch (Exception e) {
			UtilLog.log(e);
			result.setResult(false);
			result.setMessage("Cihaz kayıt edilemedi!");
		}
		return Response.ok(result).build();
	}

	/**
	 * Url: http://localhost:8080/technicalservice/rest/device/list/test@gmail.com
	 * @param email Cihazları listelenecek olan Customer maili
	 * @return cihaz listesi döner
	 */
	@GET
	@Path("/list/{email}")
	@Produces("application/json; charset=UTF-8")
	public Response login(@PathParam("email") String email) {
		List<Device> devices = new ArrayList<>();
		try {
			Customer customer = customerDao.findByUser(userDao.findByEmail(email));
			devices = deviceDao.listDevicesByCustomer(customer);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.ok(devices).build();
	}

}
