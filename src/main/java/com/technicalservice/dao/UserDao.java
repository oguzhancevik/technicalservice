package com.technicalservice.dao;

import java.util.List;

import javax.ejb.Stateless;

import com.technicalservice.dao.base.BaseDao;
import com.technicalservice.model.entity.User;

@Stateless
public class UserDao extends BaseDao<User> {

	private static final long serialVersionUID = 1L;

	public UserDao() {
		super(User.class);
	}

	private static final String passwordNotEqual = "GİRİLEN ŞİFRELER EŞLEŞMİYOR!";
	private static final String passwordLength = "GİRİLEN ŞİFRE 4 RAKAMDAN OLUŞMALIDIR!";
	private static final String passwordMustContainAllCases = "GİRİLEN ŞİFRE SADECE RAKAM İÇERMELİDİR!";

	/**
	 * Girilen iki şifreyi kontrol eder. İki şifre eşit ise ve 4 rakamdan oluşutor
	 * ise "success" döner. Eğer şifreler uyuşmuyor ise "GİRİLEN ŞİFRELER EŞLEŞMİYOR!"
	 * döner. Şifre uzunlukları 4'e eşit değil ise "GİRİLEN ŞİFRE 4 RAKAMDAN OLUŞMALIDIR!" döner.
	 * Şifre rakam haricinde başka karakter içeriyor ise
	 * "GİRİLEN ŞİFRE SADECE RAKAM İÇERMELİDİR!" döner.
	 * 
	 * @param password
	 *            Kullanıcının girdiği 1. şifre.
	 * @param password2
	 *            Kullanıcının girdiği 2. şifre (Doğrulama şifresi).
	 * @see #containsAllCases(String)
	 * @return "succes", "GİRİLEN ŞİFRELER EŞLEŞMİYOR!", "GİRİLEN ŞİFRE 4 RAKAMDAN OLUŞMALIDIR!",
	 *        "GİRİLEN ŞİFRE SADECE RAKAM İÇERMELİDİR!" döner.
	 */
	public String controlPassword(String password, String password2) {
		if (password != null && password2 != null) {
			if (!password.equals(password2)) {
				return passwordNotEqual;
			}
			if (password.length() != 4) {
				return passwordLength;
			}
			if (!containsAllCases(password)) {
				return passwordMustContainAllCases;
			}
		}

		return "success";
	}

	/**
	 * 
	 * @param str
	 *            İşlem yapılacak String ifade
	 * @return str içinde rakam haricinde bir karakter içeriyor ise false içermiyor
	 *         ise true döner
	 */
	private boolean containsAllCases(String str) {
		for (int i = 0; i < str.length(); i++) {
			char ch = str.charAt(i);

			if (!Character.isDigit(ch)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Kullanıcı mail adresine göre User nesnesini döndürür.
	 * 
	 * @param email
	 *            User nesnesini bulmak için kullanıcı mailidir.
	 * @return User nesnesi döndürür.
	 */
	@SuppressWarnings("unchecked")
	public User findByEmail(String email) {
		User user;
		List<User> listUser = entityManager.createQuery("SELECT DISTINCT u FROM User u WHERE u.email = :email")
				.setParameter("email", email).getResultList();
		if (listUser.size() > 0) {
			user = listUser.get(0);
		} else {
			user = null;
		}
		return user;
	}

	/**
	 * Kullanıcının belirlediği şifre istenen formata uygunsa ise şifreyi
	 * değiştirir.
	 * 
	 * @param password
	 *            Kullanıcının girdiği 1. şifre.
	 * @param password2
	 *            Kullanıcının girdiği 2. şifre (Doğrulama şifresi).
	 * @param user
	 *            Şifre değişriecek olan kullanıcı.
	 * @see #controlPassword(String, String)
	 * @return şifre değiştirildi, şifreler eşit değil yafa şifre 4 rakamdan
	 *         oluşmalıdır gibi uyarı mesajı döner
	 */
	public String changePassword(String password, String password2, User user) {
		String message = controlPassword(password, password2);
		if (message.equals("success")) {
			user.setPassword(password);
			merge(user);
		}
		return message;
	}

}
