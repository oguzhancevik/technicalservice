package com.technicalservice.rest;

import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.technicalservice.controller.MailSender;
import com.technicalservice.model.pojo.Result;
import com.technicalservice.model.rest.MailRest;

/**
 * Rest servis ile mail göndermek için kullanılan sınıftır.
 * @author oguzhan
 */
@Stateless
@Path("/mail")
public class MailEndpoint {

	/**
	 * Url: http://localhost:8080/technicalservice/rest/mail/send
	 * @param mailRest
	 *            Mail bilgilerinin içerdiği model (kime gideceği, mail başlığı ve
	 *            içeriği)
	 * @return mailin gidip gitmediği bilgisini döndürür.
	 * @throws Exception
	 */
	@POST
	@Path("/send")
	@Consumes(MediaType.APPLICATION_JSON + "; charset=UTF-8")
	@Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
	public Response sendMail(MailRest mailRest) throws Exception {

		Result result = new Result();
		try {
			MailSender.mailSend(null, null, "TECHNICAL SERVICE", mailRest.getTo(), mailRest.getSubject(),
					mailRest.getContent());
			result.setResult(true);
			result.setMessage("Mail gönderildi!");
		} catch (Exception e) {
			e.printStackTrace();
			result.setResult(false);
			result.setMessage("Mail gönderilemedi!");
		}
		return Response.ok(result).build();
	}

}
