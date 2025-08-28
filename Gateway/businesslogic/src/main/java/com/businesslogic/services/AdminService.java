package com.businesslogic.services;

import com.businesslogic.client.WebClient;
import com.businesslogic.dto.*;
import com.businesslogic.exceptions.UserExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.UUID;

@Service
public class AdminService {
    private WebClient webClient;
    private final AuthService authService;

    @Autowired
    public AdminService(WebClient webClient, AuthService authService) {
        this.webClient = webClient;
        this.authService = authService;
    }

    public void registerAdmin(RegisterAdminRequest registerAdminRequest) throws UserExistsException  {
        authService.registerAdmin(registerAdminRequest);
    }

    public void registerClient(RegisterUserRequest registerUserRequest) throws UserExistsException  {
        RegisterRequest req = new RegisterRequest();
        req.setLogin(registerUserRequest.getLogin());
        req.setName(registerUserRequest.getName());
        req.setAge(registerUserRequest.getAge());
        req.setGender(registerUserRequest.getGender());
        req.setHairColor(registerUserRequest.getHairColor());

        webClient.createUser(req);
        authService.registerUser(registerUserRequest);
    }

    public UserDTO getUser(String login) {
        return webClient.getUser(login);
    }

    public Collection<String> getUsersFiltered(String hairColor, String gender) {
        return webClient.getUsersFiltered(hairColor, gender);
    }

    public Collection<Integer> getAllAccountIds() {
        return webClient.getAllAccountIds();
    }

    public Collection<Integer> getAccountsByUserId(String login) {
        UserDTO userDTO = webClient.getUser(login);
        UUID userId = userDTO.getId();

        return webClient.getAccountsByUserId(userId);
    }

    public Collection<TransactionDTO> getTransactionsFiltered(Integer accountId, String type) {
        return webClient.getTransactionsFiltered(accountId, type);
    }
}
