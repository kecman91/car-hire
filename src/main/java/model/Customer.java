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
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="Customer")
@Data
@NoArgsConstructor
public class Customer implements Serializable {

	private static final long serialVersionUID = 7359410862239532125L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String persNo;
	private String name;
	private String lastName;
	private Gender gender;
	private String email;
	private String phoneNumber;
	private String address;
	
	@JsonIgnore
	@OneToMany(mappedBy="customer", fetch=FetchType.EAGER)
	private List<Booking> bookings;

	public Customer(String persNo, String name, String lastName, Gender gender, String email, String phoneNumber,
			String address) {
		this.persNo = persNo;
		this.name = name;
		this.lastName = lastName;
		this.gender = gender;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.address = address;
	}

}
