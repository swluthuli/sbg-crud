package za.co.sbg.demo.validation;

import za.co.sbg.demo.domain.request.UserRequest;
import za.co.sbg.demo.handler.exception.ConstraintViolationException;
import za.co.sbg.demo.handler.exception.ResourceNotFoundException;
import za.co.sbg.demo.persistence.entity.User;

public class UserValidation {
    public static void validateRequest(UserRequest userRequest) {
        if(userRequest == null) {
            throw new ConstraintViolationException("User request is null");
        } else if (userRequest.getName() == null || userRequest.getName().isEmpty()) {
            throw new ConstraintViolationException("User name is null or empty");
        } else if (userRequest.getPassword() == null || userRequest.getPassword().isEmpty()) {
            throw new ConstraintViolationException("User password is null or empty");
        }
    }

    public static void validateUser(Long id, User user) {
        if (user == null) {
            throw new ResourceNotFoundException("User with id " + id + " not found");
        }
    }
}
