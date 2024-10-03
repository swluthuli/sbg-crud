package za.co.sbg.demo.security;

import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

@Provider
public class AuthFilter implements ContainerRequestFilter {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String AUTH_PREFIX = "Bearer ";

    @Inject
    private JwtUtil jwtUtil;

    @Override
    public void filter(ContainerRequestContext requestContext) {
        if (!"DELETE".equals(requestContext.getMethod())) {
            return;
        }

        String authorizationHeader = requestContext.getHeaders().getFirst(AUTHORIZATION_HEADER);

        if (authorizationHeader == null || !authorizationHeader.startsWith(AUTH_PREFIX)) {
            abortWithUnauthorized(requestContext);
            return;
        }

        String token = authorizationHeader.substring("Bearer ".length());
        String username = jwtUtil.extractUsername(token);

        if (username == null || !jwtUtil.validateToken(token, username)) {
            abortWithUnauthorized(requestContext);
        }
    }

    private void abortWithUnauthorized(ContainerRequestContext requestContext) {
        requestContext.abortWith(Response
                .status(Response.Status.UNAUTHORIZED)
                .header("WWW-Authenticate", "Basic realm=\"ApplicationRealm\"")
                .entity("User cannot access the resource.")
                .build());
    }
}
