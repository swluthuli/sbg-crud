package za.co.sbg.demo.persistence.repository;

import za.co.sbg.demo.persistence.entity.Product;
import za.co.sbg.demo.persistence.entity.User;

import java.util.List;


public interface UserRepository {
    User findByUsername(String username, String password);
    void createUser(User user);
    User getUser(Long userId);
    List<User> getAllUsers();
    void updateUser(User user);
    void deleteUser(User user);
}
