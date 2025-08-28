package com.presentation.controllers;

import com.businesslogic.dto.UserDTO;
import com.businesslogic.services.ClientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.businesslogic.dto.AccountOperationRequest;
import com.businesslogic.dto.AccountRequest;
import com.businesslogic.dto.AccountTransferRequest;

import java.util.Collection;

@RestController
@RequestMapping("/api/client")
@PreAuthorize("hasRole('CLIENT')")
public class ClientController {
    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/info")
    public ResponseEntity<UserDTO> getUserInfo() {
        UserDTO userDTO = clientService.getUser();
        return ResponseEntity.status(HttpStatus.OK).body(userDTO);
    }

    @GetMapping("/accounts")
    public ResponseEntity<?> getAccounts() {
        Collection<Integer> ids = clientService.getAccountsByUserId();
        return ResponseEntity.status(HttpStatus.OK).body(ids);
    }

    @PostMapping("/accounts/create")
    public ResponseEntity<?> createAccount(@Valid @RequestBody AccountRequest request) {
        clientService.createAccount(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/accounts/balance")
    public ResponseEntity<?> getBalance(@RequestParam("accountId") Integer accountId) {
        Double balance = clientService.getBalance(accountId);
        return ResponseEntity.status(HttpStatus.OK).body(balance);
    }

    @PostMapping("/deposit")
    public ResponseEntity<?> deposit(@Valid @RequestBody AccountOperationRequest request) {
        clientService.deposit(request);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/withdraw")
    public ResponseEntity<?> withdraw(@Valid @RequestBody AccountOperationRequest request) {
        clientService.withdraw(request);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/transfer")
    public ResponseEntity<?> transfer(@Valid @RequestBody AccountTransferRequest request) {
        clientService.transferMoney(request);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/friend/{friendLogin}")
    public ResponseEntity<?> addFriend(@PathVariable("friendLogin") String friendLogin) {
        clientService.addFriend(friendLogin);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/unfriend/{friendLogin}")
    public ResponseEntity<?> removeFriend(@PathVariable("friendLogin") String friendLogin) {
        clientService.removeFriend(friendLogin);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
