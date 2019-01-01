package io.github.oguzhancevik.technicalservice.model.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import io.github.oguzhancevik.technicalservice.model.base.ExtendedModel;

/**
 * @author oguzhan
 */
@Entity
@Table
public class PasswordResetRequest extends ExtendedModel {

	private static final long serialVersionUID = 1L;

	@ManyToOne(fetch = FetchType.EAGER)
	private User user;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
