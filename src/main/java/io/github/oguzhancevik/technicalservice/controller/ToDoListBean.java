package io.github.oguzhancevik.technicalservice.controller;

import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import io.github.oguzhancevik.technicalservice.controller.base.BaseBean;
import io.github.oguzhancevik.technicalservice.dao.ToDoDao;
import io.github.oguzhancevik.technicalservice.dao.base.BaseDao;
import io.github.oguzhancevik.technicalservice.model.entity.ToDo;
import io.github.oguzhancevik.technicalservice.model.type.ProcessType;
import io.github.oguzhancevik.technicalservice.util.UtilLog;

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
			// Burada görev process type end yapılmak isteniyorsa eğer o todo-list altındaki
			// todo-listler hala bitmemiş ise uyarı veriyor
			if (processType.equals(ProcessType.END) && toDo.getToDos().stream()
					.filter(e -> e.getProcessType().equals(ProcessType.WAITING)).findAny().isPresent()) {
				UtilLog.logToScreen(FacesMessage.SEVERITY_INFO, "Uyarı",
						"Bağımlı alt To-Do listeleri henüz tamamlanmamış!");
				return;
			}
			toDo.setProcessType(processType);
			toDoDao.save(toDo);
			UtilLog.logToScreen(FacesMessage.SEVERITY_INFO, "Başarılı", "İşlem tipi değiştirildi!");
		} catch (Exception e) {
			UtilLog.log(e);
			UtilLog.logToScreen(FacesMessage.SEVERITY_ERROR, "HATA", "İşlem tipi değiştirilemedi!");
		}
	}

}
