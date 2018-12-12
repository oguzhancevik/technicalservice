package com.technicalservice.model.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.technicalservice.model.base.ExtendedModel;
import com.technicalservice.model.type.IssueStatu;
import com.technicalservice.model.type.ProcessType;

/**
 * @author oguzhan
 */
@Entity
@Table
public class Issue extends ExtendedModel {

	private static final long serialVersionUID = 1L;

	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	private Device device;

	@JsonIgnore
	@OneToOne(cascade = CascadeType.ALL)
	private Customer deviceOwner;

	@Enumerated(EnumType.STRING)
	private IssueStatu issueStatu;

	@Enumerated(EnumType.STRING)
	private ProcessType processType;

	private String description;

	@Temporal(TemporalType.TIMESTAMP)
	private Date date;

	@JsonIgnore
	@OneToMany(mappedBy = "issue", fetch = FetchType.EAGER, cascade=CascadeType.ALL)
	private List<Process> processes;

	@JsonIgnore
	@OneToOne(cascade = CascadeType.ALL, optional = true)
	private User repairman;

	public Device getDevice() {
		return device;
	}

	public void setDevice(Device device) {
		this.device = device;
	}

	public Customer getDeviceOwner() {
		return deviceOwner;
	}

	public void setDeviceOwner(Customer deviceOwner) {
		this.deviceOwner = deviceOwner;
	}

	public IssueStatu getIssueStatu() {
		return issueStatu;
	}

	public void setIssueStatu(IssueStatu issueStatu) {
		this.issueStatu = issueStatu;
	}

	public ProcessType getProcessType() {
		return processType;
	}

	public void setProcessType(ProcessType processType) {
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

	public User getRepairman() {
		return repairman;
	}

	public void setRepairman(User repairman) {
		this.repairman = repairman;
	}

	@Override
	public String toString() {
		return "Issue [device=" + device + ", date=" + date + ", repairman=" + repairman + "]";
	}

}
