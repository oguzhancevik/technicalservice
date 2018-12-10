package com.technicalservice.controller;

import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.technicalservice.controller.base.BaseBean;
import com.technicalservice.dao.ToDoDao;
import com.technicalservice.dao.base.BaseDao;
import com.technicalservice.model.entity.ToDo;
import com.technicalservice.model.type.ProcessType;
import com.technicalservice.util.UtilLog;

/**
 * Admin ekranında to-do (yapılacak) listesinin listelendiği sınıftır.
 * 
 * @author oguzhan
 *
 */
@ManagedBean(name = "toDoListBean")
@ViewScoped
public class ToDoListBean extends BaseBean<ToDo> {

	private static final long serialVersionUID = 1L;

	@EJB
	private ToDoDao toDoDao;

	@Override
	public BaseDao<ToDo> getBaseDao() {
		return toDoDao;
	}

	/**
	 * Login olan kullanıcının bilgisine göre to-do listesini listeleyen metoddur.
	 */
	@Override
	public List<ToDo> listInitial() {
		return toDoDao.getToDosByUser(getSessionObject().getUser());
	}

	/**
	 * todo listesinin processtype parametresine göre değiştirilen metoddur.
	 * 
	 * @param toDo
	 *            yapılacak listesi
	 * @param processType
	 *            işlem tipi
	 */
	public void changeProcessType(ToDo toDo, ProcessType processType) {
		try {
			toDo.setProcessType(processType);
			toDoDao.save(toDo);
			UtilLog.logToScreen(FacesMessage.SEVERITY_INFO, "Başarılı", "İşlem tipi değiştirildi!");
		} catch (Exception e) {
			UtilLog.log(e);
			UtilLog.logToScreen(FacesMessage.SEVERITY_ERROR, "HATA", "İşlem tipi değiştirilemedi!");
		}
	}

}
