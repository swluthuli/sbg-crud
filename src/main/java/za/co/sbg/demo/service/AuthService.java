package za.co.sbg.demo.service;

import za.co.sbg.demo.domain.request.LoginRequest;

public interface AuthService {

    String login(LoginRequest loginRequest);
}
