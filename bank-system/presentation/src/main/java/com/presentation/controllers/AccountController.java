package com.presentation.controllers;

import com.businesslogic.dto.TransactionDTO;
import com.businesslogic.dto.account.AccountOperationRequest;
import com.businesslogic.dto.account.AccountRequest;
import com.businesslogic.dto.account.AccountTransferRequest;
import com.businesslogic.exceptions.BLException;
import com.businesslogic.services.account.AccountService;
import com.dataaccess.enums.TransactionType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.UUID;

@RestController
@RequestMapping("/accounts")
@Tag(
        name = "Account Controller",
        description = "is responsible for account and transaction management"
)
public class AccountController {
    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/create")
    @Operation(summary = "Create the account", description = "Allows to register new account")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Account is successfully created"),
            @ApiResponse(responseCode = "400", description = "Incorrect account data"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "409", description = "Account with this id already exists")
    })
    public ResponseEntity<?> createAccount(
            @Valid @RequestBody AccountRequest request) throws BLException {
        accountService.createAccount(request.getAccountId(), request.getUserLogin());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/balance")
    @Operation(summary = "Get account balance", description = "Get current balance of specified account")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Balance retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Incorrect account data"),
            @ApiResponse(responseCode = "404", description = "Account or user not found")
    })
    public ResponseEntity<?> getBalance(@RequestParam("accountId") Integer accountId,
                                        @RequestParam("userLogin") String userLogin)
            throws BLException {
        double rez = accountService.getBalance(accountId, userLogin);
        return new ResponseEntity<>(rez, HttpStatus.OK);
    }

    @PostMapping("/deposit")
    @Operation(summary = "Deposit amount", description = "Deposit money to the account")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Deposit successful"),
            @ApiResponse(responseCode = "400", description = "Incorrect account data or amount"),
            @ApiResponse(responseCode = "404", description = "Account or user not found")
    })
    public ResponseEntity<?> deposit(@Valid @RequestBody AccountOperationRequest request) throws BLException {
        accountService.deposit(request.getAccountId(), request.getUserLogin(), request.getAmount());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/withdraw")
    @Operation(summary = "Withdraw amount", description = "Withdraw money from the account")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Withdraw successful"),
            @ApiResponse(responseCode = "400", description = "Incorrect account data or insufficient funds"),
            @ApiResponse(responseCode = "404", description = "Account or user not found")
    })
    public ResponseEntity<?> withdraw(@Valid @RequestBody AccountOperationRequest request) throws BLException {
        accountService.withdraw(request.getAccountId(), request.getUserLogin(), request.getAmount());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/transfer")
    @Operation(summary = "Transfer amount", description = "Transfer amount of money between accounts")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Transfer successful"),
            @ApiResponse(responseCode = "400", description = "Invalid amount"),
            @ApiResponse(responseCode = "404", description = "Account/accounts not found"),
            @ApiResponse(responseCode = "409", description = "Attempt to transfer to the same account")
    })
    public ResponseEntity<?> transfer(@Valid @RequestBody AccountTransferRequest request) throws BLException {
        accountService.transfer(request.getFromAccountId(),
                request.getUserLogin(),
                request.getToAccountId(),
                request.getAmount());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/get/{userId}")
    @Operation(summary = "Get user accounts", description = "Get all account IDs for specified user")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Accounts retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<Collection<Integer>> getAccountsByUserId(
            @Parameter(description = "User's UUID", example = "c4cc9b48-a58a-4632-85ac-444b24187f9f")
            @PathVariable("userId") UUID userId
    ) throws BLException {
        Collection<Integer> rez = accountService.getAllByUserId(userId);
        return new ResponseEntity<>(rez, HttpStatus.OK);
    }

    @GetMapping("/get")
    @Operation(summary = "Get all accounts", description = "Get all registered account IDs")
    @ApiResponse(responseCode = "200", description = "Accounts retrieved successfully")
    public ResponseEntity<Collection<Integer>> getAllAccountIds() {
        Collection<Integer> rez = accountService.getAllAccountIds();
        return new ResponseEntity<>(rez, HttpStatus.OK);
    }

    @GetMapping("/transactions")
    @Operation(summary = "Get filtered transactions", description = "Get filtered or all transaction history")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Transactions retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Account not found")
    })
    public ResponseEntity<Collection<TransactionDTO>> getTransactionsFiltered(
            @Parameter(description = "Account ID", example = "1111")
            @RequestParam(name = "accountId", required = false) Integer accountId,
            @Parameter(description = "Transaction type", example = "WITHDRAW")
            @RequestParam(name = "type", required = false) TransactionType type
    ) throws BLException {
        Collection<TransactionDTO> rez = accountService.getTransactionsFiltered(accountId, type);
        return new ResponseEntity<>(rez, HttpStatus.OK);
    }
}
