package beans;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Remote;

import model.VehicleCategory;

@Local
public interface VehicleCategoryBean {
	public List<VehicleCategory> loadAllVehicleCategories();
	public VehicleCategory saveVehicleCategory(VehicleCategory vc);
	public boolean updateVehicleCategory(VehicleCategory vc);
	public boolean deleteVehicleCategory(VehicleCategory vc);
	public VehicleCategory loadVehicleCategory(int categoryId);
}
