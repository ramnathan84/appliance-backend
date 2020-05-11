package com.demo.appmgmt.payload;

import java.util.Date;

import javax.validation.constraints.Size;

public class ApplianceRequest {

	@Size(max = 20)
	private String serialNumber;

	@Size(max = 20)
	private String brand;

	@Size(max = 40)
	private String model;

	@Size(max = 10)
	private String status;

	public ApplianceRequest(@Size(max = 20) String serialNumber, @Size(max = 20) String brand,
			@Size(max = 40) String model, @Size(max = 10) String status, Date dateBought) {

		this.serialNumber = serialNumber;
		this.brand = brand;
		this.model = model;
		this.status = status;
		this.dateBought = dateBought;
	}

	public ApplianceRequest() {

	}

	private Date dateBought;

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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getDateBought() {
		return dateBought;
	}

	public void setDateBought(Date dateBought) {
		this.dateBought = dateBought;
	}

	@Override
	public String toString() {
		return "ApplianceRequest [serialNumber=" + serialNumber + ", brand=" + brand + ", model=" + model + ", status="
				+ status + ", dateBought=" + dateBought + "]";
	}

}
