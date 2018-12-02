package com.technicalservice.dao.base;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.technicalservice.model.base.ExtendedModel;

@Stateless
public abstract class BaseDao<T extends ExtendedModel> implements Serializable {

	private static final long serialVersionUID = 1L;

	public BaseDao() {
		super();
	}

	@PersistenceContext
	protected EntityManager entityManager;

	private Class<T> entityClass;

	public BaseDao(Class<T> entityClass) {
		this.entityClass = entityClass;
	}

	/**
	 * Model ilk kez kayıt edilir.
	 * 
	 * @param entity
	 *            Kayıt edilecek model.
	 * @return Kaydedilen model geri göner.
	 * @throws Exception
	 */
	public T persist(T entity) throws Exception {
		entityManager.persist(entity);
		return entity;
	}

	/**
	 * Var olan model güncellenir.
	 * 
	 * @param entity
	 *            Güncellenecek model.
	 * @return Güncellenen model geri göner.
	 */
	public T merge(T entity) {
		entity = entityManager.merge(entity);
		return entity;
	}

	/**
	 * Bu sınıftan türeyen diğer alt sınıfların hangi modelde ise (ExtendedModel'den
	 * türemiş olan herhangi bir class) ona göre kaydeder. Modelin Id'si dolu ise
	 * üzerine yazar(günceller). Id boş ise yeni kayıt yapır.
	 * 
	 * @param entity
	 *            Kayıt edilecek model.
	 * @return Kaydedilen model geri göner.
	 * @throws Exception
	 */
	public T save(T entity) throws Exception {
		if (entity.getId() == null) {
			return persist(entity);
		} else {
			return merge(entity);
		}
	}

	/**
	 * Var olan modeli siler.
	 * 
	 * @param entity
	 *            Silinecek model.
	 * @throws Exception
	 */
	public void remove(T entity) throws Exception {
		if (!entityManager.contains(entity)) {
			entity = entityManager.merge(entity);
		}
		entityManager.remove(entity);
	}

	/**
	 * Var olan modeli pasif eder.
	 * 
	 * @param entity
	 *            Pasif edilecek model
	 * @throws Exception
	 */
	public void passive(T entity) throws Exception {
		entity.setStatus(0);
		entityManager.merge(entity);
	}

	/**
	 * T tipindeki modele göre status=1 olan (pasif olmayan) kayıtları döner.
	 * 
	 * @return T tipinde liste döner.
	 */
	@SuppressWarnings("unchecked")
	public List<T> listAll() {
		return entityManager.createQuery("FROM " + entityClass.getSimpleName() + "  e WHERE e.status = 1 ORDER BY e.id")
				.getResultList();
	}

	/**
	 * T tipindeki modeli id değerine göre bulur.
	 * 
	 * @param id
	 *            modeli bulmak için gerekli olan id
	 * @return T modelini döndürür.
	 */
	public T findById(Object id) {
		return entityManager.find(getClassType(), id);
	}

	/** @return T modelinin class tipini döndürür. */
	@SuppressWarnings("unchecked")
	private Class<T> getClassType() {
		ParameterizedType parameterizedType = (ParameterizedType) getClass().getGenericSuperclass();
		return (Class<T>) parameterizedType.getActualTypeArguments()[0];
	}

}
