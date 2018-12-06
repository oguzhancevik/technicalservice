package com.technicalservice.controller;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.technicalservice.model.type.Gender;
import com.technicalservice.model.type.IssueStatu;
import com.technicalservice.model.type.MemberStatu;
import com.technicalservice.model.type.ProcessType;

/**
 * Birden fazla seçeneği olan tipleri örneğin kan grubu, cinsiyet gibi olan
 * enumları tek bir yerden çağırmak için bu sınıf kullanılır.
 * 
 * @author oguzhan
 *
 */
@ManagedBean(name = "typeBean")
@SessionScoped
public class TypeBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<MemberStatu> memberStatus;
	private List<Gender> genders;
	private List<IssueStatu> issueStatus;
	private List<ProcessType> processTypes;

	public List<MemberStatu> getMemberStatus() {
		if (memberStatus == null) {
			memberStatus = Arrays.asList(MemberStatu.values());
		}
		return memberStatus;
	}

	public void setMemberStatus(List<MemberStatu> memberStatus) {
		this.memberStatus = memberStatus;
	}

	public List<Gender> getGenders() {
		if (genders == null) {
			genders = Arrays.asList(Gender.values());
		}
		return genders;
	}

	public void setGenders(List<Gender> genders) {
		this.genders = genders;
	}

	public List<IssueStatu> getIssueStatus() {
		if (issueStatus == null) {
			issueStatus = Arrays.asList(IssueStatu.values());
		}
		return issueStatus;
	}

	public void setIssueStatus(List<IssueStatu> issueStatus) {
		this.issueStatus = issueStatus;
	}

	public List<ProcessType> getProcessTypes() {
		if (processTypes == null) {
			processTypes = Arrays.asList(ProcessType.values());
		}
		return processTypes;
	}

	public void setProcessTypes(List<ProcessType> processTypes) {
		this.processTypes = processTypes;
	}

}
