package com.technicalservice.model.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.technicalservice.model.base.ExtendedModel;
import com.technicalservice.model.type.ProcessType;

/**
 * @author oguzhan
 */
@Entity
@Table
public class ToDo extends ExtendedModel {

	private static final long serialVersionUID = 1L;

	@ManyToOne
	private User owner;

	private String title;

	private String description;

	@Temporal(TemporalType.TIMESTAMP)
	private Date deadline;

	@Enumerated(EnumType.STRING)
	private ProcessType processType;

	@OneToMany(fetch=FetchType.EAGER)
	private List<ToDo> toDos;

	public ToDo() {

	}

	public ToDo(User owner) {
		super();
		this.owner = owner;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
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

	public ProcessType getProcessType() {
		return processType;
	}

	public void setProcessType(ProcessType processType) {
		this.processType = processType;
	}

	public List<ToDo> getToDos() {
		return toDos;
	}

	public void setToDos(List<ToDo> toDos) {
		this.toDos = toDos;
	}

}
