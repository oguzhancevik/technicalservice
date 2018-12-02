package com.technicalservice.controller;

import java.math.BigInteger;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import com.technicalservice.dao.PasswordResetRequestDao;
import com.technicalservice.dao.UserDao;
import com.technicalservice.model.entity.PasswordResetRequest;
import com.technicalservice.model.entity.User;
import com.technicalservice.resource.ExternalResource;
import com.technicalservice.util.UtilLog;

/**
 * 
 * @author oguzhan
 * 
 * @since 30.11.2018
 * 
 *        Şifre yenileme işlemlerini gerçekleştiren sınıftır.
 *
 */

@ManagedBean
@ViewScoped
public class ForgotPasswordBean {

	@EJB
	private PasswordResetRequestDao passwordResetRequestDao;

	@EJB
	private UserDao userDao;

	private String email;
	private String password;
	private String password2;
	private String f;
	PasswordResetRequest passReset;
	private boolean process = false;
	private String redirectPage;
	public static final String emailNotExist = "Bu mail adresine ait bir kayıt bulunmamaktadır.";
	public static final String emailSend = "Şifre yenileme linkiniz mailinize gönderilmiştir.";
	public static final String passwordNotEqual = "Girilen şifreler eşleşmiyor. Lütfen kontrol ediniz!";
	public static final String passwordLength = "Girilen şifre 4 rakamdan oluşmalıdır. Lütfen kontrol ediniz!";
	public static final String passwordMustContainAllCases = "Girilen şifre sadece rakam içermelidir.";
	public static final String notToken = "Hatalı İstek";
	public static final String tokenExpired = "Linkin kullanım süresi dolmuştur. Şifre talebinde bulunabileceğiniz şifre talep sayfasına yönlendiriliyorsunuz...";
	public static final String success = "Yeni şifre kaydedilmiştir. Giriş ekranına yönlendiriliyorsunuz.";
	public static final String activeTokenMsg = "Daha önce yapmış olduğunuz şifre yenileme talepleri bulunmaktadır. Lütfen daha önce gönderilen şifre yenileme talep maillerini kontrol ediniz!";

	/**
	 * Mail'de şifre sıfırlama linkine tıklanınca açılan sayfanın ilk çalışacak
	 * metodudur. Burada url'den gelen f değerini alınır.
	 * 
	 * @see #findEntityByForgotToken()
	 */
	public void init() {
		if (f == null || f.trim().equals("")) {
			f = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("f");
		}
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
				.getRequest();
		StringBuffer requestURL = request.getRequestURL();
		String requestURLStr = requestURL.toString();
		if (requestURLStr.contains("resetPassword.jsf")) {
			findEntityByForgotToken();
		}
	}

