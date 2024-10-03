package za.co.sbg.demo.service;

import za.co.sbg.demo.domain.request.UserRequest;
import za.co.sbg.demo.persistence.entity.User;

import java.util.List;

public interface UserService {

    List<User> getAllUsers();
    User getUserById(Long id);
    void createUser(UserRequest userRequest);
    boolean updateUser(Long id, UserRequest userRequest);
    boolean deleteUser(Long id);
}
