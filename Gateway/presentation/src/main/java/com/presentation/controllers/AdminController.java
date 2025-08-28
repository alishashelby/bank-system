package com.presentation.controllers;

import com.businesslogic.dto.*;
import com.businesslogic.client.BankClient;
import com.businesslogic.services.AdminService;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.UUID;

import com.businesslogic.services.AuthService;
import com.businesslogic.exceptions.UserExistsException;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/admins")
    public ResponseEntity<?> registerAdmin(@RequestBody @Valid RegisterAdminRequest registerAdminRequest)
            throws UserExistsException {
        adminService.registerAdmin(registerAdminRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/users")
    public ResponseEntity<?> registerClient(@Valid @RequestBody RegisterUserRequest registerUserRequest)
            throws UserExistsException {
        adminService.registerClient(registerUserRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/users/{login}")
    public ResponseEntity<UserDTO> getUserInfo(@PathVariable("login") String login) {
        UserDTO userDTO = adminService.getUser(login);
        return ResponseEntity.status(HttpStatus.OK).body(userDTO);
    }

    @GetMapping("/users/filter")
    public ResponseEntity<Collection<String>> getUsersFiltered(
            @RequestParam(name = "hairColor", required = false)String hairColor,
            @RequestParam(name = "gender", required = false)String gender) {
        Collection<String> usernames = adminService.getUsersFiltered(hairColor, gender);
        return ResponseEntity.status(HttpStatus.OK).body(usernames);
    }

    @GetMapping("/accounts")
    public ResponseEntity<Collection<Integer>> getAllAccountIds() {
        Collection<Integer> ids = adminService.getAllAccountIds();
        return ResponseEntity.status(HttpStatus.OK).body(ids);
    }

    @GetMapping("/accounts/get/{login}")
    public ResponseEntity<?> getAccountsByUserId(
            @PathVariable("login") String login) {
        Collection<Integer> ids = adminService.getAccountsByUserId(login);
        return ResponseEntity.status(HttpStatus.OK).body(ids);
    }

    @GetMapping("/accounts/transactions")
    public ResponseEntity<?> getTransactionsFiltered(
            @RequestParam(name = "accountId", required = false) Integer accountId,
            @RequestParam(name = "type", required = false) String type
    ) {
        Collection<TransactionDTO> transactions = adminService.getTransactionsFiltered(accountId, type);
        return ResponseEntity.status(HttpStatus.OK).body(transactions);
    }
}
