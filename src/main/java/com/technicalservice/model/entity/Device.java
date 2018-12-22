package com.technicalservice.model.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.technicalservice.model.base.ExtendedModel;

/**
 * @author oguzhan
 */
@Entity
@Table
public class Device extends ExtendedModel {

	private static final long serialVersionUID = 1L;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	private Customer owner;

	private String brand;

	private String model;

	private String deviceType;

	@Temporal(TemporalType.DATE)
	private Date productionDate;

	private Integer guaranteePeriod;

	private String color;

	private String image;

	private Integer height;

	private Integer width;

	private Integer kg;

	@OneToMany(mappedBy = "device", fetch = FetchType.LAZY)
	private List<Issue> issues;

	public Customer getOwner() {
		return owner;
	}

	public void setOwner(Customer owner) {
		this.owner = owner;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public Date getProductionDate() {
		return productionDate;
	}

	public void setProductionDate(Date productionDate) {
		this.productionDate = productionDate;
	}

	public Integer getGuaranteePeriod() {
		return guaranteePeriod;
	}

	public void setGuaranteePeriod(Integer guaranteePeriod) {
		this.guaranteePeriod = guaranteePeriod;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	public Integer getKg() {
		return kg;
	}

	public void setKg(Integer kg) {
		this.kg = kg;
	}

	public List<Issue> getIssues() {
		return issues;
	}

	public void setIssues(List<Issue> issues) {
		this.issues = issues;
	}

	public Device() {
	}

	public Device(Customer owner) {
		this.owner = owner;
	}

	@Override
	public String toString() {
		return "Device [brand=" + brand + ", model=" + model + ", deviceType=" + deviceType + "]";
	}

}
