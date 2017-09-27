package beans;

import java.util.*;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import dto.VehicleFilterDto;
import dto.MostPopularVehicleDto;
import model.Vehicle;

@Stateless
public class VehicleBeanImpl implements VehicleBean {

    @PersistenceContext(name = "seds")
    private EntityManager em;

    @Override
    public List<Vehicle> loadAllVehicles() {
        TypedQuery<Vehicle> q = em.createQuery(
                "select v from Vehicle v order by v.model.manufacturer.name, v.model.modelName", Vehicle.class);
        return q.getResultList();
    }

    @Override
    public Vehicle saveVehicle(Vehicle v) {
        if (em.find(Vehicle.class, v.getRegNumber()) != null) {
            em.merge(v);
        } else {
            em.persist(v);
        }
        return v;
    }

    @Override
    public boolean deleteVehicle(Vehicle v) {
        try {
            Vehicle temp = em.merge(v);
            em.remove(temp);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public List<Vehicle> loadFreeVehicles(Date from, Date to) {
        TypedQuery<Vehicle> q = em.createQuery("select v from Vehicle v where " +
                "(select count(b1.id) from Booking b1 where b1.vehicle.regNumber = v.regNumber and " +
                "(:dateFrom not between b1.dateFrom and b1.dateTo) and " +
                "(:dateTo not between b1.dateFrom and b1.dateTo)) = 0", Vehicle.class);
        q.setParameter("dateFrom", from);
        q.setParameter("dateTo", to);
        return q.getResultList();
    }

    @Override
    public Vehicle loadVehicle(String id) {
        return em.find(Vehicle.class, id);
    }

    @Override
    public List<Vehicle> loadFilteredVehicles(VehicleFilterDto filter) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Vehicle> cq = cb.createQuery(Vehicle.class);
        Root<Vehicle> vehicleRoot = cq.from(Vehicle.class);
        cq.select(vehicleRoot);
        List<Predicate> predicateList = new ArrayList<>();
        if (filter.getManufacturers() != null && filter.getManufacturers().length > 0) {
            Predicate manufacturerCondition = cb.equal(vehicleRoot.get("model").get("manufacturer")
                    .get("manufacturerCode"), filter.getManufacturers()[0]);
            for (int i = 0; i < filter.getManufacturers().length; i++) {
                manufacturerCondition = cb.or(cb.equal(vehicleRoot.get("model").get("manufacturer")
                        .get("manufacturerCode"), filter.getManufacturers()[i]), manufacturerCondition);
            }
            predicateList.add(manufacturerCondition);
        }
        if (filter.getCategories() != null && filter.getCategories().length > 0) {
            Predicate categoryCondition = cb.equal(vehicleRoot.get("category").get("id"), filter.getCategories()[0]);
            for (int i = 0; i < filter.getCategories().length; i++) {
                categoryCondition = cb.or(cb.equal(vehicleRoot.get("category").get("id"), filter.getCategories()[i]),
                        categoryCondition);
            }
            predicateList.add(categoryCondition);
        }
        if (filter.getPriceGT() > 0) {
            predicateList.add(cb.greaterThanOrEqualTo(vehicleRoot.get("price"), filter.getPriceGT()));
        }
        if (filter.getPriceLT() > 0) {
            predicateList.add(cb.lessThanOrEqualTo(vehicleRoot.get("price"), filter.getPriceLT()));
        }
        cq.where(predicateList.toArray(new Predicate[0]))
                .orderBy(cb.asc(vehicleRoot.get("model").get("manufacturer").get("name")),
                        cb.asc(vehicleRoot.get("model").get("modelName")));
        TypedQuery<Vehicle> vehicleQuery = em.createQuery(cq);
        return vehicleQuery.getResultList();
    }

    @Override
    public Vehicle getLastMonthsMostPopular() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, -1);
        calendar.set(Calendar.SECOND, 0);
        Date dateTo = calendar.getTime();
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1);
        Date dateFrom = calendar.getTime();

        TypedQuery<MostPopularVehicleDto> query = em
                .createQuery("SELECT NEW dto.MostPopularVehicleDto(v.regNumber, COUNT(b.id)) from Vehicle v " +
                        "JOIN v.bookings b WHERE b.bookedOn BETWEEN :dateFrom AND :dateTo GROUP BY " +
                        "v.regNumber ORDER BY COUNT(b.id) DESC", MostPopularVehicleDto.class);

        query.setParameter("dateFrom", dateFrom);
        query.setParameter("dateTo", dateTo);
        query.setMaxResults(1);

        MostPopularVehicleDto result = query.getSingleResult();
        return em.find(Vehicle.class, result.getRegNumber());
    }

    @Override
    public List<Vehicle> search(String searchTerm) {
        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<Vehicle> cq = cb.createQuery(Vehicle.class);
        Root<Vehicle> vehicleRoot = cq.from(Vehicle.class);
        cq.select(vehicleRoot).where(cb.or(
                cb.like(cb.lower(vehicleRoot.get("model").get("manufacturer").get("name")),
                        searchTerm.toLowerCase() + "%"),
                cb.like(cb.lower(vehicleRoot.get("model").get("modelName")), searchTerm.toLowerCase() + "%"),
                cb.like(cb.lower(vehicleRoot.get("regNumber")), searchTerm.toLowerCase() + "%")));

        TypedQuery<Vehicle> query = em.createQuery(cq);
        return query.getResultList();
    }
}
