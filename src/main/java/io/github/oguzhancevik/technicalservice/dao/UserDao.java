package io.github.oguzhancevik.technicalservice.dao;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import io.github.oguzhancevik.technicalservice.dao.base.BaseDao;
import io.github.oguzhancevik.technicalservice.model.entity.User;

/**
 * User nesnesi için database işlemlerinin yapıldığı sınıftır.
 * 
 * @author oguzhan
 *
 */
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
	 * CriteriaBuilder ile database de girilen email ve şifre ile oluşturulan bir
	 * kullanıcı varmı yokmu kontrol edilir.
	 * 
	 * @param email
	 *            Kulanıcının giriş yapacağı email
	 * @param password
	 *            Kullanıcı şifresi
	 * @return email ve password ile kayıtlı kullanıcı var ise User nesnesini
	 *         döndürür.
	 */
	public User authenticate(String email, String password) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<User> cq = cb.createQuery(User.class);
		Root<User> root = cq.from(User.class);

		List<Predicate> conditions = new ArrayList<Predicate>();
		conditions.add(cb.equal(root.get("email"), email));
		conditions.add(cb.equal(root.get("password"), password));

		if (conditions.size() > 0) {
			Predicate[] predicates = new Predicate[conditions.size()];
			conditions.toArray(predicates);
			cq.where(predicates);
		}

		TypedQuery<User> q = entityManager.createQuery(cq);
		List<User> results = q.getResultList();

		if (results.size() > 0) {
			User user = results.get(0);
			return user;
		}
		return null;
	}

	/**
	 * Girilen iki şifreyi kontrol eder. İki şifre eşit ise ve 4 rakamdan oluşutor
	 * ise "success" döner. Eğer şifreler uyuşmuyor ise "GİRİLEN ŞİFRELER
	 * EŞLEŞMİYOR!" döner. Şifre uzunlukları 4'e eşit değil ise "GİRİLEN ŞİFRE 4
	 * RAKAMDAN OLUŞMALIDIR!" döner. Şifre rakam haricinde başka karakter içeriyor
	 * ise "GİRİLEN ŞİFRE SADECE RAKAM İÇERMELİDİR!" döner.
	 * 
	 * @param password
	 *            Kullanıcının girdiği 1. şifre.
	 * @param password2
	 *            Kullanıcının girdiği 2. şifre (Doğrulama şifresi).
	 * @see #containsAllCases(String)
	 * @return "succes", "GİRİLEN ŞİFRELER EŞLEŞMİYOR!", "GİRİLEN ŞİFRE 4 RAKAMDAN
	 *         OLUŞMALIDIR!", "GİRİLEN ŞİFRE SADECE RAKAM İÇERMELİDİR!" döner.
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

	/**
	 * @return Sisteme kayıtlı olan Admin yetkisindeki aktif kullanıcıları listeler
	 */
	@SuppressWarnings("unchecked")
	public List<User> listAdmin() {
		return entityManager.createQuery("SELECT u FROM User u WHERE u.role='Admin' AND u.status=1 ORDER BY u.id")
				.getResultList();
	}

	/**
	 * @return Kayıtlı Admin sayısını döndürür.
	 */
	public Long getAdminCount() {
		return (Long) entityManager.createQuery("SELECT COUNT(u) FROM User u WHERE u.role='Admin' AND u.status=1")
				.getSingleResult();
	}

}
