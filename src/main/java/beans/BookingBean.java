package beans;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Remote;

import dto.BookingDto;
import model.Booking;

@Local
public interface BookingBean {
	List<model.Booking> loadAllBookings();
	Booking saveBooking(BookingDto bookingDto);
	boolean setPaid(int bookingId);
	boolean deleteBooking(int bookingId);
	List<Booking> searchBookings(int customerId, String vehicleRegNum, boolean unpaidOnly);
}
