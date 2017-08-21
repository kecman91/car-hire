package beans;

import dto.BookingDto;
import model.Booking;
import model.Customer;
import model.Vehicle;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Stateless
public class BookingBeanImpl implements BookingBean {

	@PersistenceContext(name="seds")
	private EntityManager em;

	@EJB
    private CustomerBean customerBean;

	@EJB
    private VehicleBean vehicleBean;

	@Override
	public List<Booking> loadAllBookings() {
		TypedQuery<Booking> q = em.createQuery("select b from Booking b", Booking.class);
		return q.getResultList();
	}

	@Override
	public Booking saveBooking(BookingDto bookingDto) {
        Customer customer;
	    if (bookingDto.getCustomerId() > 0) {
            customer = customerBean.loadCustomer(bookingDto.getCustomerId());
        } else {
	        customer = new Customer();
	        customer.setName(bookingDto.getCustomer().getName());
	        customer.setLastName(bookingDto.getCustomer().getLastName());
	        customer.setPersNo(bookingDto.getCustomer().getPersNo());
	        customer.setPhoneNumber(bookingDto.getCustomer().getPhoneNumber());
	        customer.setEmail(bookingDto.getCustomer().getEmail());
	        customer.setAddress(bookingDto.getCustomer().getAddress());
	        customer.setGender(bookingDto.getCustomer().getGender());
	        customer = customerBean.saveCustomer(customer);
        }
        Vehicle vehicle = vehicleBean.loadVehicle(bookingDto.getVehicleRegNumber());
	    Booking booking = new Booking();
	    booking.setCustomer(customer);
	    booking.setVehicle(vehicle);
	    booking.setPaymentReceived(bookingDto.isPaymentReceived());
	    booking.setDateFrom(bookingDto.getDateFrom());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(bookingDto.getDateFrom());
        calendar.add(Calendar.DAY_OF_MONTH, bookingDto.getNumberOfDays());
	    booking.setDateTo(calendar.getTime());
	    booking.setTotalPrice(vehicle.getPrice() * bookingDto.getNumberOfDays());
		try {
			em.persist(booking);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return booking;
	}

	@Override
	public boolean setPaid(int bookingId) {
		try {
			Booking b = em.find(Booking.class, bookingId);
			b.setPaymentReceived(true);
			em.merge(b);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean deleteBooking(int bookingId) {
		try {
			Booking b = em.find(Booking.class, bookingId);
			em.remove(b);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public List<Booking> searchBookings(int customerId, String vehicleRegNum, boolean unpaidOnly) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Booking> cq = cb.createQuery(Booking.class);
        Root<Booking> bookingRoot = cq.from(Booking.class);

        List<Predicate> predicates = new ArrayList<>();
        if (customerId > 0) {
			predicates.add(cb.equal(bookingRoot.get("customer").get("id"), customerId));
        }
        if (vehicleRegNum != null && vehicleRegNum.trim().length() > 0) {
            predicates.add(cb.equal(bookingRoot.get("vehicle").get("regNumber"), vehicleRegNum));
        }
        if (unpaidOnly) {
            predicates.add(cb.equal(bookingRoot.get("paymentReceived"), false));
        }

        cq.where(predicates.toArray(new Predicate[0])).orderBy(cb.desc(bookingRoot.get("bookedOn")));
		TypedQuery<Booking> query = em.createQuery(cq);
		return query.getResultList();
	}

}
