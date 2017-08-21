package rest;

import beans.ModelBean;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("models")
public class ModelService {

	@EJB
	private ModelBean modelBean;

	@GET
	@Produces("application/json")
	public Response allModels() {
		return Response.ok().entity(modelBean.loadAllModels()).build();
	}
	
	@GET
	@Path("modelsByManufacturer")
	@Produces(MediaType.APPLICATION_JSON)
	public Response modelsByManufacturer(@QueryParam("manufacturerCode") String manufacturerCode) {
		return Response.ok(modelBean.loadModelsByManufacturer(manufacturerCode)).build();
	}
}
