package rest;

import beans.CustomerBean;
import model.Customer;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("customers")
public class CustomerService {

	@EJB
	private CustomerBean customerBean;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response allCustomers() {
		return Response.ok().entity(customerBean.loadAllCustomers()).build();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response saveCustomer(Customer c) {
		return Response.ok().entity(customerBean.saveCustomer(c)).build();
	}

	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteCustomer(@QueryParam("customerId") int customerId) {
		if (customerBean.deleteCustomer(customerId)) {
			return Response.noContent().build();
		} else {
			return Response.serverError().entity("Puko sam!").build();
		}
	}

	@GET
	@Path("byUnpaidBookings")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getByUnpaidBookings() {
		return Response.ok(customerBean.getByUnpaidBookings()).build();
	}

	@GET
	@Path("search")
	@Produces(MediaType.APPLICATION_JSON)
	public Response search(@QueryParam("searchTerm") String searchTerm) {
		return Response.ok(customerBean.search(searchTerm)).build();
	}
}
