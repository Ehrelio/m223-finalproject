package ch.zli.eb.m223.finalproject.controller;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.eclipse.microprofile.openapi.annotations.Operation;

import ch.zli.eb.m223.finalproject.model.CwSUser;
import ch.zli.eb.m223.finalproject.service.UserService;

@Path("/users")
@Tag(name = "Users", description = "Handling of users")
@RolesAllowed({"user", "admin"})
public class UserController {

    @Inject
    UserService userService;

    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(
        summary = "find user by id",
        description = "find a user by his id and return it"
    )
    public CwSUser find(@PathParam("id") Long id){
        return userService.getUser(id);
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(
        summary = "Index all users",
        description = "returns a list of all users"
    )
    public List<CwSUser> index(){
        return userService.findAll();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(
        summary = "Creates new User",
        description = "creates a new user and returns the newly added user"
    )
    public CwSUser create(@Valid CwSUser user){
        return userService.createUser(user);
    }

    @Path("/{id}")
    @DELETE
    @Operation(
        summary = "Deletes a user",
        description = "deltes a user by its id"
    )
    public void delete(@PathParam("id") Long id){
        userService.deleteUser(id);
    }

    @Path("/{id}")
    @PUT
    @Operation(
        summary = "Updates a user",
        description = "Updates a user by its id"
    )
    public CwSUser update(@PathParam("id") Long id, @Valid CwSUser user){
        return userService.updateUser(id, user);
    }
    
}