	/**
	 * Kullanıcı şifresini unuttuysa bu metod çalışacaktır. Daha önce 2'den fazla
	 * şifre yenileme istediğinde bulunduysa veya mail sistemde kayıtlı değilse
	 * uyarı verecektir. Kullanıcı 3'ten az şifre yenileme talebinde bulunmuş ve
	 * mail sistemde kayıtlı ise kullanıcının mailine şifre yenileme linki
	 * gidecektir.
	 * 
	 * @see com.technicalservice.dao.PasswordResetRequestDao#passwordTokenControl(String)
	 * @see com.technicalservice.dao.UserDao#findByEmail(String)
	 * @see #messageContent(com.technicalservice.model.entity.PasswordResetRequest)
	 * @see #createMessage(String,String)
	 */
	public void sendMailForResetPassword() {
		try {
			BigInteger activeTokenCount = passwordResetRequestDao.passwordTokenControl(email);
			if (activeTokenCount.intValue() >= 3) {
				createMessage("HATA", activeTokenMsg);
			} else {
				PasswordResetRequest passReset = new PasswordResetRequest();
				User user = userDao.findByEmail(email);
				if (user == null || user.getEmail() == null) {
					createMessage("HATA!", emailNotExist);
					return;
				} else {
					UtilLog.log("user:" + user.getEmail());
					passReset.setUser(user);
					passwordResetRequestDao.persist(passReset);
				}
				String[] to = { user.getEmail() };
				MailSender.mailSend(null, null, "TECHNICAL SERVICE", to, "SIFRE YENILEME TALEBI",
						messageContent(passReset));
				UtilLog.log("Password mail send OK");
				createMessage("İşlem Başarılı!", emailSend);

			}
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("BAŞARISIZ",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "HATA", "Mail gönderilemedi"));
			UtilLog.log("Password mail send HATALI");
			e.printStackTrace();
		}
	}

	/**
	 * Şifre sıfırlama ekranında kullanıcı şifresini girip gönderdiği zaman bu metod
	 * çalışır. Şifre 4 rakamdan az ise ve uyuşmuyor ise hata verecektir. Eğer
	 * şifreler uygunsa ve istenilen formatta ise şifre kaydediliyor.
	 * 
	 * @see com.technicalservice.dao.UserDao#changePassword(String, String, User)
	 */
	public void resetPassword() {
		try {
			UtilLog.log("resetPassword:f:" + f);
			String message = userDao.changePassword(password, password2, passReset.getUser());
			if (message.equals("success")) {
				passReset.setStatus(0);
				passwordResetRequestDao.merge(passReset);
				UtilLog.logToScreen(FacesMessage.SEVERITY_INFO, "İşlem Başarılı", success);
				process = true;
				redirectPage = "success";
			} else {
				UtilLog.logToScreen(FacesMessage.SEVERITY_ERROR, "HATA!", message);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * URL'den gelen f bilgisi ile PasswordResetRequest modelini doldurur.
	 * PasswordResetRequest modelinin status değeri 0 ve null değilse geçerliliği
	 * bitti uyarısı verir. Eğer model null ise böyle bir istek yok diye uyarı
	 * verir.
	 * 
	 * @see #createMessage(String, String)
	 */
	public void findEntityByForgotToken() {
		try {
			if (process == false) {
				if (f != null) {
					passReset = new PasswordResetRequest();
					passReset = passwordResetRequestDao.findById(new Long(f));
					if (passReset != null && passReset.getStatus() == 0) {
						createMessage("Uyarı", tokenExpired);
						redirectPage = "forgotPassword";
					}
				} else {
					createMessage("Hata!", notToken);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Ekrana ilgili mesajı gösterir.
	 * 
	 * @param messageHeader
	 *            ekranda gösterilecek mesaj başlığı
	 * @param messageText
	 *            ekranda gösterilecek mesaj içeriği
	 */
	public void createMessage(String messageHeader, String messageText) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(messageHeader, messageText));
	}

	/**
	 * @param passReset
	 *            şifre yenileme isteği için kullanıcının hangi şifre yenileme id'si
	 *            ile olduğu alınır.
	 * @see com.technicalservice.resource.ExternalResource#getInstance(String)
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

	public PasswordResetRequestDao getPasswordResetRequestDao() {
		return passwordResetRequestDao;
	}

	public void setPasswordResetRequestDao(PasswordResetRequestDao passwordResetRequestDao) {
		this.passwordResetRequestDao = passwordResetRequestDao;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword2() {
		return password2;
	}

	public void setPassword2(String password2) {
		this.password2 = password2;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getF() {
		return f;
	}

	public void setF(String f) {
		this.f = f;
	}

	public static String getNottoken() {
		return notToken;
	}

	public static String getTokenexpired() {
		return tokenExpired;
	}

	public static String getPasswordnotequal() {
		return passwordNotEqual;
	}

	public static String getSuccess() {
		return success;
	}

	public static String getEmailnotexist() {
		return emailNotExist;
	}

	public static String getEmailsend() {
		return emailSend;
	}

	public boolean isProcess() {
		return process;
	}

	public void setProcess(boolean process) {
		this.process = process;
	}

	public String getRedirectPage() {
		return redirectPage;
	}

	public void setRedirectPage(String redirectPage) {
		this.redirectPage = redirectPage;
	}

}
