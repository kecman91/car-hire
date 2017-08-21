package beans;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import model.Customer;

@Stateless
public class CustomerBeanImpl implements CustomerBean {

    @PersistenceContext(name = "seds")
    private EntityManager em;

    @Override
    public List<Customer> loadAllCustomers() {
        TypedQuery<Customer> q = em.createQuery(
                "select c from Customer c order by c.name, c.lastName", Customer.class);
        return q.getResultList();
    }

    @Override
    public Customer saveCustomer(Customer c) {
        if (em.find(Customer.class, c.getId()) != null) {
            em.merge(c);
        } else {
            em.persist(c);
        }
        return c;
    }

    @Override
    public boolean deleteCustomer(int id) {
        try {
            Customer c = em.find(Customer.class, id);
            em.remove(c);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public Customer loadCustomer(int id) {
        return em.find(Customer.class, id);
    }

    @Override
    public List<Customer> getByUnpaidBookings() {
        TypedQuery query = em.createQuery(
                "SELECT DISTINCT c from Customer c JOIN c.bookings b WHERE b.paymentReceived = false " +
                        "ORDER BY c.name, c.lastName", Customer.class);
        return query.getResultList();
    }

    @Override
    public List<Customer> search(String searchTerm) {
        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<Customer> cq = cb.createQuery(Customer.class);
        Root<Customer> customerRoot = cq.from(Customer.class);
        cq.select(customerRoot).where(cb.or(
                cb.like(cb.lower(customerRoot.get("name")), searchTerm.toLowerCase() + "%"),
                cb.like(cb.lower(customerRoot.get("lastName")), searchTerm.toLowerCase() + "%"),
                cb.like(cb.lower(customerRoot.get("persNo")), searchTerm.toLowerCase() + "%")));

        TypedQuery<Customer> query = em.createQuery(cq);
        return query.getResultList();
    }

}
