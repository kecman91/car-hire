package rest;

import beans.ModelBean;
import beans.VehicleBean;
import beans.VehicleCategoryBean;
import dto.VehicleFilterDto;
import model.Vehicle;
import responses.StringResponse;

import javax.ejb.EJB;
import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Path("vehicles")
public class VehicleService {

    @EJB
    private VehicleBean vehicleBean;

    @EJB
    private VehicleCategoryBean vehicleCategoryBean;

    @EJB
    private ModelBean modelBean;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response allVehicles() {
        try {
            return Response.ok(vehicleBean.loadAllVehicles()).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().entity(new StringResponse("Puko sam!")).build();
        }
    }

    @POST
    @Path("filteredVehicles")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response filteredVehicles(VehicleFilterDto vehicleFilterDto) {
        System.out.println(String.format("%s, %s, %s, %s", vehicleFilterDto.getManufacturers(),
                vehicleFilterDto.getCategories(), vehicleFilterDto.getPriceGT(),
                vehicleFilterDto.getPriceLT()));
        List<Vehicle> vehicles = vehicleBean.loadFilteredVehicles(vehicleFilterDto);
        return Response.ok().entity(vehicles).build();

    }

    @GET
    @Path("freeVehicles")
    @Produces(MediaType.APPLICATION_JSON)
    public Response freeVehicles(@QueryParam("dateFrom") long dateFrom,
                                 @QueryParam("numDays") int numberOfDays) {
        if (numberOfDays > 7) {
            return Response.status(Status.BAD_REQUEST)
                    .entity(new StringResponse("Ne može se iznajmiti auto za više od 7 dana!")).build();
        } else if (numberOfDays < 0) {
            return Response.status(Status.BAD_REQUEST)
                    .entity(new StringResponse("Broj dana ne može biti negativan!")).build();
        }
        Calendar c = Calendar.getInstance();
        Date d = new Date(dateFrom);
        c.setTime(d);
        c.set(Calendar.DATE, c.get(Calendar.DATE) + numberOfDays);
        return Response.ok(vehicleBean.loadFreeVehicles(d, c.getTime())).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response saveVehicle(Vehicle vehicle) {
        return Response.ok().entity(vehicleBean.saveVehicle(vehicle)).build();
    }

    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteVehicle(@QueryParam("vehicleId") String vehicleId) {
        try {
            Vehicle v = vehicleBean.loadVehicle(vehicleId);
            if (v != null) {
                if (vehicleBean.deleteVehicle(v)) {
                    return Response.ok(new StringResponse("Brisanje vozila " + v.getRegNumber() + " uspešna!")).build();
                } else {
                    return Response.serverError().entity(new StringResponse("Puko sam!")).build();
                }
            } else {
                return Response.status(Status.BAD_REQUEST).entity(new StringResponse("Ne postoji vozilo sa datim id-om!")).build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().entity(new StringResponse("Puko sam!")).build();
        }
    }

    @GET
    @Path("lastMonthsMostPopular")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLastMonthsMostPopular() {
        return Response.ok(vehicleBean.getLastMonthsMostPopular()).build();
    }

    @GET
    @Path("search")
    @Produces(MediaType.APPLICATION_JSON)
    public Response search(@QueryParam("searchTerm") String searchTerm) {
        return Response.ok(vehicleBean.search(searchTerm)).build();
    }
}
