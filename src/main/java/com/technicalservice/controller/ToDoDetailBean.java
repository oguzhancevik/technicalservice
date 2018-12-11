package com.technicalservice.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;

import com.technicalservice.controller.base.BaseBean;
import com.technicalservice.dao.ToDoDao;
import com.technicalservice.dao.base.BaseDao;
import com.technicalservice.model.entity.ToDo;
import com.technicalservice.model.entity.User;
import com.technicalservice.model.type.ProcessType;
import com.technicalservice.util.UtilLog;

/**
 * Admin kullanıcısı tarafından yeni yapılacak listesi eklemek için veya
 * yapılacak listesini güncellemek için kullanılan sınıftır.
 * 
 * @author oguzhan
 */
@ManagedBean(name = "toDoDetailBean")
@ViewScoped
public class ToDoDetailBean extends BaseBean<ToDo> {

	private static final long serialVersionUID = 1L;

	@EJB
	private ToDoDao toDoDao;

	private ToDo toDo;

	private List<ToDo> toDos;

	/**
	 * To-do listesi sayfasından Flash ile bir obje gönderilmiş ise burada kontrol
	 * ediliyor. Dolu ise selectedModel değişkenine gelen obje atanıyor.
	 */
	@Override
	@PostConstruct
	public void init() {

		setSelectedModel(new ToDo(new User()));
		getSelectedModel().setToDos(new ArrayList<>());
		getSelectedModel().setProcessType(ProcessType.WAITING);

		Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
		if (flash.containsKey("model")) {
			setSelectedModel((ToDo) flash.get("model"));
		}

		toDo = new ToDo(new User());
		toDo.setToDos(new ArrayList<>());
		toDos = toDoDao.getToDosByProcessType(ProcessType.WAITING);
	}

	/**
	 * To-Do içine bağımlı başka bir To-Do listesi eklenirse bu metod kullanılır.
	 */
	public void addToDo() {
		try {

			if (toDo == null) {
				UtilLog.logToScreen(FacesMessage.SEVERITY_WARN, "Uyarı", "To-do seç!");
			} else {

				if (getSelectedModel().getToDos().contains(toDo)) {
					UtilLog.logToScreen(FacesMessage.SEVERITY_WARN, "Uyarı", "ToDo zaten eklenmiş!");
				} else {
					getSelectedModel().getToDos().add(toDo);
					toDo = new ToDo();
					UtilLog.logToScreen(FacesMessage.SEVERITY_INFO, "Başarılı", "ToDo eklendi!");
				}
			}

		} catch (Exception e) {
			UtilLog.log(e);
			UtilLog.logToScreen(FacesMessage.SEVERITY_ERROR, "HATA", "ToDo eklenemedi!");
		}
	}

	/**
	 * To-Do içine bağımlı başka bir To-Do listesini silmek için bu metod
	 * kullanılır.
	 * 
	 * @param toDo
	 *            Silinmek istenen yapılacak listesi
	 */
	public void removeToDo(ToDo toDo) {
		try {
			getSelectedModel().getToDos().remove(toDo);
			UtilLog.logToScreen(FacesMessage.SEVERITY_INFO, "Başarılı", "ToDo silindi!");
		} catch (Exception e) {
			UtilLog.log(e);
			UtilLog.logToScreen(FacesMessage.SEVERITY_ERROR, "HATA", "ToDo silinemedi!");
		}
	}

	/**
	 * @param page
	 *            Yönlendirilecek sayfa adresi
	 */
	@Override
	public void save(String page) throws IOException {
		try {
			getSelectedModel().setOwner(getSessionObject().getUser());
			super.save(page);
		} catch (Exception e) {
			UtilLog.logToScreen(FacesMessage.SEVERITY_ERROR, "HATA", "Müşteri Kaydedilemedi!");
			e.printStackTrace();
		}
	}

	@Override
	public List<ToDo> listInitial() {
		return null;
	}

	@Override
	public BaseDao<ToDo> getBaseDao() {
		return toDoDao;
	}

	public ToDo getToDo() {
		return toDo;
	}

	public void setToDo(ToDo toDo) {
		this.toDo = toDo;
	}

	public List<ToDo> getToDos() {
		return toDos;
	}

	public void setToDos(List<ToDo> toDos) {
		this.toDos = toDos;
	}

}
