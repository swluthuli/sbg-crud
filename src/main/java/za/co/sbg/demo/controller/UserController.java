package za.co.sbg.demo.controller;

import za.co.sbg.demo.domain.request.UserRequest;
import za.co.sbg.demo.persistence.entity.User;
import za.co.sbg.demo.service.UserService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserController {

    @Inject
    private UserService userService;

    @GET
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GET
    @Path("/{id}")
    public User getUserById(@PathParam("id") Long id) {
        return userService.getUserById(id);
    }

    @POST
    public Response addUser(UserRequest userRequest) {
        userService.createUser(userRequest);
       return Response.status(Response.Status.CREATED).entity(userRequest).build();
    }
    @PUT
    @Path("/{id}")
    public  Response updateUser(UserRequest userRequest , @PathParam("id") Long id) {
        if(!userService.updateUser(id,userRequest)){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.status(Response.Status.OK).build();
    }
    @DELETE
    @Path("/{id}")
    public Response deleteUser(@PathParam("id") Long id) {
        if(!userService.deleteUser(id)){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.status(Response.Status.OK).build();
    }
}
