package model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="Manufacturer")
public class Manufacturer implements Serializable {

	private static final long serialVersionUID = 5111824639117611454L;

	@Id
	private String manufacturerCode;
	private String name;
	private String address;
	private String email;
	private String phoneNumber;
	
	@JsonIgnore
	@OneToMany(mappedBy="manufacturer", fetch=FetchType.EAGER)
	private List<Model> models;
	
	public Manufacturer(){}

	public Manufacturer(String manufacturerCode, String name, String address, String email, String phoneNumber) {
		this();
		this.manufacturerCode = manufacturerCode;
		this.name = name;
		this.address = address;
		this.email = email;
		this.phoneNumber = phoneNumber;
	}

	public String getManufacturerCode() {
		return manufacturerCode;
	}

	public void setManufacturerCode(String manufacturerCode) {
		this.manufacturerCode = manufacturerCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public List<Model> getModels() {
		return models;
	}

	public void setModels(List<Model> models) {
		this.models = models;
	}

	@Override
	public String toString() {
		return "Manufacturer [manufacturerCode=" + manufacturerCode + ", name=" + name + ", address=" + address
				+ ", email=" + email + ", phoneNumber=" + phoneNumber + ", models=" + models + "]";
	}
	
	
}
