package beans;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import model.Manufacturer;

@Stateless
public class ManufacturerBeanImpl implements ManufacturerBean {
	
	@PersistenceContext(name="seds")
	EntityManager em;

	@SuppressWarnings("unchecked")
	public List<Manufacturer> loadAllManufacturers() {
		Query q = em.createQuery("select m from Manufacturer m");
		return q.getResultList();
	}

	public Manufacturer saveManufacturer(Manufacturer m) {
		try {
			em.persist(m);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return m;
	}

	public boolean updateManufacturer(Manufacturer m) {
		try {
			em.merge(m);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean deleteManufacturer(Manufacturer m) {
		try {
			Manufacturer temp = em.merge(m);
			em.remove(temp);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

}
