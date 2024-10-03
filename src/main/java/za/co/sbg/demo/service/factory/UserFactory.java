package za.co.sbg.demo.service.factory;

import za.co.sbg.demo.domain.request.UserRequest;
import za.co.sbg.demo.persistence.entity.User;

public class UserFactory {

    public static User createUser(UserRequest userRequest) {
       return User.builder()
               .email(userRequest.getEmail())
               .password(userRequest.getPassword())
               .name(userRequest.getName())
               .build();
    }
}
