package beans;

import model.Model;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Stateless
@Local
public class ModelBeanImpl implements ModelBean {
	
	@PersistenceContext(name="seds")
	private EntityManager em;

	@SuppressWarnings("unchecked")
	public List<Model> loadAllModels() {
		Query q = em.createQuery("select m from Model m");
		return q.getResultList();
	}

	public Model saveModel(Model m) {
		try {
			em.persist(m);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return m;
	}

	public boolean updateModel(Model m) {
		try {
			em.merge(m);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean deleteModel(Model m) {
		try {
			Model temp = em.merge(m);
			em.remove(temp);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@SuppressWarnings("unchecked")
	public List<Model> loadModelsByManufacturer(String manufacturerCode) {
		Query q = em.createQuery("select m from Model m where m.manufacturer.manufacturerCode = :mCode");
		q.setParameter("mCode", manufacturerCode);
		return q.getResultList();
	}

	public Model loadModel(int modelId) {
		return em.find(Model.class, modelId);
	}

}
