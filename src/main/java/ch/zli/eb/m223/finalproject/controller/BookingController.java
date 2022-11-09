package ch.zli.eb.m223.finalproject.controller;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.print.attribute.standard.Media;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.hibernate.Criteria;

import com.arjuna.ats.arjuna.common.recoveryPropertyManager;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.eclipse.microprofile.openapi.annotations.Operation;

import ch.zli.eb.m223.finalproject.model.Booking;
import ch.zli.eb.m223.finalproject.model.CwSGroup;
import ch.zli.eb.m223.finalproject.model.CwSUser;
import ch.zli.eb.m223.finalproject.service.BookingService;
import ch.zli.eb.m223.finalproject.service.UserService;
import io.vertx.core.cli.annotations.Description;

@Path("/bookings")
@Tag(name = "Bookings", description = "Handling of Bookings")
public class BookingController {

    @Inject
    BookingService bookingService;

    @Path("/{id")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(
        summary = "find booking by id",
        description = "find a booking by its id and return it"
    )
    public Booking find(@PathParam("id")Long id){
        return bookingService.getBooking(id);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(
        summary = "index all bookings",
        description = "returns a list of all bookings"
    )
    public List<Booking> index(){
        return bookingService.findAll();
    }
    

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(
        summary = "Creates new booking",
        description = "Creates a new booking and returns the newly added booking"
    )
    public Booking create(@Valid Booking booking){
        return bookingService.createBooking(booking);
    }

    @Path("/{id}")
    @DELETE
    @Operation(
        summary = "Deletes a booking",
        description = "deletes a booking by its id"
    )
    public void delete(@PathParam("id")Long id){
        bookingService.deleteBooking(id);
    }

    @Path("/{id}")
    @PUT
    @Operation(
        summary = "updates a booking",
        description = "Updates a booking by its id and returns new version"
    )
    public Booking update(@PathParam("id")Long id, @Valid Booking booking){
        return bookingService.updateBooking(id, booking);
    }
    
}
