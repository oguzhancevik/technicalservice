package io.github.oguzhancevik.technicalservice.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;

import io.github.oguzhancevik.technicalservice.dao.ToDoDao;
import io.github.oguzhancevik.technicalservice.model.entity.ToDo;
import io.github.oguzhancevik.technicalservice.model.entity.User;
import io.github.oguzhancevik.technicalservice.model.type.ProcessType;
import io.github.oguzhancevik.technicalservice.util.UtilLog;

/**
 * Admin kullanıcısı tarafından yeni yapılacak listesi eklemek için veya
 * yapılacak listesini güncellemek için kullanılan sınıftır.
 * 
 * @author oguzhan
 */
@ManagedBean(name = "toDoDetailBean")
@ViewScoped
public class ToDoDetailBean {

	@ManagedProperty(value = "#{sessionObject}")
	private SessionObject sessionObject;

	@EJB
	private ToDoDao toDoDao;

	private ToDo mainToDo;

	private ToDo subToDo;

	private List<ToDo> toDos;

	/**
	 * To-do listesi sayfasından Flash ile bir obje gönderilmiş ise burada kontrol
	 * ediliyor. Dolu ise mainToDo değişkenine gelen obje atanıyor.
	 */
	@PostConstruct
	public void init() {

		mainToDo = new ToDo(new User());
		mainToDo.setToDos(new ArrayList<>());
		mainToDo.setProcessType(ProcessType.WAITING);

		Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
		if (flash.containsKey("model")) {
			mainToDo = (ToDo) flash.get("model");
		}

		subToDo = new ToDo(new User());
		subToDo.setToDos(new ArrayList<>());
		toDos = toDoDao.getToDosByProcessType(ProcessType.WAITING);
	}

	/**
	 * To-Do içine bağımlı başka bir To-Do listesi eklenirse bu metod kullanılır.
	 */
	public void addToDo() {
		try {

			if (subToDo == null) {
				UtilLog.logToScreen(FacesMessage.SEVERITY_WARN, "Uyarı", "To-do seç!");
			} else {

				if (mainToDo.getToDos().contains(subToDo)) {
					UtilLog.logToScreen(FacesMessage.SEVERITY_WARN, "Uyarı", "ToDo zaten eklenmiş!");
				} else {
					mainToDo.getToDos().add(subToDo);
					subToDo = new ToDo();
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
			mainToDo.getToDos().remove(toDo);
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
	public void save(String page) throws IOException {
		try {
			mainToDo.setOwner(getSessionObject().getUser());
			toDoDao.save(mainToDo);
		} catch (Exception e) {
			UtilLog.logToScreen(FacesMessage.SEVERITY_ERROR, "HATA", "ToDo Kaydedilemedi!");
			UtilLog.log(e);
		}
	}

	public SessionObject getSessionObject() {
		return sessionObject;
	}

	public void setSessionObject(SessionObject sessionObject) {
		this.sessionObject = sessionObject;
	}

	public ToDo getMainToDo() {
		return mainToDo;
	}

	public void setMainToDo(ToDo mainToDo) {
		this.mainToDo = mainToDo;
	}

	public ToDo getSubToDo() {
		return subToDo;
	}

	public void setSubToDo(ToDo subToDo) {
		this.subToDo = subToDo;
	}

	public List<ToDo> getToDos() {
		return toDos;
	}

	public void setToDos(List<ToDo> toDos) {
		this.toDos = toDos;
	}

}
