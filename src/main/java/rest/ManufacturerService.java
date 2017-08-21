package rest;

import beans.ManufacturerBean;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Path("manufacturers")
public class ManufacturerService {

	public static final String SESSION_PATH = "ejb:CarHireEAR/CarHireEJB/";

	@EJB
	private ManufacturerBean manufacturerBean;
	
	@GET
	@Produces("application/json")
	public Response allManufacturers() {
		return Response.ok().entity(manufacturerBean.loadAllManufacturers()).build();
	}
	
	/*@POST
	@Consumes("application/json")
	public String saveManufacturer(@FormParam String manufacturer) {
		
	}*/
}
