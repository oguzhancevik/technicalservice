package com.technicalservice.rest;

import java.math.BigInteger;

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

import com.technicalservice.controller.MailSender;
import com.technicalservice.dao.CustomerDao;
import com.technicalservice.dao.PasswordResetRequestDao;
import com.technicalservice.dao.UserDao;
import com.technicalservice.model.entity.Customer;
import com.technicalservice.model.entity.PasswordResetRequest;
import com.technicalservice.model.entity.User;
import com.technicalservice.model.pojo.Result;
import com.technicalservice.model.rest.UserRest;
import com.technicalservice.model.type.MemberStatu;
import com.technicalservice.resource.ExternalResource;
import com.technicalservice.util.UtilLog;

@Stateless
@Path("/user")
public class UserEndpoint {

	@EJB
	private CustomerDao customerDao;

	@EJB
	private UserDao userDao;

	@EJB
	private PasswordResetRequestDao passwordResetRequestDao;

	/**
	 * Url: http://localhost:8080/technicalservice/rest/user/register/customer
	 * 
	 * @param userRest
	 *            Kaydedilecek User
	 * @return Kaydedilip kaydedilmediği bilgisi
	 *         {@link com.technicalservice.model.pojo.Result} döner.
	 * @throws Exception
	 */
	@POST
	@Path("/register/customer")
	@Consumes(MediaType.APPLICATION_JSON + "; charset=UTF-8")
	@Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
	public Response registerCustomer(UserRest userRest) throws Exception {

		Result result = new Result();
		try {

			if (userDao.findByEmail(userRest.getEmail()) != null) {
				result.setResult(false);
				result.setMessage("Email zaten kayıtlı!");
			} else {
				User user = new User();
				user.setEmail(userRest.getEmail());
				user.setPassword(userRest.getPassword());
				user.setRole("Customer");
				user.setMemberStatu(MemberStatu.WAITING);

				Customer customer = new Customer(user);
				customer.setName(userRest.getName());
				customer.setSurname(userRest.getSurname());
				customer.setMobileNo(userRest.getMobileNo());
				customerDao.save(customer);
				result.setResult(true);
				result.setMessage("Kullanıcı kayıt oldu!");
			}

		} catch (Exception e) {
			UtilLog.log(e);
			result.setResult(false);
			result.setMessage("Kullanıcı kayıt edilemedi!");
		}
		return Response.ok(result).build();
	}

	/**
	 * Url: http://localhost:8080/technicalservice/rest/user/register/admin
	 * 
	 * @param userRest
	 *            Kaydedilecek Admin
	 * @return Kaydedilip kaydedilmediği bilgisi
	 *         {@link com.technicalservice.model.pojo.Result} döner.
	 * @throws Exception
	 */
	@POST
	@Path("/register/admin")
	@Consumes(MediaType.APPLICATION_JSON + "; charset=UTF-8")
	@Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
	public Response registerAdmin(UserRest userRest) throws Exception {

		Result result = new Result();
		try {

			if (userDao.findByEmail(userRest.getEmail()) != null) {
				result.setResult(false);
				result.setMessage("Email zaten kayıtlı!");
			} else {
				User user = new User();
				user.setEmail(userRest.getEmail());
				user.setPassword(userRest.getPassword());
				user.setRole("Admin");
				user.setMemberStatu(MemberStatu.WAITING);

				userDao.save(user);
				result.setResult(true);
				result.setMessage("Admin kayıt oldu!");
			}

		} catch (Exception e) {
			UtilLog.log(e);
			result.setResult(false);
			result.setMessage("Admin kayıt edilemedi!");
		}
		return Response.ok(result).build();
	}

	/**
	 * Url:
	 * http://localhost:8080/technicalservice/rest/user/login/test@gmail.com/1234
	 * 
	 * @param email
	 *            Giriş yapılacak kullanıcı adı
	 * @param password
	 *            Giriş yapan kullanıcı şifresi
	 * @return Sisteme login olup olmadığı bilgisini
	 *         {@link com.technicalservice.model.pojo.Result} döndürür.
	 */
	@GET
	@Path("/login/{email}/{password}")
	@Produces("application/json; charset=UTF-8")
	public Response login(@PathParam("email") String email, @PathParam("password") String password) {
		Result result = new Result();
		User user = userDao.authenticate(email, password);
		if (user == null) {
			result.setResult(false);
			result.setMessage("Yanlış Kullanıcı Adı veya Şifre");
		} else {

			result.setResult(true);
			result.setMessage(user.getMemberStatu().getDisplayName());

		}
		return Response.ok(result).build();
	}

