package com.technicalservice.controller;

import java.io.File;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.technicalservice.resource.ExternalResource;
import com.technicalservice.util.UtilLog;

/**
 * Mail gönderme işlemlerini yapan sınıftır.
 * 
 * @author oguzhan
 *
 */
public class MailSender {

	/**
	 * Mail göndermek için bu fonksiyon kullanılır. SMTP bilgileri
	 * {@link com.technicalservice.resource} dizini altında bulunan mail.properties
	 * dosyasından SMTP ve mail kullanıcı adı şifresini alarak o mailden karşı
	 * tarafa mail gider.
	 * 
	 * @param from
	 *            Mailin gönderileceği hesap(mail adresi).
	 * @param password
	 *            Mail adresinin şifresi.
	 * @param senderName
	 *            Göndericinin ismi.
	 * @param to
	 *            Mailin hangi mail hesabına/hesaplarına gönderileceği.
	 * @param subject
	 *            Mailin başlığı.
	 * @param text
	 *            Mailin içeriği.
	 * @param files
	 *            Mail'deki ekler.
	 * @throws Exception
	 */
	public static void mailSend(String from, String password, String senderName, String[] to, String subject,
			String text, File... files) throws Exception {
		try {
			ExternalResource ssResource = ExternalResource.getInstance("mail");

			if (!"true".equals(ssResource.getProperty("sendMail"))) {
				UtilLog.log("sendMail false");
				return;
			}

			String host = ssResource.getProperty("smtp.ip");
			String port = ssResource.getProperty("smtp.port");
			if (from == null) {
				from = ssResource.getProperty("smtp.user");
				password = ssResource.getProperty("smtp.password");
			}
			String type = ssResource.getProperty("smtp.type");

			Properties props = System.getProperties();
			if (type.equals("ssl")) {
				props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
				props.put("mail.smtp.socketFactory.port", port);
			} else if (type.equals("tls")) {
				props.put("mail.smtp.starttls.enable", "true");
			}
			props.put("mail.smtp.host", host);
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.port", port);
			props.put("mail.smtp.password", password);

			Session session = Session.getDefaultInstance(props, null);
			MimeMessage message = new MimeMessage(session);
			message.setFrom(senderName != null ? new InternetAddress(from, senderName) : new InternetAddress(from));
			InternetAddress[] toAddress = new InternetAddress[to.length];
			for (int i = 0; i < to.length; i++) {
				toAddress[i] = new InternetAddress(to[i]);
			}

			for (int i = 0; i < toAddress.length; i++) {
				message.addRecipient(Message.RecipientType.TO, toAddress[i]);
				UtilLog.log("MailSender TO:" + toAddress[i]);
			}
			message.setSubject(subject);
			UtilLog.log("MailSender SUBJECT: " + subject);

			Multipart multipart = new MimeMultipart();
			BodyPart bodyPart = new MimeBodyPart();
			bodyPart.setText(text);
			bodyPart.setContent(text, "text/html; charset=utf-8");
			multipart.addBodyPart(bodyPart);
			UtilLog.log("MailSender BODY: " + text);

			if (files != null) {
				for (File file : files) {
					BodyPart attachmentPart = new MimeBodyPart();
					DataSource dataSource = new FileDataSource(file);
					attachmentPart.setDataHandler(new DataHandler(dataSource));
					attachmentPart.setFileName(file.getName());

					multipart.addBodyPart(attachmentPart);
					UtilLog.log("MailSender ATTACHMENT: " + file.getName());
				}
			}
			message.setContent(multipart, "text/html; charset=utf-8");

			Transport transport = session.getTransport("smtp");
			transport.connect(host, from, password);
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
			UtilLog.log("MailSender OK! ");
		} catch (Exception e) {
			UtilLog.log("MailSender HATA: " + e.getMessage());
			e.printStackTrace();
		}
	}
}
