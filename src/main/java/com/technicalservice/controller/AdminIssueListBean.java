package com.technicalservice.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.technicalservice.controller.base.BaseBean;
import com.technicalservice.dao.IssueDao;
import com.technicalservice.dao.base.BaseDao;
import com.technicalservice.model.entity.Issue;
import com.technicalservice.model.entity.Process;
import com.technicalservice.model.type.ProcessType;
import com.technicalservice.util.UtilLog;

/**
 * Admin ekranında müşterinin cihaz arıza / bakım işlemlerinin listelendiği sınıftır.
 * 
 * @author oguzhan
 *
 */
@ManagedBean(name = "adminIssueListBean")
@ViewScoped
public class AdminIssueListBean extends BaseBean<Issue> {

	private static final long serialVersionUID = 1L;

	@EJB
	private IssueDao issueDao;

	private Process process;

	private ProcessType processType;

	@Override
	public BaseDao<Issue> getBaseDao() {
		return issueDao;
	}

	@Override
	public List<Issue> listInitial() {
		process=new Process();
		return issueDao.getIssuesByProcess(null, null);
	}

	/**
	 * Admin bakım işlemini bitirmiş ise cihaz sahibine mail gönderilir.
	 * 
	 * @param issue
	 *            Bakım talebi
	 */
	public void sendMail(Issue issue) {
		try {
			if (issue.getRepairman() != null && issue.getRepairman().getId() != getSessionObject().getUser().getId()) {
				UtilLog.logToScreen(FacesMessage.SEVERITY_ERROR, "HATA", "Bu işlemi başka bir kullanıcı üstlendi!");
			} else {
				issue.setRepairman(getSessionObject().getUser());
				issue.setProcessType(processType);

				process.setIssue(issue);
				process.setDate(new Date());

				if (issue.getProcesses().size() == 0) {
					issue.setProcesses(new ArrayList<>());
				}

				issue.getProcesses().add(process);
				issueDao.save(issue);

				String[] to = { issue.getDeviceOwner().getUser().getEmail() };
				String subject = "Cihaz bakımı yapılmıştır";
				String content = "Sevgili müşterimiz cihazınızın rutin bakımı yapılmıştır. İyi günler dileriz.";
				MailSender.mailSend(null, null, "TECHNICAL SERVICE", to, subject, content);
				listInitial();
			}

		} catch (Exception e) {
			UtilLog.log(e);
		}
	}

	public Process getProcess() {
		return process;
	}

	public void setProcess(Process process) {
		this.process = process;
	}

	public ProcessType getProcessType() {
		return processType;
	}

	public void setProcessType(ProcessType processType) {
		this.processType = processType;
	}

}