	/**
	 * Url:
	 * http://localhost:8080/technicalservice/rest/user/sendMailForResetPassword
	 * Şifre hatırlatma maili gönderir.
	 * 
	 * @param userRest
	 *            Sisteme giriş yapan kullanıcı
	 * @return şifre hatırlatma maili gidip gitmediğini döndürür.
	 */
	@POST
	@Path("/sendMailForResetPassword")
	@Consumes(MediaType.APPLICATION_JSON + "; charset=UTF-8")
	@Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
	public Response sendMailForResetPassword(UserRest userRest) {

		String activeTokenMsg = "Daha önce yapmış olduğunuz şifre yenileme talepleri bulunmaktadır. Lütfen daha önce gönderilen şifre yenileme talep maillerini kontrol ediniz!";
		String emailNotExist = "Bu mail adresine ait bir kayıt bulunmamaktadır.";
		String emailSend = "Şifre yenileme linkiniz mailinize gönderilmiştir.";

		Result result = new Result();

		try {
			BigInteger activeTokenCount = passwordResetRequestDao.passwordTokenControl(userRest.getEmail());
			if (activeTokenCount.intValue() >= 3) {
				result.setResult(false);
				result.setMessage(activeTokenMsg);
			} else {
				PasswordResetRequest passReset = new PasswordResetRequest();
				User user = userDao.findByEmail(userRest.getEmail());
				if (user == null || user.getEmail() == null) {
					result.setResult(false);
					result.setMessage(emailNotExist);
				} else {
					UtilLog.log("user:" + user.getEmail());
					passReset.setUser(user);
					passwordResetRequestDao.persist(passReset);
				}
				String[] to = { user.getEmail() };
				MailSender.mailSend(null, null, "TECHNICAL SERVICE", to, "SIFRE YENILEME TALEBI",
						messageContent(passReset));
				result.setResult(true);
				result.setMessage(emailSend);

			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setResult(false);
			result.setMessage("Mail gönderilemedi");
		}

		return Response.ok(result).build();
	}

	/**
	 * @param passReset
	 *            şifre yenileme isteği için kullanıcının hangi şifre yenileme id'si
	 *            ile olduğu alınır.
	 * @return Şifre yenileme mail'i gönderilirken mail'in içeriğini döndürür.
	 */
	public String messageContent(PasswordResetRequest passReset) {
		String urlForForgotPass = ExternalResource.getInstance("mail").getProperty("urlForForgotPass");
		if (urlForForgotPass == null || urlForForgotPass.trim().length() < 1) {
			urlForForgotPass = "localhost:8080";
		}
		String messageText = "Şifre değişikliğinizin aktif olması için aşağıdaki linke tıklayınız :<br/>" + "<a href='"
				+ urlForForgotPass + "/resetPassword.jsf?f=" + passReset.getId() + "'>" + urlForForgotPass
				+ "/resetPassword.jsf?f=" + passReset.getId() + "</a>";
		return messageText;
	}

	/**
	 * Url: http://localhost:8080/technicalservice/rest/user/changePassword
	 * 
	 * @param userRest
	 *            Şifre değişikliği yapmak isteyen kullanıcı
	 * @return Şifrenin değiştirilip değiştirilmediği bilgisini döndürür.
	 */
	@PUT
	@Path("/changePassword")
	@Consumes(MediaType.APPLICATION_JSON + "; charset=UTF-8")
	@Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
	public Response changePassword(UserRest userRest) {
		Result result = new Result();

		try {
			User user = userDao.findByEmail(userRest.getEmail());
			user.setPassword(userRest.getPassword());
			userDao.save(user);
			result.setResult(true);
			result.setMessage("Şifre Değiştirildi");
		} catch (Exception e) {
			e.printStackTrace();
			result.setResult(false);
			result.setMessage("Şifre değiştirilemedi!");
		}

		return Response.ok(result).build();
	}

	/**
	 * Url: http://localhost:8080/technicalservice/rest/user/removeUser
	 * 
	 * @param userRest
	 *            Sistemden silinecek olan kullanıcı
	 * @return Silinip silinmediği bilgisini geri döner
	 */
	@DELETE
	@Path("/removeUser")
	@Consumes(MediaType.APPLICATION_JSON + "; charset=UTF-8")
	@Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
	public Response removeUser(UserRest userRest) {
		Result result = new Result();
		try {
			User user = userDao.findByEmail(userRest.getEmail());
			Customer customer = customerDao.findByUser(user);
			customerDao.remove(customer);
			result.setResult(true);
			result.setMessage("Kullanıcı silindi!");
		} catch (Exception e) {
			e.printStackTrace();
			result.setResult(true);
			result.setMessage("Kullanıcı silindi!");
		}
		return Response.ok(result).build();
	}

	/**
	 * Url: http://localhost:8080/technicalservice/rest/user/changeMemberStatu
	 * 
	 * @param userRest
	 *            üyelik statusu değiştirilecek olan kullanıcı
	 * @return Üyelik statusunun değiştirilip değiştirilmediği bilgisini döndürür.
	 */
	@PUT
	@Path("/changeMemberStatu")
	@Consumes(MediaType.APPLICATION_JSON + "; charset=UTF-8")
	@Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
	public Response changeMemberStatu(UserRest userRest) {
		Result result = new Result();

		try {
			User user = userDao.findByEmail(userRest.getEmail());
			user.setMemberStatu(MemberStatu.valueOf(userRest.getMemberStatu()));
			userDao.save(user);
			result.setResult(true);
			result.setMessage("Üyelik statusu değiştirildi");
		} catch (Exception e) {
			e.printStackTrace();
			result.setResult(false);
			result.setMessage("Üyelik statusu değiştirilemedi!");
		}

		return Response.ok(result).build();
	}

}
