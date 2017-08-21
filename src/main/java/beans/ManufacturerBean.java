package beans;

import java.util.List;

import javax.ejb.Local;

import model.Manufacturer;

@Local
public interface ManufacturerBean {
	public List<Manufacturer> loadAllManufacturers();
	public Manufacturer saveManufacturer(Manufacturer m);
	public boolean updateManufacturer(Manufacturer m);
	public boolean deleteManufacturer(Manufacturer m);
}
