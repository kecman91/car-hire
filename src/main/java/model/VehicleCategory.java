package model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="VehicleCategory")
public class VehicleCategory implements Serializable {

	private static final long serialVersionUID = -2799081492712678013L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String category;
	
	@JsonIgnore
	@OneToMany(mappedBy="category", fetch=FetchType.EAGER)
	private List<Vehicle> vehicles; 
	
	public VehicleCategory(){}

	public VehicleCategory(String category) {
		this();
		this.category = category;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public List<Vehicle> getVehicles() {
		return vehicles;
	}

	public void setVehicles(List<Vehicle> vehicles) {
		this.vehicles = vehicles;
	}

	@Override
	public String toString() {
		return "VehicleCategory [id=" + id + ", category=" + category + ", vehicles=" + vehicles + "]";
	}
	
	
}
