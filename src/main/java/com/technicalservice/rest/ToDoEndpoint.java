package com.technicalservice.rest;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.technicalservice.dao.ToDoDao;
import com.technicalservice.dao.UserDao;
import com.technicalservice.model.entity.ToDo;
import com.technicalservice.model.pojo.Result;
import com.technicalservice.model.rest.ToDoRest;
import com.technicalservice.model.type.ProcessType;
import com.technicalservice.util.UtilLog;

/**
 * Rest servis ile cihaz işlemleri yapılan sınıftır.
 * 
 * @author oguzhan
 */
@Stateless
@Path("/todo")
public class ToDoEndpoint {

	@EJB
	private UserDao userDao;

	@EJB
	private ToDoDao toDoDao;

	/**
	 * Url: http://localhost:8080/technicalservice/rest/todo/create
	 * 
	 * @param toDoRest
	 *            eklenecek todo
	 * @return todo listesinin kaydedilip kaydedilmediği bilgisini döndürür.
	 * @throws Exception
	 */
	@POST
	@Path("/create")
	@Consumes(MediaType.APPLICATION_JSON + "; charset=UTF-8")
	@Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
	public Response create(ToDoRest toDoRest) throws Exception {

		Result result = new Result();
		try {
			ToDo toDo = new ToDo(userDao.findByEmail(toDoRest.getOwnerEmail()));
			toDo.setTitle(toDoRest.getTitle());
			toDo.setDescription(toDoRest.getDescription());
			toDo.setDeadline(toDoRest.getDeadline());
			toDo.setProcessType(ProcessType.valueOf(toDoRest.getProcessType()));

			toDoDao.save(toDo);

			result.setResult(true);
			result.setMessage("To-Do kayıt edildi!");
		} catch (Exception e) {
			UtilLog.log(e);
			result.setResult(false);
			result.setMessage("To-Do kayıt edilemedi!");
		}
		return Response.ok(result).build();
	}

	/**
	 * Url:
	 * http://localhost:8080/technicalservice/rest/todo/list/oguzhancevik96@gmail.com
	 * 
	 * @param email
	 *            todo listeleri listelenecek olan kullanıcı
	 * @return todo listesi döner
	 */
	@GET
	@Path("/list/{email}")
	@Consumes(MediaType.APPLICATION_JSON + "; charset=UTF-8")
	@Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
	public Response list(@PathParam("email") String email) {
		List<ToDo> toDos = new ArrayList<>();
		try {
			toDos = toDoDao.getToDosByUser(userDao.findByEmail(email));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.ok(toDos).build();
	}

	/**
	 * Url: http://localhost:8080/technicalservice/rest/todo/update
	 * 
	 * @param toDoRest
	 *            güncellenecek todo
	 * @return todo listesinin güncellenip güncellenmediği bilgisini döndürür.
	 */
	@PUT
	@Path("/update")
	@Consumes(MediaType.APPLICATION_JSON + "; charset=UTF-8")
	@Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
	public Response update(ToDoRest toDoRest) {
		Result result = new Result();

		try {

			ToDo toDo = toDoDao.findById(toDoRest.getId());
			toDo.setTitle(toDoRest.getTitle());
			toDo.setDescription(toDoRest.getDescription());
			toDo.setDeadline(toDoRest.getDeadline());
			toDo.setProcessType(ProcessType.valueOf(toDoRest.getProcessType()));

			toDoDao.save(toDo);

			result.setResult(true);
			result.setMessage("To-Do güncellendi!");
		} catch (Exception e) {
			e.printStackTrace();
			result.setResult(false);
			result.setMessage("To-Do güncellenemedi!");
		}

		return Response.ok(result).build();
	}

	/**
	 * Url: http://localhost:8080/technicalservice/rest/todo/remove
	 * @param toDoRest
	 *            silinecek olan todo listesi
	 * @return todo listesinin silinip silinmediği bilgisini döndürür.
	 */
	@DELETE
	@Path("/remove")
	@Consumes(MediaType.APPLICATION_JSON + "; charset=UTF-8")
	@Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
	public Response remove(ToDoRest toDoRest) {
		Result result = new Result();
		try {

			ToDo toDo = toDoDao.findById(toDoRest.getId());
			toDoDao.passive(toDo);

			result.setResult(true);
			result.setMessage("To-Do silindi!");
		} catch (Exception e) {
			e.printStackTrace();
			result.setResult(true);
			result.setMessage("To-Do silinemedi!");
		}
		return Response.ok(result).build();
	}

}
