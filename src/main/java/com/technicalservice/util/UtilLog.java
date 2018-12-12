package com.technicalservice.util;

import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;

/**
 * Ekrana veya console mesaj yazmak için kullanılan sınıftır.
 * 
 * @author oguzhan
 *
 */
public class UtilLog {

	/**
	 * Console'da tarih ve mesaj bilgisini gösterir.
	 * 
	 * @param string
	 *            console'da gösterilecek mesaj.
	 */
	public static void logWithTime(String string) {
		FacesContext.getCurrentInstance().getExternalContext().getContextName();
		System.out.println("TechnicalService: " + new Date() + string);
	}

	/**
	 * Console'da mesaj bilgisini gösterir.
	 * 
	 * @param string
	 *            console'da gösterilecek mesaj.
	 */
	public static void log(String string) {
		System.out.println("TechnicalService: " + string);
	}

	/**
	 * Console'da exception gösterir.
	 * 
	 * @param e
	 *            console'da gösterilecek exception.
	 */
	public static void log(Exception e) {
		e.printStackTrace();
	}

	/**
	 * Ekran'da exception gösterir.
	 * 
	 * @param e
	 *            ekran'da gösterilecek exception.
	 */
	public static void logToScreen(Exception e) {
		e.printStackTrace();
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_ERROR, "HATA", e.getLocalizedMessage()));
	}

	/**
	 * Ekrana mesajın şidddetine göre ilgili renkte mesaj gösterir.
	 * 
	 * @param severity
	 *            Mesajın şidddeti (Bilgilendirme, Uyarı, Hata)
	 * @param summary
	 *            Mesajın özeti.
	 * @param detail
	 *            Mesajın içeriği.
	 */
	public static void logToScreen(Severity severity, String summary, String detail) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, summary, detail));
	}

}
