package com.demo.appmgmt.model;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Embeddable
public class ApplianceIdentity implements Serializable {

	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

	@NotNull
	private String serialNumber;

	@NotNull
	@Size(max = 20)
	private String brand;

	@NotNull
	@Size(max = 20)
	private String model;

	public ApplianceIdentity() {

	}

	public ApplianceIdentity(@NotNull String serialNumber, @NotNull @Size(max = 20) String brand,
			@NotNull @Size(max = 20) String model) {

		this.serialNumber = serialNumber;
		this.brand = brand;
		this.model = model;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
