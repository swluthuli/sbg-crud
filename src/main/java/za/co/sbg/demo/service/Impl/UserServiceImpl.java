package za.co.sbg.demo.service.Impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import za.co.sbg.demo.domain.request.UserRequest;
import za.co.sbg.demo.handler.exception.ResourceNotFoundException;
import za.co.sbg.demo.persistence.entity.User;
import za.co.sbg.demo.persistence.repository.UserRepository;
import za.co.sbg.demo.service.UserService;
import za.co.sbg.demo.service.factory.UserFactory;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.validation.ConstraintViolationException;
import java.util.List;

import static za.co.sbg.demo.validation.UserValidation.validateRequest;
import static za.co.sbg.demo.validation.UserValidation.validateUser;

@RequestScoped
public class UserServiceImpl implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Inject
    private UserRepository userRepository;

    @Override
    public List<User> getAllUsers() {
        return userRepository.getAllUsers();
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.getUser(id);
    }

    @Override
    public void createUser(UserRequest userRequest) {
        logger.info("Creating user {}", userRequest);
        validateRequest(userRequest);
        userRepository.createUser(UserFactory.createUser(userRequest));
        logger.info("User {} created", userRequest);
    }

    @Override
    public boolean updateUser(Long id, UserRequest userRequest) {
        logger.info("Updating user {}", userRequest);
        User user = userRepository.getUser(id);
        validateUser(id, user);
        user.setName(userRequest.getName());
            user.setEmail(userRequest.getEmail());
            user.setPassword(userRequest.getPassword());
            userRepository.updateUser(user);
            logger.info("User {} updated", user);
            return true;
        }

    @Override
    public boolean deleteUser(Long id) {
        logger.info("Deleting user {}", id);
        User user = userRepository.getUser(id);
        validateUser(id, user);
        userRepository.deleteUser(user);
        logger.info("User {} deleted", id);
        return true;
    }
}
