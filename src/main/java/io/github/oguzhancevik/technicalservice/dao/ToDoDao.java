package io.github.oguzhancevik.technicalservice.dao;

import java.util.List;

import javax.ejb.Stateless;

import io.github.oguzhancevik.technicalservice.dao.base.BaseDao;
import io.github.oguzhancevik.technicalservice.model.entity.ToDo;
import io.github.oguzhancevik.technicalservice.model.entity.User;
import io.github.oguzhancevik.technicalservice.model.type.ProcessType;

/**
 * ToDo nesnesi için database işlemlerinin yapıldığı sınıftır.
 * 
 * @author oguzhan
 *
 */
@Stateless
public class ToDoDao extends BaseDao<ToDo> {

	private static final long serialVersionUID = 1L;

	public ToDoDao() {
		super(ToDo.class);
	}

	/**
	 * @param processType
	 *            işlem türü
	 * @return işlem türüne göre todo listesini döner
	 */
	@SuppressWarnings("unchecked")
	public List<ToDo> getToDosByProcessType(ProcessType processType) {
		return entityManager.createQuery("SELECT t FROM ToDo t WHERE t.processType= :processType ORDER BY t.id")
				.setParameter("processType", processType).getResultList();
	}

	/**
	 * 
	 * @param owner
	 *            todo listesinin sahibi
	 * @return user'a ait to-do listesi döner
	 */
	@SuppressWarnings("unchecked")
	public List<ToDo> getToDosByUser(User owner) {
		return entityManager.createQuery("SELECT t FROM ToDo t WHERE t.owner= :owner ORDER BY t.id").setParameter("owner", owner)
				.getResultList();
	}

}
