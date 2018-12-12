package com.technicalservice.model.rest;

import java.io.Serializable;
import java.util.Date;

/**
 * @author oguzhan
 */
public class ProcessRest implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	private String repairmanEmail;

	private Long issueId;

	private String description;

	private Date date;

	private String processType;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRepairmanEmail() {
		return repairmanEmail;
	}

	public void setRepairmanEmail(String repairmanEmail) {
		this.repairmanEmail = repairmanEmail;
	}

	public Long getIssueId() {
		return issueId;
	}

	public void setIssueId(Long issueId) {
		this.issueId = issueId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getProcessType() {
		return processType;
	}

	public void setProcessType(String processType) {
		this.processType = processType;
	}

	@Override
	public String toString() {
		return "ProcessRest [repairmanEmail=" + repairmanEmail + ", issueId=" + issueId + ", description=" + description
				+ "]";
	}

}
