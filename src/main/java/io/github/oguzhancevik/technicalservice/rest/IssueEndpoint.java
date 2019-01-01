package io.github.oguzhancevik.technicalservice.rest;

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

import io.github.oguzhancevik.technicalservice.dao.CustomerDao;
import io.github.oguzhancevik.technicalservice.dao.DeviceDao;
import io.github.oguzhancevik.technicalservice.dao.IssueDao;
import io.github.oguzhancevik.technicalservice.dao.UserDao;
import io.github.oguzhancevik.technicalservice.model.entity.Customer;
import io.github.oguzhancevik.technicalservice.model.entity.Issue;
import io.github.oguzhancevik.technicalservice.model.entity.Process;
import io.github.oguzhancevik.technicalservice.model.pojo.Result;
import io.github.oguzhancevik.technicalservice.model.rest.IssueRest;
import io.github.oguzhancevik.technicalservice.model.rest.ProcessRest;
import io.github.oguzhancevik.technicalservice.model.type.IssueStatu;
import io.github.oguzhancevik.technicalservice.model.type.ProcessType;
import io.github.oguzhancevik.technicalservice.util.UtilLog;

/**
 * Rest servis ile bakım/onarım işlemleri yapılan sınıftır.
 * 
 * @author oguzhan
 */
@Stateless
@Path("/issue")
public class IssueEndpoint {

	@EJB
	private UserDao userDao;

	@EJB
	private CustomerDao customerDao;
	
	@EJB
	private DeviceDao deviceDao;

	@EJB
	private IssueDao issueDao;

	/**
	 * Url:http://localhost:8080/technicalservice/rest/issue/create
	 * 
	 * @param issueRest
	 *            kayıt edilecek bakım onarım talebi.
	 * @return bakım onarım talebinin kayıt edilip edilmediği bilgisini döndürür.
	 * @throws Exception
	 */
	@POST
	@Path("/create")
	@Consumes(MediaType.APPLICATION_JSON + "; charset=UTF-8")
	@Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
	public Response create(IssueRest issueRest) throws Exception {

		Result result = new Result();
		try {
			Issue issue = new Issue();
			issue.setDeviceOwner(customerDao.findByUser(userDao.findByEmail(issueRest.getOwnerEmail())));
			issue.setDevice(deviceDao.findById(issueRest.getDeviceId()));
			issue.setIssueStatu(IssueStatu.valueOf(issueRest.getIssueStatu()));
			issue.setProcessType(ProcessType.valueOf(issueRest.getProcessType()));
			issue.setDescription(issueRest.getDescription());
			issue.setDate(issueRest.getDate());

			issueDao.save(issue);

			result.setResult(true);
			result.setMessage("Bakım/Onarım talebi kayıt edildi!");
		} catch (Exception e) {
			UtilLog.log(e);
			result.setResult(false);
			result.setMessage("Bakım/Onarım talebi kayıt edilemedi!");
		}
		return Response.ok(result).build();
	}

	/**
	 * Url: http://localhost:8080/technicalservice/rest/issue/list
	 * 
	 * @return aktif tüm bakım onarım listesini döner.
	 */
	@GET
	@Path("/list")
	@Consumes(MediaType.APPLICATION_JSON + "; charset=UTF-8")
	@Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
	public Response listAll() {
		List<Issue> issues = new ArrayList<>();
		try {
			issues = issueDao.getIssuesByProcess(null, null);
		} catch (Exception e) {
			UtilLog.log(e);
		}
		return Response.ok(issues).build();
	}

	/**
	 * Url: http://localhost:8080/technicalservice/rest/issue/list/test@gmail.com
	 * 
	 * @param email
	 *            Bakım onarım talepleri listelenecek olan Customer maili
	 * @return cihaz listesi döner
	 */
	@GET
	@Path("/list/{email}")
	@Consumes(MediaType.APPLICATION_JSON + "; charset=UTF-8")
	@Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
	public Response listByEmail(@PathParam("email") String email) {
		List<Issue> issues = new ArrayList<>();
		try {
			Customer customer = customerDao.findByUser(userDao.findByEmail(email));
			issues = issueDao.listIssuesByCustomer(customer);
		} catch (Exception e) {
			UtilLog.log(e);
		}
		return Response.ok(issues).build();
	}

