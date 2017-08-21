package model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="Vehicle")
@Data
@NoArgsConstructor
public class Vehicle implements Serializable {

	private static final long serialVersionUID = 2332122258798069754L;
	
	@Id
	private String regNumber;

	@Column(nullable = false)
	private long currentMileage;

	@Column(nullable = false)
	private double engineSize;

	@Column(nullable = false)
	private int horsePower;

	private String fuelType;

	private Date lastService;

	@Column(nullable = false)
	private double price;

	@Column(nullable = false)
	private int numberOfSeats;

	@Column(nullable = false)
	private int manufacturingYear;

	private String image;
	
	@JsonIgnore
	@OneToMany(mappedBy="vehicle", cascade=CascadeType.REMOVE)
	private List<Booking> bookings;
	
	@ManyToOne(optional = false)
	private VehicleCategory category;
	
	@ManyToOne
	private Model model;

	public Vehicle(String regNumber, long currentMileage, double engineSize, int horsePower, String fuelType,
			Date lastService, double price, int numberOfSeats, int manufacturingYear, String image) {
		this.regNumber = regNumber;
		this.currentMileage = currentMileage;
		this.engineSize = engineSize;
		this.horsePower = horsePower;
		this.fuelType = fuelType;
		this.lastService = lastService;
		this.price = price;
		this.numberOfSeats = numberOfSeats;
		this.manufacturingYear = manufacturingYear;
		this.image = image;
	}
	
}
