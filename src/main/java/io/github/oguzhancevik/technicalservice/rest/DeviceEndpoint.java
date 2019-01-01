package io.github.oguzhancevik.technicalservice.rest;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import io.github.oguzhancevik.technicalservice.dao.CustomerDao;
import io.github.oguzhancevik.technicalservice.dao.DeviceDao;
import io.github.oguzhancevik.technicalservice.dao.UserDao;
import io.github.oguzhancevik.technicalservice.model.entity.Customer;
import io.github.oguzhancevik.technicalservice.model.entity.Device;
import io.github.oguzhancevik.technicalservice.model.pojo.Result;
import io.github.oguzhancevik.technicalservice.model.rest.DeviceRest;
import io.github.oguzhancevik.technicalservice.util.UtilLog;

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
	public Response create(DeviceRest deviceRest) throws Exception {

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
	 * 
	 * @param email
	 *            Cihazları listelenecek olan Customer maili
	 * @return cihaz listesi döner
	 */
	@GET
	@Path("/list/{email}")
	@Consumes(MediaType.APPLICATION_JSON + "; charset=UTF-8")
	@Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
	public Response list(@PathParam("email") String email) {
		List<Device> devices = new ArrayList<>();
		try {
			Customer customer = customerDao.findByUser(userDao.findByEmail(email));
			devices = deviceDao.listDevicesByCustomer(customer);
		} catch (Exception e) {
			UtilLog.log(e);
		}
		return Response.ok(devices).build();
	}

	/**
	 * Url: http://localhost:8080/technicalservice/rest/device/update
	 * 
	 * @param deviceRest
	 *            güncellenecek olan cihaz.
	 * @return cihazın güncellenip güncellenmediği bilgisini döndürür.
	 */
	@PUT
	@Path("/update")
	@Consumes(MediaType.APPLICATION_JSON + "; charset=UTF-8")
	@Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
	public Response update(DeviceRest deviceRest) {
		Result result = new Result();

		try {
			Device device = deviceDao.findById(deviceRest.getId());
			device.setBrand(deviceRest.getBrand());
			device.setModel(deviceRest.getModel());
			device.setDeviceType(deviceRest.getDeviceType());
			device.setGuaranteePeriod(deviceRest.getGuaranteePeriod());
			device.setColor(deviceRest.getColor());
			device.setHeight(deviceRest.getHeight());
			device.setWidth(device.getWidth());
			device.setKg(deviceRest.getKg());

			deviceDao.save(device);

			result.setResult(true);
			result.setMessage("Cihaz güncellendi!");
		} catch (Exception e) {
			UtilLog.log(e);
			result.setResult(false);
			result.setMessage("Cihaz güncellenemedi!");
		}

		return Response.ok(result).build();
	}

	/**
	 * Url: http://localhost:8080/technicalservice/rest/device/remove
	 * @param deviceRest
	 *            silinecek olan cihaz
	 * @return cihazın silinip silinmediği bilgisini döner.
	 */
	@DELETE
	@Path("/remove")
	@Consumes(MediaType.APPLICATION_JSON + "; charset=UTF-8")
	@Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
	public Response remove(DeviceRest deviceRest) {
		Result result = new Result();
		try {

			Device device = deviceDao.findById(deviceRest.getId());
			deviceDao.passive(device);

			result.setResult(true);
			result.setMessage("Cihaz silindi!");
		} catch (Exception e) {
			UtilLog.log(e);
			result.setResult(true);
			result.setMessage("Cihaz silinemedi!");
		}
		return Response.ok(result).build();
	}

}
