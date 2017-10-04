package model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

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

	@Column(nullable = false)
	private String persNo;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String lastName;

	@Column(nullable = false)
	private String phoneNumber;

	private String address;
	private Gender gender;
	private String email;
	
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