	/**
	 * Url: http://localhost:8080/technicalservice/rest/issue/update
	 * 
	 * @param issueRest
	 *            güncellenecek olan bakım onarım talebi.
	 * @return bakım onarım talebinin güncellenip güncellenmediği bilgisini
	 *         döndürür.
	 */
	@PUT
	@Path("/update")
	@Consumes(MediaType.APPLICATION_JSON + "; charset=UTF-8")
	@Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
	public Response update(IssueRest issueRest) {
		Result result = new Result();

		try {
			Issue issue = issueDao.findById(issueRest.getId());
			issue.setIssueStatu(IssueStatu.valueOf(issueRest.getIssueStatu()));
			issue.setProcessType(ProcessType.valueOf(issueRest.getProcessType()));
			issue.setDescription(issueRest.getDescription());
			issue.setDate(issueRest.getDate());

			issueDao.save(issue);

			result.setResult(true);
			result.setMessage("Bakım/Onarım talebi güncellendi!");
		} catch (Exception e) {
			UtilLog.log(e);
			result.setResult(false);
			result.setMessage("Bakım/Onarım talebi güncellenemedi!");
		}

		return Response.ok(result).build();
	}

	/**
	 * Url: http://localhost:8080/technicalservice/rest/issue/remove
	 * 
	 * @param issueRest
	 *            silinecek olan bakım/onarım talebi
	 * @return bakım/onarım talebinin silinip silinmediği bilgisini döner.
	 */
	@DELETE
	@Path("/remove")
	@Consumes(MediaType.APPLICATION_JSON + "; charset=UTF-8")
	@Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
	public Response remove(IssueRest issueRest) {
		Result result = new Result();
		try {

			Issue issue = issueDao.findById(issueRest.getId());
			issueDao.passive(issue);

			result.setResult(true);
			result.setMessage("Bakım/Onarım talebi silindi!");
		} catch (Exception e) {
			UtilLog.log(e);
			result.setResult(true);
			result.setMessage("Bakım/Onarım talebi silinemedi!");
		}
		return Response.ok(result).build();
	}

	/**
	 * Url: http://localhost:8080/technicalservice/rest/issue/addProcess
	 * 
	 * @param processRest
	 *            kayıt edilecek bakım onarım talebi için işlem bilgisi.
	 * @return bakım onarım talebi için keydedilecek işlem bilgisinin kayıt edilip
	 *         edilmediği bilgisini döndürür.
	 * @throws Exception
	 */
	@POST
	@Path("/addProcess")
	@Consumes(MediaType.APPLICATION_JSON + "; charset=UTF-8")
	@Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
	public Response addProcess(ProcessRest processRest) throws Exception {

		Result result = new Result();
		try {
			Issue issue = issueDao.findById(processRest.getIssueId());
			issue.setRepairman(userDao.findByEmail(processRest.getRepairmanEmail()));
			issue.setProcessType(ProcessType.valueOf(processRest.getProcessType()));

			if (issue.getProcesses() == null) {
				issue.setProcesses(new ArrayList<>());
			}

			Process process = new Process();
			process.setDescription(processRest.getDescription());
			process.setDate(processRest.getDate());
			issue.getProcesses().add(process);

			issueDao.save(issue);

			result.setResult(true);
			result.setMessage("Bakım/Onarım talebi için süreç kayıt edildi!");
		} catch (Exception e) {
			UtilLog.log(e);
			result.setResult(false);
			result.setMessage("Bakım/Onarım talebi için süreç kayıt edilemedi!");
		}
		return Response.ok(result).build();
	}

}
