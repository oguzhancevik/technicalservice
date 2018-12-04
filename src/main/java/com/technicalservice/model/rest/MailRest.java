package com.technicalservice.model.rest;

import java.io.Serializable;
import java.util.Arrays;

/**
 * @author oguzhan
 */
public class MailRest implements Serializable {

	private static final long serialVersionUID = 1L;

	private String[] to;

	private String subject;

	private String content;

	public String[] getTo() {
		return to;
	}

	public void setTo(String[] to) {
		this.to = to;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "MailRest [to=" + Arrays.toString(to) + ", subject=" + subject + "]";
	}

}
