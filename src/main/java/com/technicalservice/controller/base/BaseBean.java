package com.technicalservice.controller.base;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;

import com.technicalservice.controller.SessionObject;
import com.technicalservice.controller.TypeBean;
import com.technicalservice.dao.base.BaseDao;
import com.technicalservice.model.base.ExtendedModel;
import com.technicalservice.util.UtilLog;

/***
 * Her controller sınıfının ihtiyaç duyacağı işlemleri yapan soyut sınıftır.
 * 
 * @author oguzhan
 *
 * @param <T>
 *            ExtendedModel'den türeyen herhangi bir sınıf.
 */
public abstract class BaseBean<T extends ExtendedModel> implements Serializable {

	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{sessionObject}")
	private SessionObject sessionObject;

	@ManagedProperty(value = "#{typeBean}")
	private TypeBean typeBean;

	private List<T> listModel;

	private T selectedModel;

	/**
	 * Beasebean'den türeyen her sınıfın çağrıldığı zaman ilk çalışacağı metoddur.
	 * Flash ile gelen bir obje var ise selectedModel nesnesine aktarılır. Yok ise
	 * selectedModel türünde yeni bir instance oluşturulur. T tipinde listModel
	 * listesi ise {@link #listInitial()} metodundan dönen listeye atanır.
	 * 
	 * @see #listInitial()
	 */
	@SuppressWarnings("unchecked")
	@PostConstruct
	public void init() {
		try {
			Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
			if (flash.containsKey("model")) {
				selectedModel = (T) flash.get("model");
			} else {
				selectedModel = getClassType().newInstance();
			}

			listModel = listInitial();
		} catch (Exception e) {
			UtilLog.logToScreen(e);
		}
	}

	/**
	 * @return BaseBean'den türeyen her sınıfın implement edeceği bu method T
	 *         tipinde liste döner.
	 */
	public abstract List<T> listInitial();

	/**
	 * Database kayıt işlemi gerçekleşmiş ise sayfa yönlendirme işlemi
	 * gerçekleştirilir.
	 * 
	 * @param page
	 *            yönlendirilecek sayfa adresi.
	 * @see #save()
	 * @throws IOException
	 */
	public void save(String page) throws IOException {
		Boolean returnValue = save();
		if (returnValue.equals(true)) {
			FacesContext.getCurrentInstance().getExternalContext().redirect(page);
		}
	}

	/**
	 * @see com.technicalservice.dao.base.BaseDao#save(ExtendedModel)
	 * @return selectedModel nesnesi kaydedilirse true kaydedilmemiş ise false
	 *         değeri döndürür.
	 */
	public Boolean save() {
		try {
			getBaseDao().save(selectedModel);
			FacesMessage mesaj1 = new FacesMessage("Kayıt İşlemi Tamamlandı", "");
			FacesContext.getCurrentInstance().addMessage("", mesaj1);
			return true;
		} catch (Exception e) {
			UtilLog.log(e);
			return false;
		}

	}

	/**
	 * T tipindeki nesneyi silmek, pasife çekmek (statusu 0 yapılır) için kullanılan
	 * metoddur.
	 * 
	 * @see com.technicalservice.dao.base.BaseDao#passive(ExtendedModel)
	 * @see #init()
	 * @param removeModel
	 *            silinicek (pasife çekilecek) olan nesne.
	 */
	public void remove(T removeModel) {
		try {
			getBaseDao().passive(removeModel);
			init();
		} catch (Exception e) {
			UtilLog.logToScreen(e);
		}
	}

	/**
	 * T tipindeki nesnenin page adresindeki sayfaya geçirilmesi için kullanılan
	 * metoddur.
	 * 
	 * @param updateModel
	 *            Flash ile gönderilecek olan nesne.
	 * @param page
	 *            yönlendirilecek sayfa adresi.
	 */
	public void updatePage(T updateModel, String page) {
		try {
			Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
			flash.put("model", updateModel);

			FacesContext.getCurrentInstance().getExternalContext().redirect(page);
		} catch (Exception e) {
			UtilLog.logToScreen(e);
		}
	}

	/**
	 * @return sınıfın tipini döndürür. T tipinden extend eden herhangi bir sınıfın
	 *         tipini döner.
	 */
	@SuppressWarnings("unchecked")
	private Class<T> getClassType() {
		ParameterizedType parameterizedType = (ParameterizedType) getClass().getGenericSuperclass();
		return (Class<T>) parameterizedType.getActualTypeArguments()[0];
	}

	/**
	 * @return T tipindeki nesnenin dao sınıfını döndürür.
	 */
	public abstract BaseDao<T> getBaseDao();

	/**
	 * page adresindeki sayfaya yönlendirmek için kullanılan metoddur.
	 * 
	 * @param page
	 *            yönlendirilecek sayfa adresi.
	 */
	public void redirectNew(String page) {
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect(page);
		} catch (IOException e) {
			UtilLog.logToScreen(e);
		}
	}

	/**
	 * selectedModel nesnesini page sayfasına aktarmak için kullanılan metoddur.
	 * 
	 * @param page
	 *            yönlendirilecek sayfa adresi.
	 */
	public void redirectUpdate(String page) {
		try {
			Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
			flash.put("model", selectedModel);
			FacesContext.getCurrentInstance().getExternalContext().redirect(page);
		} catch (IOException e) {
			UtilLog.logToScreen(e);
		}
	}

	public SessionObject getSessionObject() {
		return sessionObject;
	}

	public void setSessionObject(SessionObject sessionObject) {
		this.sessionObject = sessionObject;
	}

	public TypeBean getTypeBean() {
		return typeBean;
	}

	public void setTypeBean(TypeBean typeBean) {
		this.typeBean = typeBean;
	}

	public List<T> getListModel() {
		return listModel;
	}

	public void setListModel(List<T> listModel) {
		this.listModel = listModel;
	}

	public T getSelectedModel() {
		return selectedModel;
	}

	public void setSelectedModel(T selectedModel) {
		this.selectedModel = selectedModel;
	}

}
