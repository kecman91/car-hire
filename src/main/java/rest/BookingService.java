package rest;

import beans.BookingBean;
import dto.BookingDto;
import responses.StringResponse;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("bookings")
public class BookingService {


    @EJB
    private BookingBean bookingBean;

    @POST
    @Path("booking")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response saveBooking(BookingDto bookingDto) {
        System.out.println(bookingDto.toString());
        if (bookingDto.getCustomer() == null && bookingDto.getCustomerId() <= 0) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new StringResponse("Loši unosni podaci!"))
                    .build();
        }
        return Response.ok(bookingBean.saveBooking(bookingDto)).build();
    }

    @GET
    @Path("search")
    @Produces(MediaType.APPLICATION_JSON)
    public Response search(@QueryParam("customerId") int customerId, @QueryParam("unpaidOnly") boolean unpaidOnly,
                           @QueryParam("vehicleRegNum") String vehicleRegNum) {
        if (customerId <= 0 && (vehicleRegNum == null || vehicleRegNum.trim().length() <= 0)) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new StringResponse("Loši unosni podaci!"))
                    .build();
        }
        return Response.ok(bookingBean.searchBookings(customerId, vehicleRegNum, unpaidOnly)).build();
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@QueryParam("bookingId") int bookingId) {
        if (bookingBean.deleteBooking(bookingId)) {
            return Response.noContent().build();
        }
        return Response.serverError().entity("Puko sam!").build();
    }

    @GET
    @Path("payment")
    @Produces(MediaType.APPLICATION_JSON)
    public Response setPaid(@QueryParam("bookingId") int bookingId) {
        if (bookingBean.setPaid(bookingId)) {
            return Response.noContent().build();
        }
        return Response.serverError().entity("Puko sam!").build();
    }
}
