package com.businesslogic.client;

import com.businesslogic.dto.*;

import java.util.Collection;
import java.util.UUID;

public interface WebClient {
    UserDTO getUser(String login);
    void createUser(RegisterRequest req);
    Collection<String> getUsersFiltered(String hairColor, String gender);
    Collection<Integer> getAllAccountIds();
    Collection<Integer> getAccountsByUserId(UUID userId);
    Collection<TransactionDTO> getTransactionsFiltered(Integer accountId, String type);
    void createAccount(AccountRequest req);
    Double getBalance(Integer accountId, String login);
    void deposit(AccountOperationRequest request);
    void withdraw(AccountOperationRequest request);
    void transferMoney(AccountTransferRequest request);
    void addFriend(String friendLogin, String login);
    void removeFriend(String friendLogin, String login);
}
