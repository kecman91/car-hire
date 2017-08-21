package beans;

import java.util.List;

import javax.ejb.Local;

import model.Model;

@Local
public interface ModelBean {
	public List<Model> loadAllModels();
	public Model saveModel(Model m);
	public boolean updateModel(Model m);
	public boolean deleteModel(Model m);
	public List<Model> loadModelsByManufacturer(String manufacturerCode);
	public Model loadModel(int modelId);
}
