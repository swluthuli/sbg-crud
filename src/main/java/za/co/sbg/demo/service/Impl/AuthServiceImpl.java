package za.co.sbg.demo.service.Impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import za.co.sbg.demo.domain.request.LoginRequest;
import za.co.sbg.demo.handler.exception.ConstraintViolationException;
import za.co.sbg.demo.handler.exception.ResourceNotFoundException;
import za.co.sbg.demo.persistence.entity.User;
import za.co.sbg.demo.persistence.repository.UserRepository;
import za.co.sbg.demo.security.JwtUtil;
import za.co.sbg.demo.service.AuthService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;


@RequestScoped
public class AuthServiceImpl implements AuthService {
    private static final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);

    @Inject
    UserRepository userRepository;
    @Inject
    JwtUtil jwtUtil;
    @Override
    public String login(LoginRequest loginRequest) {

        if(loginRequest == null || loginRequest.getUsername() == null || loginRequest.getPassword() == null) {
            throw new ConstraintViolationException("Please provide a valid username/password");
        }
        logger.info("Login request for user: {}", loginRequest.getUsername());
        User user = userRepository.findByUsername(loginRequest.getUsername(), loginRequest.getPassword());
        if (user == null) {
            throw new ResourceNotFoundException("User not found");
        }
        logger.info("User: {} ", user.getName() + " Logged in");
        return jwtUtil.generateToken(user.getName());
    }
}
