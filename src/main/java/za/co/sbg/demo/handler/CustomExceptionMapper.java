package za.co.sbg.demo.handler;

import za.co.sbg.demo.domain.ErrorResponse;
import za.co.sbg.demo.handler.exception.ResourceNotFoundException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

@Provider
public class CustomExceptionMapper implements javax.ws.rs.ext.ExceptionMapper<RuntimeException> {
    @Override
    public Response toResponse(RuntimeException e) {
        if (e instanceof ResourceNotFoundException) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorResponse("Not Found", e.getMessage()))
                    .build();
        }else if (e instanceof IllegalArgumentException) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse("Validation error", e.getMessage()))
                    .build();
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(new ErrorResponse("Internal Server Error","An unexpected error"))
                .build();
    }
}
