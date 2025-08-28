package com.businesslogic.client;

import com.businesslogic.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@Component
public class BankClient implements WebClient {
    private final RestClient restClient;

    @Autowired
    public BankClient(RestClient restClient) {
        this.restClient = restClient;
    }

    @Override
    public UserDTO getUser(String login) {
        return restClient
                .get()
                .uri("/users/{login}", login)
                .retrieve()
                .toEntity(UserDTO.class)
                .getBody();
    }

    @Override
    public void createUser(RegisterRequest req) {
        restClient.post()
                .uri("/users/create")
                .body(req)
                .retrieve()
                .toBodilessEntity();
    }

    @Override
    public Collection<String> getUsersFiltered(String hairColor, String gender) {
        return restClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/users/filter")
                        .queryParamIfPresent("hairColor", Optional.ofNullable(hairColor))
                        .queryParamIfPresent("gender", Optional.ofNullable(gender))
                        .build())
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});
    }

    @Override
    public Collection<Integer> getAllAccountIds() {
        return restClient
                .get()
                .uri("/accounts/get")
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});
    }

    @Override
    public Collection<Integer> getAccountsByUserId(UUID userId) {
        return restClient
                .get()
                .uri("/accounts/get/{userId}", userId)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});
    }

    @Override
    public Collection<TransactionDTO> getTransactionsFiltered(Integer accountId, String type) {
        return restClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/accounts/transactions")
                        .queryParamIfPresent("accountId", Optional.ofNullable(accountId))
                        .queryParamIfPresent("type", Optional.ofNullable(type))
                        .build())
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});
    }

    @Override
    public void createAccount(AccountRequest req) {
        restClient.post()
                .uri("/accounts/create")
                .body(req)
                .retrieve()
                .toBodilessEntity();
    }

    @Override
    public Double getBalance(Integer accountId, String login) {
        return restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/accounts/balance")
                        .queryParam("accountId", accountId)
                        .queryParam("userLogin", login)
                        .build())
                .retrieve()
                .body(Double.class);
    }

    @Override
    public void deposit(AccountOperationRequest request) {
        restClient.post()
                .uri("/accounts/deposit")
                .body(request)
                .retrieve()
                .toBodilessEntity();
    }

    @Override
    public void withdraw(AccountOperationRequest request) {
        restClient.post()
                .uri("/accounts/withdraw")
                .body(request)
                .retrieve()
                .toBodilessEntity();
    }

    @Override
    public void transferMoney(AccountTransferRequest request) {
        restClient.post()
                .uri("/accounts/transfer")
                .body(request)
                .retrieve()
                .toBodilessEntity();
    }

    @Override
    public void addFriend(String friendLogin, String login) {
        restClient
                .post()
                .uri("/users/makeFriends/{userLogin}/{friendLogin}", login, friendLogin)
                .retrieve()
                .toBodilessEntity();
    }

    @Override
    public void removeFriend(String friendLogin, String login) {
        restClient.delete()
                .uri("/users/unmakeFriends/{userLogin}/{friendLogin}", login, friendLogin)
                .retrieve()
                .toBodilessEntity();
    }
}
