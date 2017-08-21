package beans;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import dto.VehicleFilterDto;
import model.Vehicle;

@Local
public interface VehicleBean {
	List<Vehicle> loadAllVehicles();
	List<Vehicle> loadFreeVehicles(Date from, Date to);
	Vehicle saveVehicle(Vehicle v);
	boolean deleteVehicle(Vehicle v);
	Vehicle loadVehicle(String id);
	List<Vehicle> loadFilteredVehicles(VehicleFilterDto filter);
	Vehicle getLastMonthsMostPopular();
	List<Vehicle> search(String searchTerm);
}
