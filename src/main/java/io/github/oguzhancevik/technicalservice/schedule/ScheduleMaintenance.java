package io.github.oguzhancevik.technicalservice.schedule;

import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Startup;
import javax.ejb.Stateless;

import io.github.oguzhancevik.technicalservice.controller.MailSender;
import io.github.oguzhancevik.technicalservice.dao.IssueDao;
import io.github.oguzhancevik.technicalservice.model.entity.Issue;
import io.github.oguzhancevik.technicalservice.model.type.IssueStatu;
import io.github.oguzhancevik.technicalservice.model.type.ProcessType;
import io.github.oguzhancevik.technicalservice.util.UtilLog;

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
	 * Bakım günü gelen cihazlar için saat sabah 10'da mail atılır.
	 */
	@Schedule(hour = "10", minute = "00", second = "00", persistent = false)
	public void sendMail() {

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
			UtilLog.log(e);
		}
	}

}
