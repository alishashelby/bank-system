package com.businesslogic.services;

import com.businesslogic.client.WebClient;
import com.businesslogic.dto.AccountOperationRequest;
import com.businesslogic.dto.AccountRequest;
import com.businesslogic.dto.AccountTransferRequest;
import com.businesslogic.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.UUID;

@Service
public class ClientService {
    private WebClient webClient;

    @Autowired
    public ClientService(WebClient webClient) {
        this.webClient = webClient;
    }

    private String getCurrentLogin() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    public UserDTO getUser() {
        return webClient.getUser(getCurrentLogin());
    }

    public Collection<Integer> getAccountsByUserId() {
        UserDTO userDTO = webClient.getUser(getCurrentLogin());
        UUID userId = userDTO.getId();

        return webClient.getAccountsByUserId(userId);
    }

    public void createAccount(AccountRequest accountRequest) {
        accountRequest.setUserLogin(getCurrentLogin());
        webClient.createAccount(accountRequest);
    }

    public Double getBalance(Integer accountId) {
        return webClient.getBalance(accountId, getCurrentLogin());
    }

    public void deposit(AccountOperationRequest request) {
        request.setUserLogin(getCurrentLogin());
        webClient.deposit(request);
    }

    public void withdraw(AccountOperationRequest request) {
        request.setUserLogin(getCurrentLogin());
        webClient.withdraw(request);
    }

    public void transferMoney(AccountTransferRequest request) {
        request.setUserLogin(getCurrentLogin());
        webClient.transferMoney(request);
    }

    public void addFriend(String friendLogin) {
        webClient.addFriend(friendLogin, getCurrentLogin());
    }

    public void removeFriend(String friendLogin) {
        webClient.removeFriend(friendLogin, getCurrentLogin());
    }
}
