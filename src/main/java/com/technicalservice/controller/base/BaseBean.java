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

public abstract class BaseBean<T extends ExtendedModel> implements Serializable {

	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{sessionObject}")
	private SessionObject sessionObject;

	@ManagedProperty(value = "#{typeBean}")
	private TypeBean typeBean;

	private List<T> listModel;

	private T selectedModel;

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

	public abstract List<T> listInitial();

	public void save(String page) throws IOException {
		Boolean returnValue = save();
		if (returnValue.equals(true)) {
			FacesContext.getCurrentInstance().getExternalContext().redirect(page);
		}
	}

	public Boolean save() {
		try {
			getBaseDao().save(selectedModel);
			FacesMessage mesaj1 = new FacesMessage("Kayıt İşlemi Tamamlandı", "");
			FacesContext.getCurrentInstance().addMessage("", mesaj1);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	public void remove(T removeModel) {
		try {
			getBaseDao().passive(removeModel);
			init();
		} catch (Exception e) {
			UtilLog.logToScreen(e);
		}
	}

	public void updatePage(T updateModel, String page) {
		try {
			Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
			flash.put("model", updateModel);

			FacesContext.getCurrentInstance().getExternalContext().redirect(page);
		} catch (Exception e) {
			UtilLog.logToScreen(e);
		}
	}

	@SuppressWarnings("unchecked")
	private Class<T> getClassType() {
		ParameterizedType parameterizedType = (ParameterizedType) getClass().getGenericSuperclass();
		return (Class<T>) parameterizedType.getActualTypeArguments()[0];
	}

	public abstract BaseDao<T> getBaseDao();

	public void redirectNew(String page) {
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect(page);
		} catch (IOException e) {
			UtilLog.logToScreen(e);
		}
	}

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
