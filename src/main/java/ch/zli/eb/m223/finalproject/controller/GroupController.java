package ch.zli.eb.m223.finalproject.controller;

import java.util.List;

import javax.inject.Inject;
import javax.swing.GroupLayout.Group;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import ch.zli.eb.m223.finalproject.model.CwSGroup;
import ch.zli.eb.m223.finalproject.service.GroupService;
import ch.zli.eb.m223.finalproject.service.UserService;

@Path("/group")
@Tag(name = "Groups", description = "Handling of groups")
public class GroupController {
    

    @Inject
    GroupService groupService;

    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(
        summary = "find single group by id",
        description = "find a single group by its id and return it"
    )
    public CwSGroup find(@PathParam("id") Long id){
        return groupService.find(id);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(
        summary = "Index all groups",
        description = "Returns a list of all entries "
    )
    public List<CwSGroup> index (){
        return groupService.findAll();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(
        summary = "Create new Group",
        description = "Create a new Group and return the newly created entity"
    )
    public CwSGroup create(@Valid CwSGroup group){
        return groupService.createGroup(group);
    }

    @Path("/{id}")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(
        summary = "Update group",
        description = "Update group with speciefied id"
    )
    public CwSGroup update(@PathParam("id") Long id,@Valid CwSGroup group){
        return groupService.updateGroup(id, group);
    }

    @Path("({id}")
    @DELETE
    @Operation(
        summary = "deletes an existing group",
        description = "deletes an existing group with speciefied id"
    )
    public void delete(@PathParam("id") Long id){
        groupService.deleteGroup(id);
    }
}
