package beans;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import model.VehicleCategory;

@Stateless
public class VehicleCategoryBeanImpl implements VehicleCategoryBean {
	
	@PersistenceContext(name="seds")
	EntityManager em;

	@SuppressWarnings("unchecked")
	@Override
	public List<VehicleCategory> loadAllVehicleCategories() {
		Query q = em.createQuery("select vc from VehicleCategory vc");
		return q.getResultList();
	}

	@Override
	public VehicleCategory saveVehicleCategory(VehicleCategory vc) {
		try {
			em.persist(vc);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return vc;
	}

	@Override
	public boolean updateVehicleCategory(VehicleCategory vc) {
		try {
			em.merge(vc);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean deleteVehicleCategory(VehicleCategory vc) {
		try {
			VehicleCategory temp = em.merge(vc);
			em.remove(temp);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public VehicleCategory loadVehicleCategory(int categoryId) {
		return em.find(VehicleCategory.class, categoryId);
	}

}
