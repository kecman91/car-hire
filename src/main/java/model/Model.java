package model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="Model")
public class Model implements Serializable {

	private static final long serialVersionUID = -4552814014015941704L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String modelName;
	
	@JsonIgnore
	@OneToMany(mappedBy="model")
	private List<Vehicle> vehicles;
	
	@ManyToOne(optional = false)
	private Manufacturer manufacturer;
	
	public Model(){}	
	
	public Model(String modelName) {
		this();
		this.modelName = modelName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public List<Vehicle> getVehicles() {
		return vehicles;
	}

	public void setVehicles(List<Vehicle> vehicles) {
		this.vehicles = vehicles;
	}

	public Manufacturer getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(Manufacturer manufacturer) {
		this.manufacturer = manufacturer;
	}

	@Override
	public String toString() {
		return "Model [id=" + id + ", modelName=" + modelName + ", vehicles=" + vehicles + ", manufacturer="
				+ manufacturer + "]";
	}
	
	
}
