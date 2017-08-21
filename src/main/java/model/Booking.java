package model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="Booking")
@Getter
@Setter
@NoArgsConstructor
public class Booking implements Serializable {

	private static final long serialVersionUID = -7775585632931296740L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Column(nullable = false)
	private Date dateFrom;

	@Column(nullable = false)
	private Date dateTo;

	private boolean paymentReceived = false;

	private BookingStatus bookingStatus;

	@Column(nullable = false)
	private double totalPrice;

	@Column(nullable = false)
	private Date bookedOn = new Date();

	@ManyToOne(optional = false)
	private Customer customer;

	@ManyToOne(optional = false)
	private Vehicle vehicle;

	@Override
	public String toString() {
		return "BookingBean [id=" + id + ", from=" + dateFrom + ", to=" + dateTo + ", paymentReceived=" + paymentReceived
				+ ", customer=" + customer.toString() + ", vehicle=" + vehicle.toString() + "]";
	}
	
}
