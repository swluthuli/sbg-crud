package za.co.sbg.demo.controller;

import za.co.sbg.demo.domain.request.LoginRequest;
import za.co.sbg.demo.service.AuthService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/login")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthController {


    @Inject
    private AuthService authService;

    @POST
    public Response login(LoginRequest loginRequest) {
    String token =  authService.login(loginRequest);
    return Response.ok().entity(token).build();
}
}
