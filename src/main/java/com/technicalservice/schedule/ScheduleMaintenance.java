package com.technicalservice.schedule;

import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Startup;
import javax.ejb.Stateless;

import com.technicalservice.controller.MailSender;
import com.technicalservice.dao.IssueDao;
import com.technicalservice.model.entity.Issue;
import com.technicalservice.model.type.IssueStatu;
import com.technicalservice.model.type.ProcessType;

/**
 * İşlemlerin otomotik yapıldığı sınıftır. Ör: bakım günü gelen cihazın sahibine
 * mail atma.
 * 
 * @author oguzhan
 *
 */
@Startup
@Stateless
public class ScheduleMaintenance {

	@EJB
	private IssueDao issueDao;

	/**
	 * Bakım günü gelen cihazlar için her saat başı mail atılır.
	 */
	@Schedule(hour = "09", minute = "15", second = "00", persistent = false)
	public void runForBackup() {

		try {
			List<Issue> issues = issueDao.getIssueByMaintenanceDay(new Date(), IssueStatu.MAINTENANCE,
					ProcessType.WAITING);
			String[] to = new String[issues.size()];
			for (int i = 0; i < issues.size(); i++) {
				to[i] = issues.get(i).getDeviceOwner().getUser().getEmail();
			}

			String subject = "Cihaz bakım gününüz gelmiştir.";
			String content = "Sevgili müşterimiz cihazınızın rutin bakımı yapılacaktır. Bilginize.";
			MailSender.mailSend(null, null, "TECHNICAL SERVICE", to, subject, content);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
