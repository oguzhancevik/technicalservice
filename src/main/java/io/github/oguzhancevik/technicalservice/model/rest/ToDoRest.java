package io.github.oguzhancevik.technicalservice.model.rest;

import java.io.Serializable;
import java.util.Date;

/**
 * @author oguzhan
 */
public class ToDoRest implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	private String ownerEmail;

	private String title;

	private String description;

	private Date deadline;

	private String processType;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOwnerEmail() {
		return ownerEmail;
	}

	public void setOwnerEmail(String ownerEmail) {
		this.ownerEmail = ownerEmail;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getDeadline() {
		return deadline;
	}

	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}

	public String getProcessType() {
		return processType;
	}

	public void setProcessType(String processType) {
		this.processType = processType;
	}

	@Override
	public String toString() {
		return "ToDoRest [title=" + title + ", description=" + description + "]";
	}

}
