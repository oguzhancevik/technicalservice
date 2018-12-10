package com.technicalservice.model.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.technicalservice.model.base.ExtendedModel;

/**
 * @author oguzhan
 */
@Entity
@Table
public class Process extends ExtendedModel {

	private static final long serialVersionUID = 1L;

	@ManyToOne
	private Issue issue;

	private String description;

	@Temporal(TemporalType.TIMESTAMP)
	private Date date;

	public Issue getIssue() {
		return issue;
	}

	public void setIssue(Issue issue) {
		this.issue = issue;
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

	@Override
	public String toString() {
		return "Process [description=" + description + ", date=" + date + "]";
	}

}
