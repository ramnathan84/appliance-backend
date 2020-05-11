package com.demo.appmgmt.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "appliance")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Appliance {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	private String serialNumber;

	@NotNull
	@Size(max = 20)
	private String brand;

	@NotNull
	@Size(max = 20)
	private String model;

	@Size(max = 20)
	private String status;

	private Date dateBought;

	public Appliance() {

	}

	public Appliance(Long id, @NotNull String serialNumber, @NotNull @Size(max = 20) String brand,
			@NotNull @Size(max = 20) String model, @Size(max = 20) String status, Date dateBought) {
		super();
		this.id = id;
		this.serialNumber = serialNumber;
		this.brand = brand;
		this.model = model;
		this.status = status;
		this.dateBought = dateBought;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
		return "Appliance [serialNumber=" + serialNumber + ", brand=" + brand + ", model=" + model + ", status="
				+ status + ", dateBought=" + dateBought + "]";
	}

}
