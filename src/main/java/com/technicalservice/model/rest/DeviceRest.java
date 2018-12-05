package com.technicalservice.model.rest;

import java.io.Serializable;

/**
 * @author oguzhan
 */
public class DeviceRest implements Serializable {

	private static final long serialVersionUID = 1L;

	private String ownerEmail;

	private String brand;

	private String model;

	private String deviceType;

	private Integer guaranteePeriod;

	private String color;

	private Integer height;

	private Integer width;

	private Integer kg;

	public String getOwnerEmail() {
		return ownerEmail;
	}

	public void setOwnerEmail(String ownerEmail) {
		this.ownerEmail = ownerEmail;
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

	@Override
	public String toString() {
		return "DeviceRest [ownerEmail=" + ownerEmail + ", brand=" + brand + ", deviceType=" + deviceType + "]";
	}

}
