package io.github.oguzhancevik.technicalservice.model.rest;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import io.github.oguzhancevik.technicalservice.model.entity.Process;

/**
 * @author oguzhan
 */
public class IssueRest implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	private String ownerEmail;

	private Long deviceId;

	private String issueStatu;

	private String processType;

	private String description;

	private Date date;

	private List<Process> processes;

	private Long repairmanEmail;

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

	public Long getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(Long deviceId) {
		this.deviceId = deviceId;
	}

	public String getIssueStatu() {
		return issueStatu;
	}

	public void setIssueStatu(String issueStatu) {
		this.issueStatu = issueStatu;
	}

	public String getProcessType() {
		return processType;
	}

	public void setProcessType(String processType) {
		this.processType = processType;
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

	public List<Process> getProcesses() {
		return processes;
	}

	public void setProcesses(List<Process> processes) {
		this.processes = processes;
	}

	public Long getRepairmanEmail() {
		return repairmanEmail;
	}

	public void setRepairmanEmail(Long repairmanEmail) {
		this.repairmanEmail = repairmanEmail;
	}

	@Override
	public String toString() {
		return "IssueRest [ownerEmail=" + ownerEmail + ", deviceId=" + deviceId + ", description=" + description + "]";
	}

}
